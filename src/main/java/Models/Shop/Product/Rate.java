package Models.Shop.Product;

import Control.Identity;
import Models.Account.Account;

import java.util.ArrayList;

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
