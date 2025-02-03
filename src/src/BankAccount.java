import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Represents a shared bank account that supports concurrent transactions.
 */
class BankAccount {
    private double balance;
    private final Lock lock = new ReentrantLock();
    private final Condition sufficientFunds = lock.newCondition();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    /**
     * Deposits the specified amount into the bank account.
     *
     * @param amount The amount to deposit.
     */
    public void deposit(double amount, String threadName) {
        lock.lock();
        try {
            balance += amount;
            logTransaction(threadName + " deposited $" + amount + ". New balance: $" + balance);
            sufficientFunds.signalAll(); // Notify waiting threads that funds are available
        } finally {
            lock.unlock();
        }
    }

    /**
     * Withdraws the specified amount from the bank account.
     *
     * @param amount The amount to withdraw.
     */
    public void withdraw(double amount, String threadName) {
        lock.lock();
        try {
            while (balance < amount) {
                logTransaction(threadName + " attempted to withdraw $" + amount + " but failed due to insufficient funds. Current balance: $" + balance);
                sufficientFunds.await(); // Wait until sufficient funds are available
            }
            balance -= amount;
            logTransaction(threadName + " withdrew $" + amount + ". New balance: $" + balance);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logTransaction("Withdrawal operation interrupted.");
        } finally {
            lock.unlock();
        }
    }

    /**
     * Adds interest to the account balance.
     * @param interestRate The interest rate to apply.
     */
    public void addInterest(double interestRate) {
        lock.lock();
        try {
            double interest = balance * interestRate;
            balance += interest;
            logTransaction("Interest of $" + interest + " added. New balance: $" + balance);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Gets the current balance of the bank account.
     *
     * @return The current balance.
     */
    public double getBalance() {
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Logs a transaction with timestamp.
     *
     * @param message The transaction message.
     */
    private void logTransaction(String message) {
        System.out.println(LocalDateTime.now().format(formatter) + " - " + message);
    }
}