package View;

import Control.Manager;
import Control.UserPanel.DashboardManager;
import Control.UserPanel.UserPanelManager;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    protected static Scanner scanner = new Scanner(System.in);
    protected Manager manager;

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    protected Matcher getMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }

    protected void openUserPanel(boolean status) {
        if (manager.getAccount() == null) {
            new UserPanelManager(manager.getAccount());
            if (status)
                return;
        }
        if (manager.getAccount() != null) {
            new DashboardManager(manager.getAccount());
        }
    }

    protected void logout() {
        manager.logout();
    }
}
