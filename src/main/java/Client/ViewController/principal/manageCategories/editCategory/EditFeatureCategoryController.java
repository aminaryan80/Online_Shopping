package Client.ViewController.principal.manageCategories.editCategory;

import Server.Control.Manager;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditFeatureCategoryController extends EditCategoryController {

    public RadioButton addRB;
    public RadioButton editRB;
    public RadioButton deleteRB;
    public TextField featureNameField;
    public TextField newNameField;
    public Label newNameLabel;

    public void editCategory(ActionEvent actionEvent) {
        if (addRB.isSelected()) {
            addFeature();
        } else if (editRB.isSelected()) {
            editFeature();
        } else if (deleteRB.isSelected()) {
            deleteFeature();
        }
        ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
        loadFxml(Addresses.MANAGE_CATEGORIES);
    }

    private void addFeature() {
        //((EditCategoryManager) manager).addFeature(featureNameField.getText());
        String response = sendRequest("EDIT_CATEGORY ADD_FEATURE " + categoryName + " " + featureNameField.getText());
        if (response.equals("0")) {
            success("Feature added successfully.");
        } else error("Something went wrong.");
    }

    private void editFeature() {
        //((EditCategoryManager) manager).editFeature(featureNameField.getText(), newNameField.getText());
        String response = sendRequest("EDIT_CATEGORY EDIT_FEATURE " + categoryName + " " + featureNameField.getText() + " " + newNameField.getText());
        if (response.equals("0")) {
            success("Feature added successfully.");
        } else error("Something went wrong.");
    }

    private void deleteFeature() {
        //((EditCategoryManager) manager).removeFeature(featureNameField.getText());
        String response = sendRequest("EDIT_CATEGORY DELETE_FEATURE " + categoryName + " " + featureNameField.getText());
        if (response.equals("0")) {
            success("Feature added successfully.");
        } else error("Something went wrong.");
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
