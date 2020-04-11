package Models.Account;

import Models.Shop.BuyingLog;
import Models.Shop.Cart;
import Models.Shop.Log;

import java.util.ArrayList;

public class Customer extends Account {
    private Cart cart;
    private ArrayList<BuyingLog> allLogs;

    public ArrayList<String> viewLogsInShort() {

    }

    public Log getLogById(String id) {

    }

    public Cart getCart() {
        return cart;
    }

    public boolean hasProductById(String id) {

    }
}
