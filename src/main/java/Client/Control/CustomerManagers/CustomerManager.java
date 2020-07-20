package Client.Control.CustomerManagers;

import Client.Control.Manager;
import Client.ViewController.Controller;
import Client.ViewController.customer.CustomerController;
import Models.Account.Account;

public class CustomerManager extends Manager {

    public CustomerManager(Account account) {
        super(account);
    }

    public CustomerManager(Account account, Addresses address, Manager manager) {
        super(account, address, manager);
    }

    public void update(Controller c) {
        /*CustomerController controller = (CustomerController) c;
        controller.setCustomer(account);
        controller.init();*/
    }

    public void openOrders() {
        new ViewOrdersManager(account, Addresses.CUSTOMER_MENU, this);
    }
}
