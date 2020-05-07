package Models.Account;

import Models.Address;
import Models.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
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

    public String getUsername() {
        return username;
    }

    public static void open(){
        File folder = new File(Address.ACCOUNTS.get());
        if(!folder.exists()) folder.mkdir();
        else {
            for (File file : folder.listFiles()) {
                StringBuilder json = new StringBuilder();
                try {
                    Scanner reader = new Scanner(file);
                    while (reader.hasNext()) {
                        json.append(reader.next());
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                allAccounts.add(Gson.INSTANCE.get().fromJson(json.toString(),Account.class));
            }
        }
    }

    public static void save(){
        for (Account account : allAccounts) {
            try {
                String jsonAccount = Gson.INSTANCE.get().toJson(account);
                System.out.println("created");
                try {
                    FileWriter file = new FileWriter(Address.ACCOUNTS.get() +"\\"+account.getUsername()+".json");
                    file.write(jsonAccount);
                    file.close();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
