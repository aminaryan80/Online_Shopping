package Client.ViewController.products;

import Models.Gson;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class AddCommentPageController extends ProductPageController {
    public TextField titleField;
    public TextArea commentArea;

    public void confirmComment(ActionEvent actionEvent) {
        if (titleField.getText().isEmpty() || commentArea.getText().isEmpty()) {
            error("Fill the fields then confirm!");
        } else {
            ArrayList<String> inputs = new ArrayList<>();
            inputs.add(titleField.getText());
            inputs.add(commentArea.getText());
            if (accountUsername != null) {
                sendRequest("ADD_COMMENT " + accountUsername + " " + product.getId() + " " + Gson.INSTANCE.get().toJson(inputs));
                success("comment added!");
            } else error("Something went wrong.");
            //((ProductPageManager) manager).addComment(titleField.getText(), commentArea.getText());
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.close();
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
