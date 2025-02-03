import java.util.concurrent.TimeUnit;

/**
 * Represents a thread that performs deposit operations on a BankAccount.
 */
class DepositThread implements Runnable {
    private final BankAccount account;
    private final double amount;
    private final int interval;

    public DepositThread(BankAccount account, double amount, int interval) {
        this.account = account;
        this.amount = amount;
        this.interval = interval;
    }

    @Override
    public void run() {
        while (true) {
            account.deposit(amount, Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(interval);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}