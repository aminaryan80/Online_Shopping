package View.CustomerMenus;

import Control.CustomerManagers.PurchaseManager;
import Control.Manager;
import View.CustomerMenu;

public class PurchaseMenu extends CustomerMenu {

    protected PurchaseManager purchaseManager = (PurchaseManager) manager;
    public PurchaseMenu(Manager manager) {
        super(manager);
        purchase();
    }

    private void purchase() {
        new ReceiverInformationMenu(manager);
    }
}
