import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Simulates a banking system with multiple threads.
 */
public class Main {
    public static void main(String[] args) {
        BankAccount sharedAccount = new BankAccount(1000);
        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(new DepositThread(sharedAccount, 300, 1)); // Thread 1: Deposits 300 every second
        executor.submit(new WithdrawThread(sharedAccount, 200, 2)); // Thread 2: Withdraws 200 every two seconds
        executor.submit(new WithdrawThread(sharedAccount, 500, 3)); // Thread 3: Withdraws 500 every three seconds
        executor.submit(new InterestThread(sharedAccount, 0.02, 5)); // Bonus: Adds 2% interest every 5 seconds

        //Shutdown
        try {
            executor.awaitTermination(60, TimeUnit.SECONDS);
            executor.shutdownNow();
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

    }
}