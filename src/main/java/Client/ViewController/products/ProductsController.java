package Client.ViewController.products;

import Client.Control.Products.ProductsManager;
import Client.ViewController.Controller;
import Models.Shop.Category.Category;
import Models.Shop.Product.Product;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
                controller.setInfos(product.getAuction(), product, product.hasAuction());
                controller.setManager(manager);
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

    public void initTable(ArrayList<Object> tableObjects) {
        ArrayList<Product> tableProducts = new ArrayList<>();
        for (Object tableProduct : tableObjects) {
            tableProducts.add((Product) tableProduct);
        }
        products = tableProducts;
        ObservableList<Node> children = productsGridPane.getChildren();
        productsGridPane.getChildren().removeAll(children);
        init();
    }

    public void filter(ActionEvent actionEvent) {
        ((ProductsManager) manager).openFilter(this);
    }

    public void sort(ActionEvent actionEvent) {
        manager.openSort(this, manager);
    }

    public void viewCategories(ActionEvent actionEvent) {
        ((ProductsManager) manager).viewCategories();
    }
}
