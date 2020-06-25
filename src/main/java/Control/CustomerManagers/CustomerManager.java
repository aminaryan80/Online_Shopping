package Control.CustomerManagers;

import Control.Manager;
import Models.Account.Account;
import Models.Account.Customer;
import Models.Shop.Off.Discount;
import View.CustomerMenus.customer.CustomerMenu;
import ViewController.Controller;
import ViewController.customer.CustomerController;

import java.util.ArrayList;

public class CustomerManager extends Manager {

    private Customer customer = (Customer) account;

    public CustomerManager(Account account) {
        super(account);
        this.menu = new CustomerMenu(this);
    }

    public CustomerManager(Account account, Addresses address, Manager manager) {
        super(account, address, manager);
        //this.menu = new CustomerMenu(this);
        CustomerController controller = (CustomerController) loadFxml(Addresses.CUSTOMER_MENU);
        update(controller);
    }

    public void update(Controller c) {
        CustomerController controller = (CustomerController) c;
        controller.setCustomer(account);
        controller.init();
    }

    // view cart
//    public String viewCart() {
//        // show commands
//        return null;
//    }

//    public void viewProductById(String id) {
//        // goes to product page
//    }

//    public boolean hasProductInAuctions(Product product) {
//        product.getSeller().hasProductInAuctions(product);
//        return true;
//    } //TODO find out where was this needed?

    // view orders
//    public ArrayList<String> viewOrders() {
//        return null;
//    }

    // view balance
    public double viewCustomerBalance() {
        return customer.getBalance();
    }

    // view discount codes
    public ArrayList<String> viewDiscountCodes() {
        ArrayList<String> discountsPercentagesAndIds = new ArrayList<>();
        for (Discount discount : customer.getDiscounts()) {
            discountsPercentagesAndIds.add("" + discount.getId() + ": " + discount.getDiscountPercent());
        }
        return discountsPercentagesAndIds;
    }

    public void openCart() {
        new ViewCartManager(account, Addresses.CUSTOMER_MENU, this);
    }

    public void openOrders() {
        new ViewOrdersManager(account, Addresses.CUSTOMER_MENU, this);
    }
}
