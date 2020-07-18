package Client.ViewController.principal.manageCategories.editCategory;

import Client.Control.Principal.ManageCategories.EditCategoryManager;
import Client.ViewController.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditFeatureCategoryController extends Controller {

    @FXML
    private RadioButton addRB;
    @FXML
    private RadioButton editRB;
    @FXML
    private RadioButton deleteRB;
    @FXML
    private TextField featureNameField;
    @FXML
    private TextField newNameField;
    @FXML
    private Label newNameLabel;

    public void editCategory(ActionEvent actionEvent) {
        if (addRB.isSelected()) {
            addFeature();
        } else if (editRB.isSelected()) {
            editFeature();
        } else if (deleteRB.isSelected()) {
            deleteFeature();
        }
        ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
    }

    private void addFeature() {
        ((EditCategoryManager) manager).addFeature(featureNameField.getText());
    }

    private void editFeature() {
        ((EditCategoryManager) manager).editFeature(featureNameField.getText(), newNameField.getText());
    }

    private void deleteFeature() {
        ((EditCategoryManager) manager).removeFeature(featureNameField.getText());
    }

    public void openAddFeature(ActionEvent actionEvent) {
        newNameLabel.setVisible(false);
        newNameField.setVisible(false);
    }

    public void openEditFeature(ActionEvent actionEvent) {
        newNameLabel.setVisible(true);
        newNameField.setVisible(true);
    }

    public void openDeleteFeature(ActionEvent actionEvent) {
        newNameLabel.setVisible(false);
        newNameField.setVisible(false);
    }
}
