package ViewController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductsController implements Initializable {

    @FXML
    private GridPane products;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            AnchorPane product = FXMLLoader.load(getClass().getResource("../view/products/Product.fxml"));
            AnchorPane product2 = FXMLLoader.load(getClass().getResource("../view/products/Product.fxml"));
            AnchorPane product3 = FXMLLoader.load(getClass().getResource("../view/products/Product.fxml"));
            AnchorPane product4 = FXMLLoader.load(getClass().getResource("../view/products/Product.fxml"));
            products.add(product, 0, 0);
            products.add(product2, 1, 0);
            products.add(product3, 2, 0);
            products.add(product4, 0, 1);
            products.setPrefHeight(760);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
