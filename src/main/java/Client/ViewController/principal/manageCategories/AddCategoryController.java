package Client.ViewController.principal.manageCategories;

import Client.Control.Principal.ManageCategories.ManageCategoriesManager;
import Client.ViewController.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class AddCategoryController extends Controller {

    HashMap<String, Integer> features = new HashMap<>();
    private String categoryName;
    @FXML
    private TextField supCategoryField;
    @FXML
    private TextField featureField;
    @FXML
    private TextField featureTypeField;


    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void addCategory(ActionEvent actionEvent) {
        ((ManageCategoriesManager) manager).addCategory(supCategoryField.getText(), categoryName, features, new ArrayList<>());
        ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
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
