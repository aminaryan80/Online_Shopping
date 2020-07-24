package Client.ViewController.products;

import Server.Control.Manager;
import Server.Control.Products.ProductsManager;
import Client.ViewController.MainController;
import Models.Gson;
import Models.Shop.Category.Category;
import Models.Shop.Off.Auction;
import Models.Shop.Product.Product;
import com.google.gson.reflect.TypeToken;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProductsController extends MainController implements Initializable {

    public GridPane productsGridPane;
    public Label categoryNameLabel;
    private static Category currentCategory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            if(currentCategory == null) {
                currentCategory = Gson.INSTANCE.get().fromJson(sendRequest("GET_CATEGORY mainCategory"), Category.class);
            }
            ObservableList<Node> children = productsGridPane.getChildren();
            productsGridPane.getChildren().removeAll(children);
            categoryNameLabel.setText(currentCategory.getName());
            int i = 0;
            productsGridPane.setPrefHeight(0);
            ArrayList<Product> products = new ArrayList<>();
            products.addAll(Gson.INSTANCE.get().fromJson(sendRequest("LOAD_PRODUCTS " + isOffsMenu),
                    new TypeToken<ArrayList<Product>>() {
                    }.getType()));
            for (Product product : products) {
                FXMLLoader loader = getLoader(Addresses.PRODUCT_ITEM);
                Parent root = loader.load();
                ProductItemController controller = loader.getController();
                Auction auction = Gson.INSTANCE.get().fromJson(sendRequest("GET_PRODUCT_AUCTION " + product.getId()), Auction.class);
                controller.setInfos(auction, product, product.hasAuction());
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

    public void viewCategories(ActionEvent actionEvent) {
        loadFxml(Addresses.VIEW_CATEGORIES, true);
    }

    public void filter(ActionEvent actionEvent) {
        ((ProductsManager) manager).openFilter(this);
    }

    public void sort(ActionEvent actionEvent) {
        manager.openSort(this, manager);
    }

    public void back() {
        loadFxml(Addresses.MAIN_MENU);
    }
}
