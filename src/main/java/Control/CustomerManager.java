package Control;

import Models.Account.Account;
import Models.Account.Customer;
import Models.Shop.Product;
import View.CustomerMenu;
import View.ErrorProcessor;
import View.MainMenu;
import View.Menu;

import java.util.ArrayList;
import java.util.List;

public class CustomerManager extends MainManager {

    Customer customer = (Customer) account;

    public CustomerManager(Account account) {
        super(account);
        this.menu = new CustomerMenu(this);
    }

    // edit [field]
    public boolean isEnteredAccountFieldValid(String field) {
        //if(field.matches("(?i)(first name|last name|"))
        return true;
    }

    public void editAccountAttribute(String field, String newAttribute) {

    }

    public boolean isNewFieldAcceptable(String field, String newAttribute) {
        return true;
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
        List<String> productsInfos = customer.getCart().showProductsInShort();
        String allProductsInfo = "";
        for (String productsInfo : productsInfos) {
            allProductsInfo += productsInfo;
        }
        return allProductsInfo;
    }

    public void viewProductById(String id) {
        // goes to product page
    }

    public void ProductQuantity(String id,boolean isIncrease) {
        Product product;
        if ((product = Product.getProductById(id)) != null) {
            if(isIncrease)
            customer.getCart().addProduct(product);
            else customer.getCart().deleteProduct(product);
        } else ErrorProcessor.invalidProductId();
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
