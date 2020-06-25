package ViewController.products;

import Control.CustomerManagers.ProductPageManager;
import Control.Manager;
import Models.Address;
import Models.Shop.Category.Feature;
import Models.Shop.Product.Comment;
import Models.Shop.Product.Product;
import ViewController.Controller;
import com.sun.org.apache.xpath.internal.res.XPATHErrorResources_ko;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
    public TableColumn featureColumn;
    public TableColumn valueColumn;
    @FXML
    private GridPane comments;

    private Product product;

    public void init() {
        product = ((ProductPageManager) manager).getProduct();
        initializeProduct();
        initializeComments();
    }

    public void initializeComments() {
        int row = 0;
        comments.setPrefHeight(500);
        for (String senderName : ((ProductPageManager) manager).commentsFXML().keySet()) {
            try {
                AnchorPane commentPane = FXMLLoader.load(getClass().getClassLoader().getResource(Manager.Addresses.COMMENT.getAddress()));
                Label senderNameLabel = (Label) commentPane.getChildren().get(0);
                Label commentInfo = (Label) commentPane.getChildren().get(1);
                senderNameLabel.setText(senderName);
                commentInfo.setText(((ProductPageManager) manager).commentsFXML().get(senderName));
                comments.add(commentPane, 0, row);
                comments.setVgap(10);
                row++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initializeProduct() {
        productName.setText(product.getName());
        price.setText(product.getPrice() + "$");
        sellerName.setText(product.getSeller().getName());
        companyName.setText(product.getCompanyName());
        rate.setText("" + product.getRate());
        description.setText(product.getDescription());
        productId.setText("#" + product.getId());
        featureValueTable.setItems(FXCollections.observableArrayList(product.getFeatures()));
        featureColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
    }

    public void back(MouseEvent mouseEvent) {
        super.back(null);
    }

    public void rate(MouseEvent mouseEvent) {
    }

    public void addToCart(MouseEvent mouseEvent) {
    }

    public void addComment(MouseEvent mouseEvent) {
        ((ProductPageManager) manager).addComment();
    }
}
