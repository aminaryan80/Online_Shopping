package Server.Control.UserPanel;

import Server.Control.Manager;
import Models.Account.Account;
import Models.Account.Customer;
import Models.Account.Principal;
import Models.Account.Seller;
import Models.Shop.Cart;

public class LoginToExistingAccountManager extends Manager {
    public LoginToExistingAccountManager(Account account) {
        super(account);
    }

    public boolean canLogin(String username) {
        return Account.hasAccountWithUsername(username);
    }

    public String login(String username, String password) {
        if (username != null && password != null) {
            if (canLogin(username)) {
                Account account = Account.getAccountByUsername(username);
                if (account.getPassword().equals(password)) {
                    Manager.account = account;
                    if (Manager.account instanceof Customer) {
                        Cart.addCartToCustomerCart((Customer) (Manager.account), cart);
                        cart = ((Customer) account).getCart();
                    }
                    if (account instanceof Principal) {
                        return "P&&&" + account.getUsername();
                    } else if (account instanceof Customer) {
                        return "C&&&" + account.getUsername();
                    } else if (account instanceof Seller) {
                        return "S&&&" + account.getUsername();
                    }
                    return null;
                } else return "2";
            } else return "1";
        } else return "3";
    }
}