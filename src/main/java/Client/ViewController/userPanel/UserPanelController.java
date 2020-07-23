package Client.ViewController.userPanel;

import Client.Control.Manager;
import Client.ViewController.Controller;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class UserPanelController extends Controller {
    public TextField userNameField;
    public PasswordField passwordField;

    public void login(ActionEvent actionEvent) {
        String username = userNameField.getText();
        String password = passwordField.getText();
        String response = sendRequest("LOGIN " + username + " " + password);
        if(!response.equals("1") && !response.equals("2")) {
            String[] res = response.split("&&&");
            accountType = res[0];
            accountUsername = res[1];
        } else error("Username or Password is wrong.");
        openUserPanel();
    }

    public void openRegister(ActionEvent actionEvent) {
        loadFxml(Manager.Addresses.REGISTER, true);
    }

    public void back(ActionEvent actionEvent) {
        loadFxml(Manager.Addresses.MAIN_MENU);
    }
}
