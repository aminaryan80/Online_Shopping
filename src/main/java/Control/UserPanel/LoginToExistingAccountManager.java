package Control.UserPanel;

import Control.Manager;
import Models.Account.Account;
import Models.Account.Customer;
import Models.Account.Principal;
import Models.Account.Seller;
import View.ErrorProcessor;
import View.UserPanel.CreateNewAccountMenu;
import View.UserPanel.LoginToExistingAccountMenu;

import java.util.ArrayList;

public class LoginToExistingAccountManager extends Manager {
    public LoginToExistingAccountManager(Account account, String username) {
        super(account);
        new LoginToExistingAccountMenu(this, username);
    }

    public boolean canLogin(String username) {
        return Account.hasAccountWithUsername(username);
    }

    public void login(String username, String password) {
        Account account = Account.getAccountByUsername(username);
        if(account.getPassword().equals(password)) {
            this.account = account;
        } else ErrorProcessor.wrongPassword();
    }
}
