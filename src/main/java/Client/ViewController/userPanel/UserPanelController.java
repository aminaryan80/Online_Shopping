package Client.ViewController.userPanel;

import Client.Control.Manager;
import Client.Control.UserPanel.LoginToExistingAccountManager;
import Client.Control.UserPanel.UserPanelManager;
import Client.ViewController.Controller;
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
