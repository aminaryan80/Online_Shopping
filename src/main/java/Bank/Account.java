package Bank;

import Models.Gson;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Account {

    private String id;
    private String firstName;
    private String lastName;
    private String userName;
    private String passWord;
    private int money;
    private static Random random = new Random();
    private static ArrayList<Account> accounts = new ArrayList<>();

    public Account(String firstName, String lastName, String userName, String passWord) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.passWord = passWord;
        this.money = 100;
        this.id = String.valueOf(random.nextInt(1000000));
        accounts.add(this);
    }

    public String getId() {
        return id;
    }

    public static boolean isThereAccountWithName(String userName) {
        for (Account account : accounts) {
            if (account.userName.equals(userName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isThereAccountWithId(String id) {
        for (Account account : accounts) {
            if (account.id.equals(id)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPasswordCorrect(String userName, String passWord) {
        if (getAccountByName(userName).passWord.equals(passWord)) {
            return true;
        }
        return false;
    }

    public static Account getAccountByName(String userName) {
        for (Account account : accounts) {
            if (account.userName.equals(userName)) {
                return account;
            }
        }
        return null;
    }

    public static Account getAccountById(String Id) {
        for (Account account : accounts) {
            if (account.id.equals(Id)) {
                return account;
            }
        }
        return null;
    }

    public int getMoney() {
        return money;
    }

    public void pay(int amount)  {
        money += amount;
    }

    public static void open() throws Exception {
        File folder = new File("BankDataBase" + "\\" + "Accounts");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        else {
            for (File file : folder.listFiles()) {
                accounts.add(open(file));
            }
        }
    }

    public static Account open(File file) throws Exception {
        StringBuilder json = new StringBuilder();
        Scanner reader = new Scanner(file);
        while (reader.hasNext()) {
            json.append(reader.next());
        }
        reader.close();
        return Gson.INSTANCE.get().fromJson(json.toString(), Account.class);
    }

    public static void save() throws Exception {
        for (Account account : accounts) {
            save(account);
        }
    }

    public static void save(Account account) throws Exception {
        String jsonAccount = Gson.INSTANCE.get().toJson(account);
        FileWriter file = new FileWriter("BankDataBase" + "\\" + "Accounts" + "\\" + account.getId() + ".json");
        file.write(jsonAccount);
        file.close();
    }
}
