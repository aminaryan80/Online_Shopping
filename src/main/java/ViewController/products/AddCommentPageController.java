package ViewController.products;

import Control.CustomerManagers.ProductPageManager;
import ViewController.Controller;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AddCommentPageController extends Controller {
    public TextField titleField;
    public TextArea commentArea;

    public void confirmComment(ActionEvent actionEvent) {
        if (titleField.getText().isEmpty() || commentArea.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Fill the fields then confirm!", ButtonType.OK);
            alert.show();
        } else {
            ((ProductPageManager) manager).addComment(titleField.getText(), commentArea.getText());
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.close();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "comment added!", ButtonType.OK);
            alert.show();
        }
    }

    public void clearFields(ActionEvent actionEvent) {
        titleField.setText("");
        commentArea.setText("");
    }

    public void cancel(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
