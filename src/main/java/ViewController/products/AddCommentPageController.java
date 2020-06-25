package ViewController.products;

import Control.CustomerManagers.ProductPageManager;
import ViewController.Controller;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddCommentPageController extends Controller {
    public TextField commentField;
    public TextField titleField;

    public void confirmComment(ActionEvent actionEvent) {
        if (titleField.getText().isEmpty() || commentField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Fill the fields then confirm!", ButtonType.OK);
            alert.show();
        } else {
            ((ProductPageManager) manager).addComment(titleField.getText(), commentField.getText());
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    public void clearFields(ActionEvent actionEvent) {
        titleField.setText("");
        commentField.setText("");
    }

    public void cancel(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
