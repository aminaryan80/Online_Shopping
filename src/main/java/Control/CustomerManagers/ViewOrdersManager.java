package Control.CustomerManagers;

import Models.Account.Account;
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
        // TODO: Rate is a Class
    }
}
