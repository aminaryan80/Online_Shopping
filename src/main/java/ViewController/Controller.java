package ViewController;

import Control.Manager;
import Control.UserPanel.DashboardManager;
import Control.UserPanel.UserPanelManager;
import javafx.event.ActionEvent;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    protected static Scanner scanner = new Scanner(System.in);
    protected Manager manager;

    public void init() {

    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    protected Matcher getMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }

    protected void openUserPanel(boolean status) {
        openUserPanel(status, Manager.Addresses.MAIN_MENU);
    }

    protected void openUserPanel(boolean status, Manager.Addresses address) {
        if (manager.getAccount() == null) {
            new UserPanelManager(manager.getAccount(), address, manager);
            if (status)
                return;
        }
        if (manager.getAccount() != null) {
            new DashboardManager(manager.getAccount(), address, manager);
        }
    }

    protected void logout() {
        manager.logout();
    }

    public void back(ActionEvent actionEvent) {
        manager.back();
    }

    public void openUserPanel(ActionEvent actionEvent) {
        openUserPanel(true);
    }

    public void logout(ActionEvent actionEvent) {
        // TODO
    }

}
