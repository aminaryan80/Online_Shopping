package Models.Account;

import Models.Shop.BuyingLog;
import Models.Shop.Cart;

import java.util.ArrayList;

public class Customer extends Account {
    private Cart cart;
    private ArrayList<BuyingLog> allLogs;

    public Cart getCart() {
        return cart;
    }
}
