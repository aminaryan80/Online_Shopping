package Client.ViewController.userPanel.Seller;

import Client.ViewController.Controller;
import Models.Shop.Category.Category;
import Models.Shop.Category.Feature;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class FeaturesPopUpController extends Controller {

    @FXML
    private TextField featuresText;
    @FXML
    private Label featuresLabel;
    private int i;
    private ArrayList<String> featuresNames;
    private ArrayList<Feature> allFeatures;
    private int featuresNumbers;
    private Controller controller;

    public void init(Controller controller) {
        allFeatures = new ArrayList<>();
        i = -1;
        featuresLabel.setText("enter category");
        this.controller = controller;
    }

    public void next(ActionEvent actionEvent) {
        if (i == -1) {
            String categoryName = featuresText.getText();
            if (!Category.hasCategoryWithName(categoryName)) {
                manager.error("wrong category name");
                return;
            }
            featuresNames = Category.getCategoryByName(categoryName).getFeaturesNames();
            featuresNumbers = featuresNames.size();
            i++;
            featuresText.clear();
            featuresLabel.setText(featuresNames.get(i));
        } else if (i == featuresNumbers - 1) {
            featuresText.clear();
            ((EditProductsController) controller).addProduct(allFeatures);
            featuresLabel.setText("its done close the pop up");
        } else {
            allFeatures.add(new Feature(featuresNames.get(i), featuresText.getText()));
            i++;
            featuresText.clear();
            featuresLabel.setText(featuresNames.get(i));
        }

    }
}
