package Control.UserPanel;

import Control.Manager;
import Models.Account.Account;
import Models.Account.Customer;
import Models.Shop.Cart;

public class LoginToExistingAccountManager extends Manager {
    public LoginToExistingAccountManager(Account account, String username, String password) {
        super(account);
        if (canLogin(username)) {
            login(username, password);
        } else error("Wrong username.");
    }

    public boolean canLogin(String username) {
        return Account.hasAccountWithUsername(username);
    }

    public void login(String username, String password) {
        Account account = Account.getAccountByUsername(username);
        if (account.getPassword().equals(password)) {
            success("You logged in successfully.");
            Manager.account = account;
            if (Manager.account instanceof Customer) {
                Cart.addCartToCustomerCart((Customer) (Manager.account), cart);
                cart = ((Customer) account).getCart();
            }
        } else error("Wrong password.");
    }
}