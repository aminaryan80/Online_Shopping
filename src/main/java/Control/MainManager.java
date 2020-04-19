package Control;

import Models.Account.Account;
import View.MainMenu;

public class MainManager extends Manager {

    public MainManager(Account account) {
        super(account);
    }

    public String viewPersonalInfo() {
        return account.toString();
    }

    public boolean isDiscountCodeValid(String id) {

    }
}
