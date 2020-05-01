package Control.UserPanel;

import Control.Manager;
import Models.Account.Account;
import View.ErrorProcessor;
import View.UserPanel.UserPanelMenu;

public class UserPanelManager extends Manager {
    public UserPanelManager(Account account) {
        super(account);
        this.menu = new UserPanelMenu(this);
    }
}
