package ViewController.products;

import Control.CustomerManagers.ProductPageManager;
import Control.Manager;
import Models.Address;
import Models.Shop.Product.Product;
import ViewController.Controller;
import com.sun.org.apache.xpath.internal.res.XPATHErrorResources_ko;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import java.io.IOException;

public class ProductPageController extends Controller {

    public Label productName;
    public Label price;
    public Label sellerName;
    public Label companyName;
    public Label rate;
    public Label description;
    public TableView featureValueTable;
    public Label productId;
    @FXML
    private GridPane comments;

    private Product product;
    public void init() {
        product = ((ProductPageManager) manager).getProduct();
        initializeProduct();
        try {
            AnchorPane comment = FXMLLoader.load(getClass().getResource(Manager.Addresses.COMMENT.getAddress()));
            AnchorPane comment2 = FXMLLoader.load(getClass().getResource(Manager.Addresses.COMMENT.getAddress()));
            comments.add(comment, 0, 0);
            comments.add(comment2, 0, 1);
            comments.setPrefHeight(500);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeProduct() {
         productName.setText(product.getName());
         price.setText(product.getPrice()+"$");
         sellerName.setText(product.getSeller().getName());
         companyName.setText(product.getCompanyName());
         rate.setText(""+product.getRate());
         description.setText(product.getDescription());
         productId.setText("#"+product.getId());
    }

    public void rate(MouseEvent mouseEvent) {
    }

    public void addToCart(MouseEvent mouseEvent) {
    }

    public void addComment(MouseEvent mouseEvent) {
    }
}
