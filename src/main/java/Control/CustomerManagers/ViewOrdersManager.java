package Control.CustomerManagers;

import Control.Manager;
import Models.Account.Account;
import Models.Account.Customer;
import Models.Shop.Product.Product;
import View.CustomerMenus.customer.ViewOrdersMenu;

public class ViewOrdersManager extends Manager {
    private Customer customer = (Customer) account;
    public ViewOrdersManager(Account account) {
        super(account);
        this.menu = new ViewOrdersMenu(this);
    }

    public String showOrderById(String logId) {
        return customer.getLogById(logId).toString();
    }

    public void rateProduct(String productId, int score) {
        Product product = Product.getProductById(productId);
        product.addRate(customer,score);
    }
}
