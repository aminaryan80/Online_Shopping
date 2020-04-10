package Models.Account;

import Models.Shop.Discount;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private static ArrayList<Account> allAccounts = new ArrayList<Account>();
    protected String username;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String phoneNumber;
    protected String password;
    protected double balance;
    protected List<Discount> discounts;

    public static Account getAccountByUsername(String username) {

    }

    public static boolean hasCustomerWithUsername(String username) {

    }

    public ArrayList<String> showAccountsInShort() {

    }

    public boolean canChangePassword(String currentPassword) {
        return currentPassword.equals(password);
    }

    public void setPassword(String password) {
        this.password = password;
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

    public double getBalance() {
        return balance;
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

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public static void deleteAccount(Account account) {

    }
}
