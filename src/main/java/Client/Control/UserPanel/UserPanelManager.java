package Client.Control.UserPanel;

import Client.Control.Manager;
import Models.Account.Account;
import Models.Account.Customer;
import Models.Shop.Cart;

public class UserPanelManager extends Manager {
    //TODO userPanel manager is handled in manager

    public UserPanelManager(Account account, boolean status) {
        super(account);
        if (status) {
            new CreateNewAccountManager(account, status);
        } else {
            loadFxml(Addresses.USER_PANEL);
        }
    }

    public UserPanelManager(Account account, Addresses address, Manager manager, boolean status) {
        super(account, address, manager);
        if (status) {
            new CreateNewAccountManager(account, status);
        } else {
            loadFxml(Addresses.USER_PANEL);
        }
    }

    public void openRegister() {
        new CreateNewAccountManager(account, false);
    }
}
