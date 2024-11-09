import java.util.ArrayList;
import java.util.Scanner;

class Account {
    private String username;
    private String password;
    private double balance;
    private int failedLoginAttempts;
    private boolean isLocked;
    private ArrayList<String> transactionHistory;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        this.balance = 0.0;
        this.failedLoginAttempts = 0;
        this.isLocked = false;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public boolean validatePassword(String password) {
        return this.password.equals(password);
    }

    public double getBalance() {
        return balance;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void lockAccount() {
        isLocked = true;
    }

    public void unlockAccount() {
        isLocked = false;
        failedLoginAttempts = 0;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add("Deposited: $" + amount);
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactionHistory.add("Withdrew: $" + amount);
            return true;
        } else {
            System.out.println("Insufficient balance.");
            return false;
        }
    }

    public void displayTransactionHistory() {
        System.out.println("Transaction History for " + username + ":");
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            for (String transaction : transactionHistory) {
                System.out.println(transaction);
            }
        }
    }

    public void applyInterest(double interestRate) {
        double interest = balance * interestRate;
        balance += interest;
        transactionHistory.add("Interest applied: $" + interest);
    }

    public boolean requestLoan(double loanAmount) {
        if (loanAmount <= 5000) {
            balance += loanAmount;
            transactionHistory.add("Loan approved: $" + loanAmount);
            return true;
        } else {
            System.out.println("Loan amount exceeds limit.");
            return false;
        }
    }
}

public class BankingApplication {
    private static ArrayList<Account> accounts = new ArrayList<>();
    private static Account loggedInAccount = null;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to the Banking Application!");

        accounts.add(new Account("Kavin", "102"));
        accounts.add(new Account("Mugesh", "126"));
        accounts.add(new Account("Kirubha", "106"));
        accounts.add(new Account("Muralis", "132"));

        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    System.out.println("Exiting the application.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        simulateLogin(username, password);
        if (loggedInAccount != null) {
            accountMenu();
        }
    }

    private static void register() {
        System.out.print("Enter a new username: ");
        String username = scanner.nextLine();

        if (findAccountByUsername(username) != null) {
            System.out.println("Username already exists. Try again.");
            return;
        }

        System.out.print("Enter a new password: ");
        String password = scanner.nextLine();

        accounts.add(new Account(username, password));
        System.out.println("Account registered successfully!");
    }

    private static void simulateLogin(String username, String password) {
        Account account = findAccountByUsername(username);
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        if (account.isLocked()) {
            System.out.println("Your account is locked due to multiple failed login attempts.");
            return;
        }

        int attempts = 0;
        while (attempts < 3) {
            if (account.validatePassword(password)) {
                System.out.println(username + " login successful!");
                loggedInAccount = account;
                return;
            } else {
                System.out.println("Incorrect password for " + username + ".");
                attempts++;
            }
        }

        account.lockAccount();
        System.out.println(username + "'s account is locked after 3 failed attempts.");
    }

    private static void accountMenu() {
        while (true) {
            System.out.println("\nAccount Menu for " + loggedInAccount.getUsername());
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. View Transaction History");
            System.out.println("5. Apply Interest");
            System.out.println("6. Request Loan");
            System.out.println("7. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    deposit();
                    break;
                case 2:
                    withdraw();
                    break;
                case 3:
                    checkBalance();
                    break;
                case 4:
                    viewTransactionHistory();
                    break;
                case 5:
                    applyInterest();
                    break;
                case 6:
                    requestLoan();
                    break;
                case 7:
                    logout();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void deposit() {
        System.out.print("Enter deposit amount: ");
        double amount = scanner.nextDouble();
        loggedInAccount.deposit(amount);
        System.out.println("Deposited $" + amount + " successfully.");
    }

    private static void withdraw() {
        System.out.print("Enter withdraw amount: ");
        double amount = scanner.nextDouble();
        if (loggedInAccount.withdraw(amount)) {
            System.out.println("Withdrew $" + amount + " successfully.");
        }
    }

    private static void checkBalance() {
        System.out.println("Current balance for " + loggedInAccount.getUsername() + ": $" + loggedInAccount.getBalance());
    }

    private static void viewTransactionHistory() {
        loggedInAccount.displayTransactionHistory();
    }

    private static void applyInterest() {
        System.out.print("Enter interest rate (as a percentage): ");
        double interestRate = scanner.nextDouble() / 100;
        loggedInAccount.applyInterest(interestRate);
        System.out.println("Interest applied successfully.");
    }

    private static void requestLoan() {
        System.out.print("Enter loan amount: ");
        double loanAmount = scanner.nextDouble();
        if (loggedInAccount.requestLoan(loanAmount)) {
            System.out.println("Loan of $" + loanAmount + " granted.");
        }
    }

    private static void logout() {
        loggedInAccount = null;
        System.out.println("Logged out successfully.");
    }

    private static Account findAccountByUsername(String username) {
        for (Account account : accounts) {
            if (account.getUsername().equals(username)) {
                return account;
            }
        }
        return null;
    }
}
