# ATM Banking System Simulation

This Java project simulates a basic banking system with an ATM, demonstrating concurrent transactions, thread synchronization, and basic banking operations like deposits, withdrawals, and interest calculation.

## Features

*   **Concurrent Transactions:** Supports multiple threads simulating users depositing and withdrawing money simultaneously.
*   **Thread Synchronization:** Uses `ReentrantLock` and `Condition` to ensure that transactions are thread-safe and that withdrawals are blocked when there are insufficient funds.
*   **Logging:** Tracks each transaction (deposit, withdrawal, interest addition) with timestamps and relevant details (thread name, amount, balance).
*   **Interest Calculation:** A separate thread periodically adds interest to the account balance.
*   **Testable:** Includes a comprehensive JUnit 5 test suite to verify the correctness of the banking operations under various scenarios, including concurrent access.

## Project Structure

The project consists of the following classes:

*   **`BankAccount`:** Represents the shared bank account. It handles deposits, withdrawals, interest calculations, and maintains the account balance. It uses synchronization mechanisms to ensure thread safety.
*   **`DepositThread`:** Represents a thread that repeatedly deposits a fixed amount into the bank account at a specified interval.
*   **`WithdrawThread`:** Represents a thread that repeatedly withdraws a fixed amount from the bank account at a specified interval. It handles waiting when there are insufficient funds.
*   **`InterestThread`:** Represents a thread that periodically adds interest to the bank account balance.
*   **`BankingSimulation`:** The main class that sets up the simulation, creates the bank account and threads, and starts the simulation.
*   **`BankingSimulationTest`:** A JUnit 5 test suite that thoroughly tests the `BankAccount` class, including concurrent transaction scenarios.

## Code Principles Followed

*   **DRY (Don't Repeat Yourself):** Common logic is encapsulated in appropriate classes (e.g., transaction logic in `DepositThread` and `WithdrawThread`).
*   **KISS (Keep It Simple, Stupid):** The code is designed to be straightforward and easy to understand.
*   **Single Responsibility Principle:** Each class has a well-defined responsibility (e.g., `BankAccount` manages the account, `DepositThread` handles deposits).

## Prerequisites

*   Java Development Kit (JDK) 8 or higher.
*   A Java IDE (e.g., IntelliJ IDEA, Eclipse) or a build tool like Maven or Gradle (optional, but recommended for dependency management).

## How to Run

1.  **Clone the repository:**

    ```bash
    git clone <https://github.com/heev7777/ATM-Withdraw-Threads.git>
    ```

2.  **Compile the code:**

    *   **Using an IDE:** Import the project into your IDE and build it.

3.  **Run the simulation:**

    *   **Using an IDE:** Locate the `BankingSimulation` class and run its `main` method.

4.  **Run the tests:**

    *   **Using an IDE:** Locate the `BankingSimulationTest` class, right-click, and select "Run 'BankingSimulationTest'".

## Output

When you run the `BankingSimulation`, you will see output similar to this in the console:

```declarative
2025-02-03 12:01:10 - pool-1-thread-1 deposited $300.0. New balance: $1300.0
2025-02-03 12:01:10 - pool-1-thread-2 withdrew $200.0. New balance: $1100.0
2025-02-03 12:01:11 - pool-1-thread-1 deposited $300.0. New balance: $1400.0
2025-02-03 12:01:12 - pool-1-thread-2 withdrew $200.0. New balance: $1200.0
2025-02-03 12:01:12 - pool-1-thread-1 deposited $300.0. New balance: $1500.0
2025-02-03 12:01:13 - pool-1-thread-1 deposited $300.0. New balance: $1800.0
2025-02-03 12:01:14 - pool-1-thread-2 withdrew $200.0. New balance: $1600.0
2025-02-03 12:01:14 - pool-1-thread-1 deposited $300.0. New balance: $1900.0
2025-02-03 12:01:15 - pool-1-thread-1 deposited $300.0. New balance: $2200.0
2025-02-03 12:01:16 - pool-1-thread-2 withdrew $200.0. New balance: $2000.0
2025-02-03 12:01:16 - pool-1-thread-1 deposited $300.0. New balance: $2300.0
2025-02-03 12:01:17 - pool-1-thread-1 deposited $300.0. New balance: $2600.0
2025-02-03 12:01:18 - pool-1-thread-2 withdrew $200.0. New balance: $2400.0
2025-02-03 12:01:18 - pool-1-thread-1 deposited $300.0. New balance: $2700.0
```