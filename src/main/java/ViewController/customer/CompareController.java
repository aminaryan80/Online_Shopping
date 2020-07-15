package ViewController.customer;

import Control.CustomerManagers.ProductPageManager;
import Models.Shop.Product.Product;
import ViewController.Controller;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class CompareController extends Controller {

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

    Product product;

    @Override
    public void init() {
        product = ((ProductPageManager) manager).getProduct();
        initializeProduct(product);
    }

    private void initializeProduct(Product product) {
        productName.setText(product.getName());
        price.setText(product.getPrice() + "$");
        sellerName.setText(product.getSeller().getName());
        companyName.setText(product.getCompanyName());
        double score = product.getRate();
        rate.setText(("" + score).length() > 1 ? ("" + score).substring(0, 3) : ("" + score).substring(0, 1));
        description.setText(product.getDescription());
        productId.setText("#" + product.getId());
        featureValueTable.setItems(FXCollections.observableArrayList(product.getFeatures()));
        featureColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
    }

    private void initializeOtherProduct(Product product) {
        productNameOther.setText(product.getName());
        priceOther.setText(product.getPrice() + "$");
        sellerNameOther.setText(product.getSeller().getName());
        companyNameOther.setText(product.getCompanyName());
        double score = product.getRate();
        rateOther.setText(("" + score).length() > 1 ? ("" + score).substring(0, 3) : ("" + score).substring(0, 1));
        descriptionOther.setText(product.getDescription());
        productIdOther.setText("#" + product.getId());
        featureValueTableOther.setItems(FXCollections.observableArrayList(product.getFeatures()));
        featureColumnOther.setCellValueFactory(new PropertyValueFactory<>("name"));
        valueColumnOther.setCellValueFactory(new PropertyValueFactory<>("value"));
    }

    public void setOtherProduct(ActionEvent actionEvent) {
        if(otherProductIdField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Fill the fields then confirm!", ButtonType.OK);
            alert.show();
        } else {
            if(manager.getProduct(otherProductIdField.getText())!=null){
                if(!otherProductIdField.getText().equals(product.getId())){
                    initializeOtherProduct(manager.getProduct(otherProductIdField.getText()));
                }else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Can not compare to itself!", ButtonType.OK);
                    alert.show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Wrong Id!", ButtonType.OK);
                alert.show();
            }
        }
    }
}
