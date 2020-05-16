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

    public String showRequestDetails(String id) {
        return Request.getRequestById(id).toString();
    }
}
