package Server.Control.Principal;

import Server.Control.Manager;
import Models.Account.Account;
import Models.Shop.Request.Request;

import java.io.IOException;

public class ManageRequestsManager extends Manager {

    public ManageRequestsManager(Account account) {
        super(account);
    }

    public String showRequestDetails(String id) {
        if(Request.hasRequestById(id)) {
            return Request.getRequestById(id).toString();
        }
        return "Not Found";
    }

    public int acceptRequest(String id) {
        if (Request.hasRequestById(id)) {
            try {
                Request.getRequestById(id).accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return 0;
        }
        return 1;
    }

    public int declineRequest(String id) {
        if (Request.hasRequestById(id)) {
            try {
                Request.getRequestById(id).decline();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return 0;
        }
        return 1;
    }
}