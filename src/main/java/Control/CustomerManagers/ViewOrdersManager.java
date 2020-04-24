package Control.CustomerManagers;

import Control.CustomerManager;
import Models.Account.Account;
import Models.Shop.Product;
import View.CustomerMenus.ViewOrdersMenu;

public class ViewOrdersManager extends CustomerManager {

    public ViewOrdersManager(Account account) {
        super(account);
        this.menu = new ViewOrdersMenu(this);
    }

    public String showOrderById(String logId) {
        return customer.getLogById(logId).toString();
    }

    public void rateProduct(String productId, int score) {
        Product.getProductById(productId).addRate(score);
    }
}
