package Client.Control;

import Client.Control.UserPanel.UserPanelManager;
import Models.Account.Account;

public class MainManager extends Manager {
    public MainManager(Account account) {
        super(account);
        loadFxml(Addresses.MAIN_MENU);
        if (!Account.isPrincipalExists()) {
            new UserPanelManager(account, Addresses.MAIN_MENU, this, true);
        }
    }
}