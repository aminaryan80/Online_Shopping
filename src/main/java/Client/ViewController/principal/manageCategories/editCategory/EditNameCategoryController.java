package Client.ViewController.principal.manageCategories.editCategory;

import Client.Control.Principal.ManageCategories.EditCategoryManager;
import Client.ViewController.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditNameCategoryController extends Controller {

    @FXML
    private TextField categoryNameField;

    public void editCategoryName(ActionEvent actionEvent) {
        ((EditCategoryManager) manager).editName(categoryNameField.getText());
        ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
    }
}
