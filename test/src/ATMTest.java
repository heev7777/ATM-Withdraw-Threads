import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ATMTest {

    private BankAccount account;

    @BeforeEach
    void setUp() {
        account = new BankAccount(0);
    }

    @Test
    void testDeposit() {
        account.deposit(100, "TestDepositThread");
        assertEquals(100, account.getBalance(), "Deposit should increase balance");
    }

    @Test
    void testWithdrawSufficientFunds() {
        account.deposit(200, "TestDepositThread");
        account.withdraw(100, "TestWithdrawThread");
        assertEquals(100, account.getBalance(), "Withdraw should decrease balance");
    }

    @Test
    void testWithdrawInsufficientFunds() throws InterruptedException {
        WithdrawThread withdrawThread = new WithdrawThread(account, 100, 1); // Withdraw 100 every second.
        Thread thread = new Thread(withdrawThread);
        thread.start();

        TimeUnit.SECONDS.sleep(2); // We wait for the withdraw

        // Let the thread wait and we expect balance to be 0
        assertEquals(0.0, account.getBalance(), 0.0);

        // Now deposit and see if it happens
        account.deposit(200, "TestDepositThread");

        TimeUnit.SECONDS.sleep(2); // We give some time

        // The balance should now be 100
        assertTrue(account.getBalance() <= 100, "Balance should be less than or equal to 100 after deposit and withdrawal");
    }

    @Test
    void testConcurrentDeposits() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(new DepositThread(account, 100, 1));
        executor.submit(new DepositThread(account, 200, 1));

        TimeUnit.SECONDS.sleep(5); // Let the deposit happen
        executor.shutdownNow();

        assertTrue(account.getBalance() >= 1500, "Concurrent deposits should be handled correctly");
    }

    @Test
    void testConcurrentWithdrawals() throws InterruptedException {
        account.deposit(2000, "InitialDeposit");

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(new WithdrawThread(account, 100, 1));
        executor.submit(new WithdrawThread(account, 200, 1));

        TimeUnit.SECONDS.sleep(5);
        executor.shutdownNow();

        assertTrue(account.getBalance() <= 500, "Concurrent withdrawals should be handled correctly");
    }

    @Test
    void testDepositAndWithdrawal() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(new DepositThread(account, 300, 1));
        executor.submit(new WithdrawThread(account, 100, 2));

        TimeUnit.SECONDS.sleep(5);
        executor.shutdownNow();

        assertTrue(account.getBalance() >= 500, "Combined deposits and withdrawals should be handled correctly");
    }

    @Test
    void testAddInterest() {
        account.deposit(1000, "InterestTestDeposit");
        account.addInterest(0.02); // 2% interest
        assertEquals(1020.0, account.getBalance(), 0.0, "Interest should be added correctly");
    }

    @Test
    void testInterestWithConcurrentTransactions() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.submit(new DepositThread(account, 100, 1)); // Deposit 100 every second
        executor.submit(new WithdrawThread(account, 50, 2)); // Withdraw 50 every 2 seconds
        executor.submit(new InterestThread(account, 0.02, 5)); // Add 2% interest every 5 seconds

        TimeUnit.SECONDS.sleep(10);
        executor.shutdownNow();

        assertTrue(account.getBalance() > 0, "Balance should reflect deposits, withdrawals, and interest");
    }
}