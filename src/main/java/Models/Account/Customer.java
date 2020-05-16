package Models.Account;

import Models.Shop.*;
import Models.Shop.Log.BuyingLog;
import Models.Shop.Log.Log;
import Models.Shop.Off.Discount;
import Models.Shop.Product.Product;

import java.util.ArrayList;
import java.util.List;

public class Customer extends Account {
    private Cart cart;
    private ArrayList<BuyingLog> allLogs;
    private ArrayList<Discount> discounts;
    private ArrayList<String> discountsId;

    public Customer(String username, String firstName, String lastName, String email, String phoneNumber, String password, double balance) {
        super(username, firstName, lastName, email, phoneNumber, password, balance);
        this.cart = new Cart();
        this.allLogs = new ArrayList<>();
        this.discounts = new ArrayList<>();
        this.discountsId = new ArrayList<>();
    }

    // check this fucking piece of shit later
    public void addDiscount(Discount discount) {
        discounts.add(discount);
        discountsId.add(discount.getId());
    }

    @Override
    protected void loadReference() {
        discounts = new ArrayList<>();
        for (String discountId : discountsId) {
            discounts.add(Discount.getDiscountById(discountId));
        }
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
