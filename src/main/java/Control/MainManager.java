package Control;

import Models.Account.Account;
import View.MainMenu;
import View.Menu;

public class MainManager extends Manager {

    MainMenu mainMenu;

    public MainManager(Account account) {
        super(account);
        this.mainMenu = new MainMenu(this);
    }

    public MainMenu getMenu() {
        return mainMenu;
    }

    public String viewPersonalInfo() {
        return account.toString();
    }

    public boolean isDiscountCodeValid(String id) {
return true;
    }
}
