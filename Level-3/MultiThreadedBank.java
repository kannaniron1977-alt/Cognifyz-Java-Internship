import java.util.concurrent.locks.ReentrantLock;

/**
 * Level 3 - Task 2: Multi-threaded Application
 * Shows a race condition (shared counter WITHOUT protection) and two fixes:
 *   1. synchronized method
 *   2. ReentrantLock
 * Then a bank account shared by several threads.
 */
public class MultiThreadedBank {

    // ---- Part 1: shared counters ----
    static int unsafeCounter = 0;               // no protection -> wrong result
    static int syncCounter = 0;                 // protected with synchronized
    static int lockCounter = 0;                 // protected with ReentrantLock
    static final ReentrantLock counterLock = new ReentrantLock();

    static void unsafeIncrement() { unsafeCounter++; }

    static synchronized void syncIncrement() { syncCounter++; }

    static void lockIncrement() {
        counterLock.lock();
        try {
            lockCounter++;
        } finally {
            counterLock.unlock(); // always unlock, even if something goes wrong
        }
    }

    // ---- Part 2: bank account ----
    static class BankAccount {
        private int balance;
        private final ReentrantLock lock = new ReentrantLock();

        BankAccount(int balance) { this.balance = balance; }

        void deposit(int amount, String who) {
            lock.lock();
            try {
                balance += amount;
                System.out.println(who + " deposited " + amount + " -> balance " + balance);
            } finally {
                lock.unlock();
            }
        }

        void withdraw(int amount, String who) {
            lock.lock();
            try {
                if (balance >= amount) {
                    balance -= amount;
                    System.out.println(who + " withdrew " + amount + " -> balance " + balance);
                } else {
                    System.out.println(who + " tried to withdraw " + amount + " but only " + balance + " available");
                }
            } finally {
                lock.unlock();
            }
        }

        int getBalance() { return balance; }
    }

    static void runThreads(int count, Runnable task) throws InterruptedException {
        Thread[] threads = new Thread[count];
        for (int i = 0; i < count; i++) {
            threads[i] = new Thread(task);
            threads[i].start();
        }
        for (Thread t : threads) {
            t.join(); // wait for every thread to finish
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int threads = 4;
        int loops = 100_000;
        int expected = threads * loops;

        System.out.println("=== Part 1: Shared counter (" + threads + " threads x " + loops + " increments) ===");
        System.out.println("Expected value     : " + expected);

        runThreads(threads, () -> { for (int i = 0; i < loops; i++) unsafeIncrement(); });
        System.out.println("No protection      : " + unsafeCounter + (unsafeCounter == expected ? "" : "   <-- wrong! race condition"));

        runThreads(threads, () -> { for (int i = 0; i < loops; i++) syncIncrement(); });
        System.out.println("synchronized       : " + syncCounter);

        runThreads(threads, () -> { for (int i = 0; i < loops; i++) lockIncrement(); });
        System.out.println("ReentrantLock      : " + lockCounter);

        System.out.println();
        System.out.println("=== Part 2: Bank account shared by 3 threads ===");
        BankAccount account = new BankAccount(1000);

        Thread alice = new Thread(() -> {
            for (int i = 0; i < 3; i++) account.deposit(200, "Alice");
        });
        Thread bob = new Thread(() -> {
            for (int i = 0; i < 3; i++) account.withdraw(300, "Bob");
        });
        Thread carol = new Thread(() -> {
            for (int i = 0; i < 3; i++) account.withdraw(500, "Carol");
        });

        alice.start();
        bob.start();
        carol.start();
        alice.join();
        bob.join();
        carol.join();

        System.out.println("Final balance: " + account.getBalance());
    }
}
