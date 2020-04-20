package Control;

import Models.Account.Account;
import View.MainMenu;

public class MainManager extends Manager {

    MainMenu mainMenu;

    public MainManager(Account account) {
        super(account);
        this.mainMenu = new MainMenu(this);
    }

    public String viewPersonalInfo() {
        return account.toString();
    }

    public boolean isDiscountCodeValid(String id) {
return true;
    }
}
