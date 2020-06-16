package Control.Principal;

import Control.Manager;
import Models.Account.Account;
import Models.Shop.Request.Request;
import View.Principal.ManageRequestsMenu;

public class ManageRequestsManager extends Manager {
    public ManageRequestsManager(Account account) {
        super(account);
        new ManageRequestsMenu(this);
    }

    public ManageRequestsManager(Account account, Addresses address, Manager manager) {
        super(account, address, manager);
        //new ManageRequestsMenu(this);
        loadFxml(Addresses.MANAGE_REQUESTS);
    }

    public String showRequestDetails(String id) {
        return Request.getRequestById(id).toString();
    }
}