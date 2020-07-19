package Client.ViewController.userPanel;

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
        String response = sendRequest("EDIT_PASSWORD " + accountUsername + " " + currentPasswordField.getText() + " " + newPasswordField.getText());
        if (response.equals("0")) {
            success("Password successfully edited.");
        } else {
            error("Something went wrong.");
        }
        ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
    }
}
