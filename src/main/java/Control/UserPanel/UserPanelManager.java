package Control.UserPanel;

import Control.Manager;
import Models.Account.Account;
import ViewController.userPanel.UserPanelController;

public class UserPanelManager extends Manager {
    //TODO userPanel manager is handled in manager
    public UserPanelManager(Account account) {
        super(account);
        //this.menu = new UserPanelMenu(this);
        loadFxml(Addresses.USER_PANEL);
    }

    public UserPanelManager(Account account, Addresses address, Manager manager) {
        super(account, address, manager);
        UserPanelController controller = (UserPanelController) loadFxml(Addresses.USER_PANEL);
    }
}
