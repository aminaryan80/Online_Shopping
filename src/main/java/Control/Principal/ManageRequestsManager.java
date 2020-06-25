package Control.Principal;

import Control.Manager;
import Models.Account.Account;
import Models.Shop.Request.Request;
import View.ErrorProcessor;
import View.Principal.ManageRequestsMenu;

import java.io.IOException;

public class ManageRequestsManager extends Manager {
    public ManageRequestsManager(Account account) {
        super(account);
        new ManageRequestsMenu(this);
    }

    public ManageRequestsManager(Account account, Addresses address, Manager manager) {
        super(account, address, manager);
        loadFxml(Addresses.MANAGE_REQUESTS);
    }

    public String showRequestDetails(String id) {
        return Request.getRequestById(id).toString();
    }

    public void acceptRequest(String id) {
        if (Request.hasRequestById(id)) {
            try {
                Request.getRequestById(id).accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else ErrorProcessor.invalidRequestId();
    }

    public void declineRequest(String id) {
        if (Request.hasRequestById(id)) {
            try {
                Request.getRequestById(id).decline();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else ErrorProcessor.invalidRequestId();
    }
}