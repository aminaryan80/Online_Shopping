package ViewController.userPanel.Seller;

import Control.Manager;
import Control.Seller.EditProductsManager;
import Control.Seller.SellerManager;
import Models.Account.Account;
import Models.Account.Seller;
import Models.Shop.Category.Category;
import Models.Shop.Category.Feature;
import Models.Shop.Product.Product;
import Models.Shop.Request.EditProductRequest;
import ViewController.Controller;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;

public class EditProductsController extends Controller {

    @FXML
    private TableView<Product> products;
    @FXML
    private TableView<Feature> features;
    @FXML
    private TableView<Account> buyers;
    @FXML
    private TableColumn<Product, String> idColumn;
    @FXML
    private TableColumn<Product, String> productsNameColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private TableColumn<Account, String> buyersNameColumn;
    @FXML
    private TableColumn<Feature, String> featureColumn;
    @FXML
    private TableColumn<Feature, String> valueColumn;
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
    private ChoiceBox changeableFeatures;
    @FXML
    private TextField changeableFeatureValue;
    private Product product;
    private Seller seller;

    public void init(Seller seller) {
        this.seller = seller;
        ArrayList<Object> objects = new ArrayList<>();
        objects.addAll(Product.getProductsBySeller(seller));
        initTable(objects);
    }

    public void initTable(ArrayList<Object> tableObjects) {
        ArrayList<Product> tableProducts = new ArrayList<>();
        for (Object tableProduct : tableObjects) {
            tableProducts.add((Product) tableProduct);
        }
        products.setItems(FXCollections.observableArrayList(tableProducts));
        productsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        //categoryColumn.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    public void remove(ActionEvent actionEvent) {
        try {
            ((SellerManager) manager).deleteProductById(id.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(ActionEvent actionEvent) {
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
    }

    public void add(ActionEvent actionEvent) {
        if (!manager.checkNumber(price.getText())) {
            manager.error("wrong price format");
            return;
        }
        ((EditProductsManager) manager).featuresPopUp(this);
    }

    public void addProduct(ArrayList<Feature> allFeatures) {
        ((EditProductsManager) manager).addProduct(name.getText(), Category.getCategoryByName(category.getText()),
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
        changeableFeatures.setItems(FXCollections.observableList(product.getCategory().getFeaturesNames()));
    }

    private void initBuyers() {
        buyers.setItems(FXCollections.observableArrayList(product.getAllBuyers()));
        buyersNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
    }

    public void updateFeatures(){
        Product product1 = ((EditProductsManager) manager).editProduct(id.getText(), (String) changeableFeatures.getSelectionModel().getSelectedItem(), changeableFeatureValue.getText());
        new EditProductRequest((Seller) manager.getAccount(), product1);
    }

    public void sort(ActionEvent actionEvent) {
        manager.openSort(this, manager);
    }

}
