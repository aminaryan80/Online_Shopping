package Models.Account;

import Models.Shop.BuyingLog;
import Models.Shop.Cart;
import Models.Shop.Log;
import Models.Shop.Product;

import java.util.ArrayList;

public class Customer extends Account {
    private Cart cart;
    private ArrayList<BuyingLog> allLogs;

    public Customer(String username, String firstName, String lastName, String email, String phoneNumber, String password, double balance) {
        super(username, firstName, lastName, email, phoneNumber, password, balance);

    }

    public static void deleteProductFromCarts(Product product) {
        for(Account account : allAccounts) {
            try {
                if(((Customer) account).hasProductById(product.getId())) {
                    ((Customer) account).getCart().deleteProduct(product);
                }
            } catch (Exception ignored) {

            }
        }
    }

    public ArrayList<String> viewLogsInShort() {
        return null;
    }

    public Log getLogById(String id) {
        return null;
    }

    public Cart getCart() {
        return cart;
    }

    public boolean hasProductById(String id) {
     return true;
    }

    public void addLog(BuyingLog buyingLog) {
        allLogs.add(buyingLog);
    }
}
