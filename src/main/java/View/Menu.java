package View;

import Control.Manager;
import Control.UserPanel.DashboardManager;
import Control.UserPanel.UserPanelManager;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Menu {
    protected Manager manager;
    protected static Scanner scanner = new Scanner(System.in);

    public Menu(Manager manager) {
        this.manager = manager;
    }

    protected Matcher getMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }

    protected void openUserPanel() {
        if (manager.getAccount() == null)
            new UserPanelManager(manager.getAccount());
        if (manager.getAccount() != null)
            new DashboardManager(manager.getAccount());
    }

}
