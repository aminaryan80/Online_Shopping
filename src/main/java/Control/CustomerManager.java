package Control;

import Models.Account.Account;
import Models.Account.Customer;
import Models.Shop.Product;
import View.CustomerMenu;

import java.util.ArrayList;

public class CustomerManager extends MainManager {

    protected Customer customer = (Customer) account;

    public CustomerManager(Account account) {
        super(account);
        this.menu = new CustomerMenu(this);
    }


    // view cart
    public String viewCart() {
        // show commands
        return null;
    }

    public void viewProductById(String id) {
        // goes to product page
    }

    public boolean hasProductInAuctions(Product product) {
        product.getSeller().hasProductInAuctions(product);
        return true;
    }


    // view orders
    public ArrayList<String> viewOrders() {
        return null;
    }

    public String showOrderById(String id) {
        return null;
    }

    public void rateProduct(String id, int score) {

    }

    // view balance
    public double viewCustomerBalance() {
        return customer.getBalance();
    }

    // view discount codes
    public ArrayList<String> viewDiscountCodes() {
        return customer.g;
    }
}
