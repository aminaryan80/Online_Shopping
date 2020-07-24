package Client.ViewController.principal.manageCategories.editCategory;

import Server.Control.Manager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditNameCategoryController extends EditCategoryController {

    @FXML
    private TextField categoryNameField;

    public void editCategoryName(ActionEvent actionEvent) {
        //((EditCategoryManager) manager).editName(categoryNameField.getText());
        String response = sendRequest("EDIT_CATEGORY EDIT_NAME " + categoryName + " " + categoryNameField.getText());
        if (response.equals("0")) {
            success("Feature added successfully.");
        } else error("Something went wrong.");
        ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
        loadFxml(Addresses.MANAGE_CATEGORIES);
    }
}
