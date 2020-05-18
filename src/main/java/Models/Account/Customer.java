package Models.Account;

import Models.Shop.Cart;
import Models.Shop.Log.BuyingLog;
import Models.Shop.Log.Log;
import Models.Shop.Off.Discount;
import Models.Shop.Product.Product;

import java.util.ArrayList;
import java.util.Arrays;

public class Customer extends Account {
    private final String[] changeableFields = {"password", "email", "firstName", "lastName", "phoneNumber", "balance"};
    private Cart cart;
    private ArrayList<BuyingLog> allLogs;
//    private ArrayList<Discount> discounts;
    private static ArrayList<String> discountsIds= new ArrayList<>();

    public Customer(String username, String firstName, String lastName, String email, String phoneNumber, String password, double balance) {
        super(username, firstName, lastName, email, phoneNumber, password, balance);
        this.cart = new Cart();
        this.allLogs = new ArrayList<>();
        //this.discounts = new ArrayList<>();
        discountsIds = new ArrayList<>();
    }

    public void addDiscount(Discount discount) {
        if(discount != null)
        discountsIds.add(discount.getId());
    }

    public void deleteDiscount(Discount discount) {
        discountsIds.remove(discount.getId());
    }

    public static ArrayList<Discount> getDiscounts(){
        ArrayList<Discount> discounts = new ArrayList<>();
        for (String discountId : discountsIds) {
            discounts.add(Discount.getDiscountById(discountId));
        }
        return discounts;
    }

    public ArrayList<String> getChangeableFields() {
        return new ArrayList<>(Arrays.asList(changeableFields));
    }

    public static void deleteProductFromCarts(Product product) {
        for (Account account : allAccounts) {
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

    @Override
    public String toString() {
        return username + " : \n" +
                "firstName = " + firstName + '\n' +
                "lastName = " + lastName + '\n' +
                "email = " + email + '\n' +
                "phoneNumber = " + phoneNumber + '\n' +
                "password = " + password + '\n' +
                "balance = " + balance;
    }
}
