package ViewController.userPanel;

import Control.EditPasswordManager;
import Models.Account.Account;
import ViewController.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditPasswordController extends Controller {

    private Account account;
    @FXML
    private TextField currentPasswordField;
    @FXML
    private TextField newPasswordField;

    public void setAccount(Account account) {
        this.account = account;
    }

    public void editPassword(ActionEvent actionEvent) {
        ((EditPasswordManager) manager).editPassword(currentPasswordField.getText(), newPasswordField.getText());
        ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
    }
}
