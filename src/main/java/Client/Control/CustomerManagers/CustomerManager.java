package Client.Control.CustomerManagers;

import Client.Control.Manager;
import Client.ViewController.Controller;
import Client.ViewController.customer.CustomerController;
import Models.Account.Account;

public class CustomerManager extends Manager {

    public CustomerManager(Account account, Addresses address, Manager manager) {
        super(account, address, manager);
        CustomerController controller = (CustomerController) loadFxml(Addresses.CUSTOMER_MENU);
        update(controller);
    }

    public void update(Controller c) {
        CustomerController controller = (CustomerController) c;
        controller.setCustomer(account);
        controller.init();
    }

    public void openCart() {
        new ViewCartManager(account, Addresses.CUSTOMER_MENU, this);
    }

    public void openOrders() {
        new ViewOrdersManager(account, Addresses.CUSTOMER_MENU, this);
    }
}
