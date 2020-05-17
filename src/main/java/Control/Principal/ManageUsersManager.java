package Control.Principal;

import Control.Manager;
import Models.Account.Account;
import Models.Account.Principal;
import View.ErrorProcessor;
import View.Principal.ManageUsersMenu;

import java.util.ArrayList;

public class ManageUsersManager extends Manager {
    public ManageUsersManager(Account account) {
        super(account);
        new ManageUsersMenu(this);
    }

    public ArrayList<String> showUsers() {
        return Account.showAccountsInShort();
    }

    public String viewUsername(String username) {
        if (Account.hasAccountWithUsername(username))
            return Account.getAccountByUsername(username).toString();
        return null;
    }

    public void deleteUsername(String username) {
        if (!username.equals(account.getUsername()))
            Account.deleteAccount(Account.getAccountByUsername(username));
        else ErrorProcessor.cantDeleteYourAccount();
    }

    public void createManagerProfile(ArrayList<String> inputs) {
        // username, password, email, phoneNumber, firstName, lastName
        new Principal(inputs.get(0), inputs.get(4), inputs.get(5), inputs.get(2), inputs.get(3), inputs.get(1));
    }
}
