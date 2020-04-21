package View;

import Control.CustomerManager;
import Control.Manager;

import java.util.ArrayList;

public class CustomerMenu extends MainMenu {

    public CustomerMenu(Manager manager) {
        super(manager);
    }

    public void customerMenu() {

    }

    protected void viewPersonalInfo() {
        new ViewPersonalInfoCustomer(manager);
    }

    private void viewCart() {
        new ViewCart(manager);
    }

    private void showProducts() {

    }

    private void viewProductById(String id) {

    }

    private void increaseProductQuantity(String id) {

    }

    private void decreaseProductQuantity(String id) {

    }

    private void showTotalPrice() {

    }

    private void purchase() {

    }

    private ArrayList<String> getReceiverInformation() {
        return null;
    }

    private String getBuyerDiscountCode() {
        return null;
    }

    private void viewOrders() {

    }

    private void showOrderById(String id) {

    }

    private void rateProduct(String id, int score) {

    }

    private void viewBalance() {

    }

    private void viewDiscountCodes() {

    }
}
