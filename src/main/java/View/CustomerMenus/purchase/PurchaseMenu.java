package View.CustomerMenus.purchase;

import Control.CustomerManagers.PurchaseManager;
import Control.Manager;
import View.Menu;

public class PurchaseMenu extends Menu {

    public PurchaseMenu(Manager manager) {
        super(manager);
        purchase();
    }

    private void purchase() {
        new ReceiverInformationMenu(manager);
    }
}
