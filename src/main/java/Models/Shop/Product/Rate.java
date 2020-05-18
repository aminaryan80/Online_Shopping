package Models.Shop.Product;

import Control.Identity;
import Models.Account.Account;
import Models.Address;
import Models.Gson;
import Models.Shop.Category.Category;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Rate {
    private Account account;
    private int score;
    private Product product;
    private String id;
    private static ArrayList<Rate> allRates = new ArrayList<>();
    public Rate(Account account, int score, Product product) {
        this.account = account;
        this.score = score;
        this.product = product;
        id = Identity.getId();
        allRates.add(this);
    }

    public static void open() throws Exception {
        File folder = new File(Address.RATES.get());
        if (!folder.exists()) folder.mkdirs();
        else {
            for (File file : folder.listFiles()) {
                allRates.add(open(file));
            }
        }
    }

    public static Rate open(File file) throws Exception {
        StringBuilder json = new StringBuilder();
        Scanner reader = new Scanner(file);
        while (reader.hasNext()) json.append(reader.next());
        reader.close();
        return Gson.INSTANCE.get().fromJson(json.toString(), Rate.class);
    }

    public static void save() throws Exception {
        for (Rate rate : allRates) {
            save(rate);
        }
    }

    public static void save(Rate rate) throws Exception {
        String jsonAccount = Gson.INSTANCE.get().toJson(rate);
        FileWriter file = new FileWriter(Address.RATES.get() + "\\" + rate.getId() + ".json");
        file.write(jsonAccount);
        file.close();
    }

    public static Rate getRateById(String rateId) {
        for (Rate rate : allRates) {
            if(rate.getId().equals(rateId)) return rate;
        }
        return null;
    }

    public String getId() {
        return id;
    }

    public int getScore() {
        return score;
    }
}
