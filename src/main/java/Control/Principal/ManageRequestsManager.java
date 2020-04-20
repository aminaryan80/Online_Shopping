package Control.Principal;

import Control.Manager;
import Models.Account.Account;
import Models.Shop.Request;
import View.Principal.ManageRequestsMenu;
import View.Principal.ManageUsersMenu;

public class ManageRequestsManager extends Manager {
    public ManageRequestsManager(Account account) {
        super(account);
        new ManageRequestsMenu(this);
    }

    public String showRequestDetails(String id) {
        return Request.getRequestById(id).toString();
    }
}
