package Control;

import Models.Account.Account;
import View.MainMenu;

public class MainManager extends Manager {

    public MainManager(Account account) {
        super(account);
        this.menu = new MainMenu(this);
    }

    public String viewPersonalInfo() {
        return account.toString();
    }

    public boolean isDiscountCodeValid(String id) {
return true;
    }
}
