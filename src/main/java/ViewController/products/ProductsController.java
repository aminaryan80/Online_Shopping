package ViewController.products;

import Models.Shop.Product.Product;
import ViewController.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductsController extends Controller {

    @FXML
    private GridPane productsGridPane;
    private ArrayList<Product> products;

    public void setProducts(List<Product> products) {
        this.products = (ArrayList<Product>) products;
    }

    public void init() {
        try {
            int i = 0;
            productsGridPane.setPrefHeight(0);
            for (Product product : products) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/products/Product.fxml"));
                Parent root = loader.load();
                ProductItemController controller = loader.getController();
                controller.setInfos(product);
                productsGridPane.add(root, i % 3, i / 3);
                if (i % 3 == 0) {
                    productsGridPane.setPrefHeight(productsGridPane.getPrefHeight() + 380);
                }
                i++;
            }
            /*AnchorPane product = FXMLLoader.load(getClass().getResource("../../view/products/Product.fxml"));
            AnchorPane product2 = FXMLLoader.load(getClass().getResource("../../view/products/Product.fxml"));
            AnchorPane product3 = FXMLLoader.load(getClass().getResource("../../view/products/Product.fxml"));
            AnchorPane product4 = FXMLLoader.load(getClass().getResource("../../view/products/Product.fxml"));
            productsGridPane.add(product, 0, 0);
            productsGridPane.add(product2, 1, 0);
            productsGridPane.add(product3, 2, 0);
            productsGridPane.add(product4, 0, 1);
            productsGridPane.setPrefHeight(760);*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
