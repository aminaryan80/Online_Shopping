package ViewController.userPanel;

import Control.Manager;
import Control.UserPanel.LoginToExistingAccountManager;
import Control.UserPanel.UserPanelManager;
import ViewController.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class UserPanelController extends Controller {
    @FXML
    private TextField userNameField;
    @FXML
    private PasswordField passwordField;

    public void login(ActionEvent actionEvent) {
        String username = userNameField.getText();
        String password = passwordField.getText();
        new LoginToExistingAccountManager(manager.getAccount(), username, password);
        openUserPanel(Manager.Addresses.MAIN_MENU);
    }

    public void openRegister(ActionEvent actionEvent) {
        ((UserPanelManager) manager).openRegister();
    }
}
