package Client.ViewController.userPanel;

import Client.Control.EditPasswordManager;
import Client.ViewController.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditPasswordController extends Controller {

    @FXML
    private TextField currentPasswordField;
    @FXML
    private TextField newPasswordField;

    public void editPassword(ActionEvent actionEvent) {
        ((EditPasswordManager) manager).editPassword(currentPasswordField.getText(), newPasswordField.getText());
        ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
    }
}
