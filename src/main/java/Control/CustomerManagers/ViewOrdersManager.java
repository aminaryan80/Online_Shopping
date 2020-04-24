package Control.CustomerManagers;

import Control.CustomerManager;
import Models.Account.Account;

public class ViewOrdersManager extends CustomerManager {

    public ViewOrdersManager(Account account) {
        super(account);
        this.menu = new ViewOrdersMenu(this);
    }
}
