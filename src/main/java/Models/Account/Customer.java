package Models.Account;

import Models.Shop.*;

import java.util.ArrayList;
import java.util.List;

public class Customer extends Account {
    private Cart cart;
    private ArrayList<BuyingLog> allLogs;
    private ArrayList<Discount> discounts;

    public Customer(String username, String firstName, String lastName, String email, String phoneNumber, String password, double balance) {
        super(username, firstName, lastName, email, phoneNumber, password, balance);
        cart = new Cart();
        allLogs = new ArrayList<>();
        discounts = new ArrayList<>();
    }

    public static void deleteProductFromCarts(Product product) {
        for(Account account : allAccounts) {
            try {
                if (((Customer) account).hasProductById(product.getId())) {
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
        for (BuyingLog log : allLogs) {
            if(log.getId().equals(id)){
                return log;
            }
        }
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

    public List<Discount> getDiscounts() {
        return discounts;
    }
}
