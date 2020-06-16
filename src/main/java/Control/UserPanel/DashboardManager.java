package Control.UserPanel;

import Control.CustomerManagers.CustomerManager;
import Control.Manager;
import Control.Principal.PrincipalManager;
import Control.Seller.SellerManager;
import Models.Account.Account;
import Models.Account.Customer;
import Models.Account.Principal;
import Models.Account.Seller;

public class DashboardManager extends Manager {
    public DashboardManager(Account account) {
        super(account);
        //dashboard(account);
    }

    public DashboardManager(Account account, Addresses address, Manager manager) {
        super(account);
        dashboard(account, address, manager);
    }

    private void dashboard(Account account, Addresses address, Manager manager) {
        if (account instanceof Principal) {
            new PrincipalManager(account, address, manager);
        } else if (account instanceof Customer) {
            new CustomerManager(account);
        } else if (account instanceof Seller) {
            new SellerManager(account);
        }
    }
}
