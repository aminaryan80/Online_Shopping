package ViewController.products;

import Control.Products.ProductsManager;
import Models.Shop.Category.Category;
import Models.Shop.Product.Product;
import ViewController.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductsController extends Controller {

    public GridPane productsGridPane;
    public Label categoryNameLabel;
    private ArrayList<Product> products;
    private Category currentCategory;

    public void setProducts(List<Product> products) {
        this.products = (ArrayList<Product>) products;
    }

    public void setCategory(Category currentCategory) {
        this.currentCategory = currentCategory;
    }

    public void init() {
        try {
            categoryNameLabel.setText(currentCategory.getName());
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void filter(ActionEvent actionEvent) {

    }

    public void sort(ActionEvent actionEvent) {

    }

    public void viewCategories(ActionEvent actionEvent) {
        ((ProductsManager) manager).viewCategories();
    }
}
