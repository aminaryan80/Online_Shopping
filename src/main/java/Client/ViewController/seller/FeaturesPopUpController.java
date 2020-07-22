package Client.ViewController.seller;

import Models.Gson;
import Models.Shop.Category.Category;
import Models.Shop.Category.Feature;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FeaturesPopUpController extends ManageProductsController implements Initializable {

    public TextField featuresText;
    public Label featuresLabel;

    private int i;
    private ArrayList<String> featuresNames;
    private ArrayList<Feature> allFeatures;
    private int featuresNumbers;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        allFeatures = new ArrayList<>();
        i = -1;
        featuresLabel.setText("Enter category:");
    }

    public void next(ActionEvent actionEvent) {
        if (i == -1) {
            String categoryName = featuresText.getText();
            Category category = Gson.INSTANCE.get().fromJson(sendRequest("GET_CATEGORY " + categoryName), Category.class);
            if (category == null) {
                error("Wrong category name.");
                return;
            }
            featuresNames = category.getFeaturesNames();
            featuresNumbers = featuresNames.size();
            i++;
            featuresText.clear();
            featuresLabel.setText(featuresNames.get(i));
        } else if (i == featuresNumbers - 1) {
            featuresText.clear();
            addProduct(allFeatures);
            ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
        } else {
            allFeatures.add(new Feature(featuresNames.get(i), featuresText.getText()));
            i++;
            featuresText.clear();
            featuresLabel.setText(featuresNames.get(i));
        }
    }
}
