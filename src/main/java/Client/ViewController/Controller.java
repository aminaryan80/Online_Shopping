package Client.ViewController;

import Client.Control.Manager;
import Client.Control.UserPanel.DashboardManager;
import Client.Control.UserPanel.UserPanelManager;
import javafx.event.ActionEvent;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    protected Manager manager;

    public void init() {

    }

    public void initTable(ArrayList<Object> tableObjects) {

    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    protected Matcher getMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }

    protected void openUserPanel() {
        openUserPanel(Manager.Addresses.MAIN_MENU);
    }

    protected void openUserPanel(Manager.Addresses address) {
        if (manager.getAccount() == null) {
            new UserPanelManager(manager.getAccount(), address, manager, false);
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
        openUserPanel();
    }

    public void logout(ActionEvent actionEvent) {
        logout();
        System.out.println("LOGOUT");
    }

}
