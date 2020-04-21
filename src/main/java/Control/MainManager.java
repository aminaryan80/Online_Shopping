package Control;

import Models.Account.Account;
import View.MainMenu;
import View.Menu;

public class MainManager extends Manager {

    public MainManager(Account account, Menu menu) {
        super(account, menu);
    }

    public String viewPersonalInfo() {
        return account.toString();
    }

    public boolean isDiscountCodeValid(String id) {
return true;
    }
}
