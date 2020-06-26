package Control;

import Control.UserPanel.UserPanelManager;
import Models.Account.Account;
import ViewController.MainController;

public class MainManager extends Manager {

    /*public MainManager(Account account) {
        super(account);
        //this.menu = new MainMenu(this);
    }*/

    public MainManager(Account account) {
        super(account);
        MainController mainController = (MainController) loadFxml(Addresses.MAIN_MENU);
        if (!Account.isPrincipalExists()) {
            new UserPanelManager(account, Addresses.MAIN_MENU, this, true);
        }
    }
}