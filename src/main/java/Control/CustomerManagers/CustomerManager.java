package Control.CustomerManagers;

import Control.Manager;
import Models.Account.Account;
import Models.Account.Customer;
import Models.Shop.Off.Discount;
import Models.Shop.Product.Product;
import View.CustomerMenus.customer.CustomerMenu;

import java.util.ArrayList;

public class CustomerManager extends Manager {

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

    // view balance
    public double viewCustomerBalance() {
        return customer.getBalance();
    }

    // view discount codes
    public ArrayList<String> viewDiscountCodes() {
        ArrayList<String> discountsPercentagesAndIds = new ArrayList<>();
        for (Discount discount : customer.getDiscounts()) {
            discountsPercentagesAndIds.add(""+discount.getId()+discount.getDiscountPercent());
        }
        return discountsPercentagesAndIds;
    }
}
