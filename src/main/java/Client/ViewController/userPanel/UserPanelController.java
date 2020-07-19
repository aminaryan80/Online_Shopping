package Client.ViewController.userPanel;

import Client.Control.Manager;
import Client.Control.UserPanel.LoginToExistingAccountManager;
import Client.Control.UserPanel.UserPanelManager;
import Client.ViewController.Controller;
import Models.Account.Account;
import Models.Account.Customer;
import Models.Account.Principal;
import Models.Account.Seller;
import Models.Gson;
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
        //new LoginToExistingAccountManager(manager.getAccount(), username, password);
        String response = sendRequest("LOGIN " + username + " " + password);
        String[] res = response.split("&&&");
        accountType = res[0];
        accountUsername = res[1];
        openUserPanel();
    }

    public void openRegister(ActionEvent actionEvent) {
        ((UserPanelManager) manager).openRegister();
    }

    public void back(ActionEvent actionEvent) {
        loadFxml(Manager.Addresses.MAIN_MENU);
    }
}
