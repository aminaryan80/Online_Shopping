package View.UserPanel;

import Control.Manager;
import Control.UserPanel.CreateNewAccountManager;
import Control.UserPanel.LoginToExistingAccountManager;
import View.ErrorProcessor;
import View.Menu;

import java.util.regex.Matcher;

public class UserPanelMenu extends Menu {

    public UserPanelMenu(Manager manager) {
        super(manager);
        userPanel();
    }

    public void userPanel() {
        while (true) {
            Matcher matcher;
            String input = scanner.nextLine();
            if ((matcher = getMatcher(input, "^create account (\\S+) (\\S+)$")).find()) {
                createNewAccount(matcher.group(2), matcher.group(1));
            } else if ((matcher = getMatcher(input, "^login (\\S+)$")).find()) {
                loginToExistingAccount(matcher.group(1));
                if(manager.getAccount() != null)
                    return;
            } else if (getMatcher(input, "^help$").find()) {
                help();
            } else if (getMatcher(input, "^back$").find()) {
                return;
            } else System.out.println("invalid command");
        }
    }

    private void createNewAccount(String username, String type) {
        if (type.equals("principal") && manager.isPrincipalExists())
            ErrorProcessor.principalExists();
        else new CreateNewAccountManager(manager.getAccount(), username, type);
    }

    private void loginToExistingAccount(String username) {
        new LoginToExistingAccountManager(manager.getAccount(), username);
    }

    private void help() {
        System.out.println("create account [type] [username]\n" +
                "-types : (customer|seller|principal)\n" +
                "login [username]\n" +
                "help\n" +
                "back");
    }
}
