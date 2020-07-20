package Client.Control.UserPanel;

import Client.Control.CustomerManagers.CustomerManager;
import Client.Control.Manager;
import Client.Control.Principal.PrincipalManager;
import Client.Control.Seller.SellerManager;
import Models.Account.Account;
import Models.Account.Customer;
import Models.Account.Principal;
import Models.Account.Seller;

public class DashboardManager extends Manager {
    public DashboardManager(Account account, Addresses address, Manager manager) {
        super(account);
        dashboard(account, address, manager);
    }

    private void dashboard(Account account, Addresses address, Manager manager) {
        if (account instanceof Principal) {
            new PrincipalManager(account, address, manager);
        } else if (account instanceof Customer) {
            new CustomerManager(account, address, manager);
        } else if (account instanceof Seller) {
            new SellerManager(account, address, manager);
        }
    }
}
