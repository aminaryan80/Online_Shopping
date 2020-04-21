package Control;

import Models.Account.Account;
import Models.Account.Customer;
import Models.Shop.Product;
import View.MainMenu;
import View.Menu;

import java.util.ArrayList;

public class CustomerManager extends MainManager {

    public CustomerManager(Account account, Menu menu) {
        super(account, menu);
    }

    // edit [field]
    public boolean isEnteredAccountFieldValid(String field) {
return true;
    }

    public void editAccountAttribute(String field, String newAttribute) {

    }

    public boolean isEnteredFieldValid(String type) {
        return true;
    }

    // view cart
    public String viewCart() {
        // show commands
        return null;
    }

    public String showProducts() {
        return null;
    }

    public void viewProductById(String id) {
        // goes to product page
    }

    public void increaseProductQuantity(String id) {

    }

    public void decreaseProductQuantity(String id) {

    }

    public boolean hasProductInAuctions(Product product) {
        product.getSeller().hasProductInAuctions(product);
        return true;
    }

    public double getTotalPrice() {
        return ((Customer) account).getCart().getTotalPrice();
    }

    // purchase
    public void pay(ArrayList<String> receiverInformation, String discountCode) {

    }

    public double canPay() {
        return 1;
    }

    private double getPaymentAmountDiscountApplied() {
        return 1;
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
        return 1;
    }

    // view discount codes
    public ArrayList<String> viewDiscountCodes() {
        return null;
    }
}
