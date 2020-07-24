package Client.ViewController.principal.manageCategories.editCategory;

import Server.Control.Manager;
import Client.ViewController.principal.manageCategories.ManageCategoriesController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EditCategoryController extends ManageCategoriesController implements Initializable {

    @FXML
    private TextField editField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void edit(ActionEvent actionEvent) {
        String featureName = editField.getText();
        //((EditCategoryManager) manager).editCategory(editField.getText());
        if (featureName.equals("name")) {
            loadFxml(Addresses.EDIT_NAME_CATEGORY, true);
        } else if (featureName.equals("feature")) {
            loadFxml(Addresses.EDIT_FEATURES_CATEGORY, true);
        } else error("Invalid input");
    }
}
