import java.util.concurrent.TimeUnit;

/**
 * Represents a thread that periodically adds interest to a BankAccount.
 */
class InterestThread implements Runnable {
    private final BankAccount account;
    private final double interestRate;
    private final int interval;

    public InterestThread(BankAccount account, double interestRate, int interval) {
        this.account = account;
        this.interestRate = interestRate;
        this.interval = interval;
    }

    @Override
    public void run() {
        while (true) {
            account.addInterest(interestRate);
            try {
                TimeUnit.SECONDS.sleep(interval);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}