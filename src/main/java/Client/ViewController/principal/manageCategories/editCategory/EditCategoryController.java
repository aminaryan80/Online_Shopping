package Client.ViewController.principal.manageCategories.editCategory;

import Client.Control.Principal.ManageCategories.EditCategoryManager;
import Client.ViewController.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class EditCategoryController extends Controller {

    @FXML
    private TextField editField;

    public void edit(ActionEvent actionEvent) {
        ((EditCategoryManager) manager).editCategory(editField.getText());
    }
}
