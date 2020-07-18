package Server.RequestProcessor;

import Models.Account.Account;
import Models.Account.Customer;
import Models.Account.Principal;
import Models.Account.Seller;
import Models.Address;
import Models.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class AccountRequestsProcessor extends RequestProcessor {
    private ArrayList<Account> allAccounts = new ArrayList<>();

    public AccountRequestsProcessor() {
        try {
            open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Principal openPrincipal(File file) throws Exception {
        StringBuilder json = fileToString(file);
        return Gson.INSTANCE.get().fromJson(json.toString(), Principal.class);
    }

    private static StringBuilder fileToString(File file) throws Exception {
        StringBuilder json = new StringBuilder();
        Scanner reader = new Scanner(file);
        while (reader.hasNext()) json.append(reader.next());
        reader.close();
        return json;
    }

    public String getAllAccounts() {
        return Gson.INSTANCE.get().toJson(allAccounts);
    }

    public void open() throws Exception {
        openCustomers();
        openPrincipals();
        openSellers();
    }

    public void openCustomers() throws Exception {
        File folder = new File(Address.CUSTOMERS.get());
        if (!folder.exists()) folder.mkdirs();
        else {
            for (File file : folder.listFiles()) {
                allAccounts.add(openCustomer(file));
            }
        }
    }

    public void openSellers() throws Exception {
        File folder = new File(Address.SELLERS.get());
        if (!folder.exists()) folder.mkdirs();
        else {
            for (File file : folder.listFiles()) {
                allAccounts.add(openSeller(file));
            }
        }
    }

    public void openPrincipals() throws Exception {
        File folder = new File(Address.PRINCIPALS.get());
        if (!folder.exists()) folder.mkdirs();
        else {
            for (File file : folder.listFiles()) {
                allAccounts.add(openPrincipal(file));
            }
        }
    }

    private Customer openCustomer(File file) throws Exception {
        StringBuilder json = fileToString(file);
        return Gson.INSTANCE.get().fromJson(json.toString(), Customer.class);
    }

    private Seller openSeller(File file) throws Exception {
        StringBuilder json = fileToString(file);
        return Gson.INSTANCE.get().fromJson(json.toString(), Seller.class);
    }
}
