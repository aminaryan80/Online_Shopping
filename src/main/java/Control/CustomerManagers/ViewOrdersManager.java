package Control.CustomerManagers;

import Control.Manager;
import Models.Account.Account;
import Models.Account.Customer;
import Models.Shop.Product.Product;
import View.CustomerMenus.customer.ViewOrdersMenu;
import View.ErrorProcessor;

public class ViewOrdersManager extends Manager {
    private Customer customer = (Customer) account;

    public ViewOrdersManager(Account account) {
        super(account);
        this.menu = new ViewOrdersMenu(this);
    }

    public boolean canShowOrderWithId(String logId) {
        return customer.getLogById(logId) != null;
    }

    public String showOrderById(String logId) {
        return customer.getLogById(logId).toString();
    }

    public void rateProduct(String productId, int score) { //TODO RECHECK
        Product product = Product.getProductById(productId);
        if (product != null) product.addRate(customer, score);
        else ErrorProcessor.invalidProductId();
    }
}
