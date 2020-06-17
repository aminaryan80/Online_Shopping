package ViewController.principal;

import Control.Principal.ManageAllProductsManager;
import Models.Shop.Product.Product;
import ViewController.Controller;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageProductsController extends Controller implements Initializable {
    @FXML
    private TableView<Product> productsTable;
    @FXML
    private TableColumn<Product, String> productIdCol;
    @FXML
    private TableColumn<Product, String> productNameCol;
    @FXML
    private TableColumn<Product, String> productPriceCol;
    @FXML
    private TableColumn<Product, String> productSellerCol;
    @FXML
    private TextField productIdField;

    public void deleteProduct(ActionEvent actionEvent) {
        String productId = productIdField.getText();
        ((ManageAllProductsManager) manager).removeProductById(productId);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productsTable.setItems(FXCollections.observableArrayList(Product.getAllProducts()));
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productSellerCol.setCellValueFactory(new PropertyValueFactory<>("sellerUsername"));
    }
}
