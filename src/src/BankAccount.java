public class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public synchronized void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited: " + amount + ", Current balance: " + balance);
        notifyAll();
    }

    public synchronized void withdraw(double amount) {
        while (balance < amount) {
            try {
                System.out.println("Waiting to withdraw: " + amount + ", Current balance: " + balance);
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        balance -= amount;
        System.out.println("Withdrew: " + amount + ", Current balance: " + balance);
    }

    public synchronized double getBalance() {
        return balance;
    }
}