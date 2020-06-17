package ViewController.principal.manageCategories.editCategory;

import Control.Principal.ManageCategories.EditCategoryManager;
import ViewController.Controller;
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
