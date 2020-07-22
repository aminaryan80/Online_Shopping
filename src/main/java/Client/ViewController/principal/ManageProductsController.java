package Client.ViewController.principal;

import Client.Control.Manager;
import Client.ViewController.Controller;
import Models.Shop.Product.Product;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageProductsController extends Controller implements Initializable {
    public TableView<Product> productsTable;
    public TableColumn<Product, String> productIdCol;
    public TableColumn<Product, String> productNameCol;
    public TableColumn<Product, String> productPriceCol;
    public TableColumn<Product, String> productSellerCol;
    public TextField productIdField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productsTable.setItems(FXCollections.observableArrayList(getAllProducts()));
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productSellerCol.setCellValueFactory(new PropertyValueFactory<>("sellerUsername"));
    }

    public void deleteProduct(ActionEvent actionEvent) {
        String productId = productIdField.getText();
        String response = sendRequest("DELETE_PRODUCT " + productId);
        if (response.equals("0")) {
            success("Product deleted successfully.");
        } else error("Something went wrong.");
    }

    public void sort(ActionEvent actionEvent) {
        manager.openSort(this, manager);
    }

    public void back() {
        loadFxml(Manager.Addresses.PRINCIPAL_MENU);
    }
}
