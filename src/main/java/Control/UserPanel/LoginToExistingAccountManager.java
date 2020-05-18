package Control.UserPanel;

import Control.Manager;
import Models.Account.Account;
import Models.Account.Customer;
import Models.Shop.Cart;
import View.ErrorProcessor;
import View.UserPanel.LoginToExistingAccountMenu;

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
            Manager.account = account;
            if (Manager.account instanceof Customer)
                Cart.addCartToCustomerCart((Customer) (Manager.account), cart);
        } else ErrorProcessor.wrongPassword();
    }
}