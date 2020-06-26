package Control.UserPanel;

import Control.Manager;
import Models.Account.Account;
import ViewController.userPanel.UserPanelController;

public class UserPanelManager extends Manager {
    //TODO userPanel manager is handled in manager
    public UserPanelManager(Account account) {
        super(account);
        loadFxml(Addresses.USER_PANEL);
    }

    public UserPanelManager(Account account, Addresses address, Manager manager, boolean status) {
        super(account, address, manager);
        if (status) {
            new CreateNewAccountManager(account, status);
        } else {
            UserPanelController controller = (UserPanelController) loadFxml(Addresses.USER_PANEL);
        }
    }

    public void openRegister() {
        new CreateNewAccountManager(account, false);
    }
}
