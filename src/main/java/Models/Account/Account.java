package Models.Account;

import Models.Address;
import Models.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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
        for (Account account : allAccounts) {
            if (account.username.equals(username))
                return account;
        }
        return null;
    }

    public static boolean hasAccountWithUsername(String username) {
        for (Account account : allAccounts) {
            if (account.username.equals(username))
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
        return firstName + " " + lastName;
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

    public String getUsername() {
        return username;
    }

    public static void open() throws Exception {
        openCustomers();
        openPrincipals();
        openSellers();
    }

    public static void openCustomers() throws Exception {
        File folder = new File(Address.CUSTOMERS.get());
        if (!folder.exists()) folder.mkdirs();
        else {
            for (File file : folder.listFiles()) {
                allAccounts.add(openCustomer(file));
            }
        }
    }

    public static void openSellers() throws Exception {
        File folder = new File(Address.SELLERS.get());
        if (!folder.exists()) folder.mkdirs();
        else {
            for (File file : folder.listFiles()) {
                allAccounts.add(openSeller(file));
            }
        }
    }

    public static void openPrincipals() throws Exception {
        File folder = new File(Address.PRINCIPALS.get());
        if (!folder.exists()) folder.mkdirs();
        else {
            for (File file : folder.listFiles()) {
                allAccounts.add(openPrincipal(file));
            }
        }
    }

    private static Customer openCustomer(File file) throws Exception {
        StringBuilder json = fileToString(file);
        return Gson.INSTANCE.get().fromJson(json.toString(), Customer.class);
    }

    private static Seller openSeller(File file) throws Exception {
        StringBuilder json = fileToString(file);
        return Gson.INSTANCE.get().fromJson(json.toString(), Seller.class);
    }

    private static Principal openPrincipal(File file) throws Exception {
        StringBuilder json = fileToString(file);
        return Gson.INSTANCE.get().fromJson(json.toString(), Principal.class);
    }

    private static StringBuilder fileToString(File file) throws Exception {
        StringBuilder json = new StringBuilder();
        Scanner reader = new Scanner(file);
        while (reader.hasNext()) json.append(reader.next());
        return json;
    }

    public static void save() throws Exception {
        for (Account account : allAccounts) {
            save(account);
        }
    }

    public static void save(Account account) throws Exception {
        if (account instanceof Customer) {
            saveCustomer(account);
        } else if (account instanceof Seller) {
            saveSeller(account);
        } else if (account instanceof Principal) {
            savePrincipal(account);
        }
    }

    private static void savePrincipal(Account account) throws IOException {
        Principal principal = (Principal) account;
        String jsonAccount = Gson.INSTANCE.get().toJson(principal);
        write(account, jsonAccount, Address.PRINCIPALS);
    }

    private static void saveSeller(Account account) throws IOException {
        Seller seller = (Seller) account;
        String jsonAccount = Gson.INSTANCE.get().toJson(seller);
        write(account, jsonAccount, Address.SELLERS);
        return;
    }

    private static void saveCustomer(Account account) throws IOException {
        Customer customer = (Customer) account;
        String jsonAccount = Gson.INSTANCE.get().toJson(customer);
        write(account, jsonAccount, Address.CUSTOMERS);
    }

    private static void write(Account account, String jsonAccount, Address address) throws IOException {
        FileWriter file = new FileWriter(address.get() + "\\" + account.getUsername() + ".json");
        file.write(jsonAccount);
        file.close();
    }
}
