package View.CustomerMenus;

import Control.CustomerManagers.CustomerManager;
import Control.CustomerManagers.ViewCartManager;
import Control.CustomerManagers.ViewOrdersManager;
import Control.Manager;
import View.Menu;

import java.util.ArrayList;

public class CustomerMenu extends Menu {

    CustomerManager customerManager = (CustomerManager) manager;

    public CustomerMenu(Manager manager) {
        super(manager);
        customerMenu();
    }

    public void customerMenu() {
        String input;
        while (!(input = scanner.nextLine().trim()).matches("(?i)exit")) {
            if (ConsoleCommand.VIEW_PERSONAL_INFO.getStringMatcher(input).find()) {
                viewPersonalInfo();
            } else if (ConsoleCommand.VIEW_CART.getStringMatcher(input).find()) {
                new ViewCartManager(manager.getAccount());
            } else if (ConsoleCommand.VIEW_ORDERS.getStringMatcher(input).find()) {
                new ViewOrdersManager(manager.getAccount());
            } else if (ConsoleCommand.VIEW_DISCOUNT_CODES.getStringMatcher(input).find()) {
                customerManager.viewDiscountCodes().forEach(System.out::println);
            } else if (ConsoleCommand.VIEW_BALANCE.getStringMatcher(input).find()) {
                customerManager.viewCustomerBalance();
            }
        }
    }

    protected void viewPersonalInfo() {
        manager.viewPersonalInfo();
    }

    private void viewCart() {
        new ViewCartMenu(manager);
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
