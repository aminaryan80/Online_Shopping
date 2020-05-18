package View.CustomerMenus.purchase;

import Control.CustomerManagers.PurchaseManager;
import Control.Manager;
import View.Menu;

public class PurchaseMenu extends Menu {

    protected PurchaseManager purchaseManager = (PurchaseManager) manager;

    public PurchaseMenu(Manager manager) {
        super(manager);
        purchase();
    }

    private void purchase() {
        new ReceiverInformationMenu(manager);
    }
}
