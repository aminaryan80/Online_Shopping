package Client.ViewController.principal.manageCategories;

import Client.Control.Manager;
import Models.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class AddCategoryController extends ManageCategoriesController implements Initializable {

    HashMap<String, Integer> features = new HashMap<>();
    //private String categoryName;
    @FXML
    private TextField supCategoryField;
    @FXML
    private TextField featureField;
    @FXML
    private TextField featureTypeField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void addCategory(ActionEvent actionEvent) {
        String response = sendRequest("ADD_CATEGORY " + supCategoryField.getText() + "&&&" + categoryName + "&&&" + Gson.INSTANCE.get().toJson(features));
        if (response.equals("0")) {
            success("Category added successfully.");
        } else error("Something went wrong.");
        ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
        loadFxml(Manager.Addresses.MANAGE_CATEGORIES);
    }

    public void addFeature(ActionEvent actionEvent) {
        String feature = featureField.getText();
        int featureType;
        if (featureTypeField.getText().equals("number")) {
            featureType = 0;
            features.put(feature, featureType);
        } else if (featureTypeField.getText().equals("text")) {
            featureType = 1;
            features.put(feature, featureType);
        }
        featureField.setText("");
        featureTypeField.setText("");
    }
}
