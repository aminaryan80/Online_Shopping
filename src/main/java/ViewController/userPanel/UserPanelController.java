package ViewController.userPanel;

import Control.Manager;
import Control.UserPanel.CreateNewAccountManager;
import Control.UserPanel.LoginToExistingAccountManager;
import View.ErrorProcessor;
import ViewController.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.regex.Matcher;

public class UserPanelController extends Controller {
    @FXML
    private TextField userNameField;
    @FXML
    private TextField passwordField;

    public void userPanel() {
        while (true) {
            Matcher matcher;
            String input = scanner.nextLine();
            if ((matcher = getMatcher(input, "^create account (\\S+) (\\S+)$")).find()) {
                createNewAccount(matcher.group(2), matcher.group(1));
            } else if ((matcher = getMatcher(input, "^login (\\S+)$")).find()) {
                loginToExistingAccount(matcher.group(1));
                if (manager.getAccount() != null)
                    return;
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

    public void login(ActionEvent actionEvent) {
        String username = userNameField.getText();
        String password = passwordField.getText();
        new LoginToExistingAccountManager(manager.getAccount(), username, password);
        openUserPanel(false, Manager.Addresses.MAIN_MENU);
    }
}
