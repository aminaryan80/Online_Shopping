package Models.Account;

import Models.Shop.Discount;

import java.util.ArrayList;
import java.util.List;

public class Account {
    protected static ArrayList<Account> allAccounts = new ArrayList<>();
    protected String username;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String phoneNumber;
    protected String password;
    protected double balance;
    protected boolean isGuest;

    public Account(String username, String firstName, String lastName, String email, String phoneNumber, String password, double balance) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.balance = balance;
        this.isGuest = false;
        allAccounts.add(this);
    }

    public static Account getAccountByUsername(String username) {
        for(Account account : allAccounts) {
            if(account.username.equals(username))
                return account;
        }
        return null;
    }

    public static boolean hasAccountWithUsername(String username) {
        for(Account account : allAccounts) {
            if(account.username.equals(username))
                return true;
        }
        return false;
    }

    public static ArrayList<String> showAccountsInShort() {
        return null;
    }

    public static void deleteAccount(Account account) {
        allAccounts.remove(account);
    }

    public boolean canChangePassword(String currentPassword) {
        return currentPassword.equals(password);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public String getName() {
        return firstName +" "+ lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void payMoney(double money) {
        this.balance = balance - money;
    }

    @Override
    public String toString() {
        return "Account{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                '}';
    }
}
