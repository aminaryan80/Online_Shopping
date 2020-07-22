package Client.ViewController.customer;

import Client.ViewController.products.ProductPageController;
import Models.Gson;
import Models.Shop.Category.Feature;
import Models.Shop.Product.Product;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CompareController extends ProductPageController implements Initializable {

    public Label productId;
    public Label productName;
    public Label sellerName;
    public Label price;
    public Label companyName;
    public Label description;
    public Label rate;
    public TableView featureValueTable;
    public TableColumn featureColumn;
    public TableColumn valueColumn;
    public Label productIdOther;
    public Label productNameOther;
    public Label sellerNameOther;
    public Label descriptionOther;
    public Label priceOther;
    public Label companyNameOther;
    public Label rateOther;
    public TableView featureValueTableOther;
    public TableColumn featureColumnOther;
    public TableColumn valueColumnOther;
    public TextField otherProductIdField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productName.setText(product.getName());
        price.setText(product.getPrice() + "$");
        sellerName.setText(product.getSellerUsername());
        companyName.setText(product.getCompanyName());
        double score = Double.parseDouble(sendRequest("GET_PRODUCT_RATE " + product.getId()));
        rate.setText(("" + score).length() > 1 ? ("" + score).substring(0, 3) : ("" + score).substring(0, 1));
        description.setText(product.getDescription());
        productId.setText("#" + product.getId());
        ArrayList<Feature> features = new ArrayList<>(Gson.INSTANCE.get().fromJson(sendRequest("GET_PRODUCT_FEATURES " + product.getId()),
                new TypeToken<ArrayList<Feature>>() {}.getType()));
        featureValueTable.setItems(FXCollections.observableArrayList(features));
        featureColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
    }

    private void initializeOtherProduct(Product product) {
        productNameOther.setText(product.getName());
        priceOther.setText(product.getPrice() + "$");
        sellerNameOther.setText(product.getSellerUsername());
        companyNameOther.setText(product.getCompanyName());
        double score = Double.parseDouble(sendRequest("GET_PRODUCT_RATE " + product.getId()));
        rateOther.setText(("" + score).length() > 1 ? ("" + score).substring(0, 3) : ("" + score).substring(0, 1));
        descriptionOther.setText(product.getDescription());
        productIdOther.setText("#" + product.getId());
        ArrayList<Feature> features = new ArrayList<>(Gson.INSTANCE.get().fromJson(sendRequest("GET_PRODUCT_FEATURES " + product.getId()),
                new TypeToken<ArrayList<Feature>>() {}.getType()));
        featureValueTableOther.setItems(FXCollections.observableArrayList(features));
        featureColumnOther.setCellValueFactory(new PropertyValueFactory<>("name"));
        valueColumnOther.setCellValueFactory(new PropertyValueFactory<>("value"));
    }

    public void setOtherProduct(ActionEvent actionEvent) {
        if (otherProductIdField.getText().isEmpty()) {
            error("Fill the fields then confirm!");
        } else {
            Product product = Gson.INSTANCE.get().fromJson(sendRequest("GET_PRODUCT " + otherProductIdField.getText()), Product.class);
            if (product != null) {
                if (!otherProductIdField.getText().equals(product.getId())) {
                    initializeOtherProduct(product);
                } else {
                    error("Can not compare to itself!");
                }
            } else {
                error("Wrong Id!");
            }
        }
    }
}
