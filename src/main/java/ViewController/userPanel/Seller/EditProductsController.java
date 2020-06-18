package ViewController.userPanel.Seller;

import Control.Seller.EditProductsManager;
import Control.Seller.SellerManager;
import Models.Account.Account;
import Models.Account.Seller;
import Models.Shop.Category.Category;
import Models.Shop.Category.Feature;
import Models.Shop.Product.Product;
import Models.Shop.Request.EditProductRequest;
import ViewController.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EditProductsController extends Controller {

    @FXML
    private TableView<Product> products;
    @FXML
    private TableView<Feature> features;
    @FXML
    private TableView<Account> buyers;
    @FXML
    private TableView<Feature> newFeatures;
    @FXML
    private TableColumn<Product, String> idColumn;
    @FXML
    private TableColumn<Product, String> productsNameColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private TableColumn<Product, String> categoryColumn;
    @FXML
    private TableColumn<Account, String> buyersNameColumn;
    @FXML
    private TableColumn<Feature, String> featureColumn;
    @FXML
    private TableColumn<Feature, String> valueColumn;
    @FXML
    private TableColumn<Feature, String> newFeaturesColumn;
    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private TextField price;
    @FXML
    private TextField category;
    @FXML
    private TextField description;
    @FXML
    private TextField rate;
    @FXML
    private TextField isAvailable;
    @FXML
    private TextField newFeaturesValues;
    private Product product;

    public void init() {
        products.setItems(FXCollections.observableArrayList(Product.getAllProducts()));
        productsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    public void remove(MouseEvent mouseEvent) {
        try {
            ((SellerManager) manager).deleteProductById(id.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(MouseEvent mouseEvent) {
        if (!product.getName().equals(name.getText())) {
            Product product1 = ((EditProductsManager) manager).editProduct(id.getText(), "name", name.getText());
            new EditProductRequest((Seller) manager.getAccount(), product1);
        }
        if (product.getPrice() != Double.parseDouble(price.getText())) {
            Product product1 = ((EditProductsManager) manager).editProduct(id.getText(), "price", id.getText());
            new EditProductRequest((Seller) manager.getAccount(), product1);
        }
        if (product.isAvailable() != Boolean.parseBoolean(isAvailable.getText())) {
            Product product1 = ((EditProductsManager) manager).editProduct(id.getText(), "isAvailable", isAvailable.getText());
            new EditProductRequest((Seller) manager.getAccount(), product1);
        }
        if (!product.getDescription().equals(description.getText())) {
            Product product1 = ((EditProductsManager) manager).editProduct(id.getText(), "description", description.getText());
            new EditProductRequest((Seller) manager.getAccount(), product1);
        }
        Feature feature = features.getSelectionModel().getSelectedItem();
        if (feature != null) {
            Product product1 = ((EditProductsManager) manager).editProduct(id.getText(), feature.getName(), newFeaturesValues.getText());
            new EditProductRequest((Seller) manager.getAccount(), product1);
        }
    }

    public void add(MouseEvent mouseEvent) {
        if (!Category.hasCategoryWithName(category.getText()) && !category.getText().equals("mainCategory")) {
            //TODO error popup
            return;
        }
        if (!manager.checkNumber(price.getText())) {
            //TODO error popup
            return;
        }
        ArrayList<Feature> allFeatures = new ArrayList<>();
        String[] featuresNames = (String[]) product.getFeatures().toArray();
        String[] featuresValues = newFeaturesValues.getText().split("//s+");
        for (int i = 0; i < featuresNames.length; i++) {
            allFeatures.add(new Feature(featuresNames[i], featuresValues[i]));
        }
        ((SellerManager) manager).addProduct(name.getText(), Category.getCategoryByName(category.getText()),
                Double.parseDouble(price.getText()), Boolean.parseBoolean(isAvailable.getText()), description.getText(), allFeatures);
    }

    public void updateScene(MouseEvent mouseEvent) {
        product = products.getSelectionModel().getSelectedItem();
        initBuyers();
        initFeatures();
        initFields();
    }

    private void initFields() {
        id.setText(product.getId());
        name.setText(product.getName());
        description.setText(product.getDescription());
        rate.setText(String.valueOf(product.getRate()));
        price.setText(String.valueOf(product.getPrice()));
        category.setText(product.getCategory().getName());
        isAvailable.setText(String.valueOf(product.isAvailable()));
    }

    private void initFeatures() {
        features.setItems(FXCollections.observableArrayList(product.getFeatures()));
        featureColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
    }

    private void initBuyers() {
        buyers.setItems(FXCollections.observableArrayList(product.getAllBuyers()));
        buyersNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
    }

    public void updateFeatures() {
        newFeatures.setItems(FXCollections.observableArrayList(Category.getCategoryByName(category.getText()).getFeatures()));
        newFeaturesColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    }
}
