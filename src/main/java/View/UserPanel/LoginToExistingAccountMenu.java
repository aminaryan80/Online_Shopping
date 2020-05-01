package View.UserPanel;

import Control.Manager;
import Control.UserPanel.LoginToExistingAccountManager;
import View.ErrorProcessor;
import View.Menu;

public class LoginToExistingAccountMenu extends Menu {
    public LoginToExistingAccountMenu(Manager manager, String username) {
        super(manager);
        loginExistingAccount(username);
    }

    private void loginExistingAccount(String username) {
        if (((LoginToExistingAccountManager) manager).canLogin(username)) {
            System.out.println("Enter password:");
            ((LoginToExistingAccountManager) manager).login(username, scanner.nextLine());
        } else ErrorProcessor.wrongUsername();
    }
}
