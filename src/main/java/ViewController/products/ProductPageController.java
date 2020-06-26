package ViewController.products;

import Control.CustomerManagers.ProductPageManager;
import Control.CustomerManagers.ViewCartManager;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.Optional;

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
        double score = product.getRate();
        rate.setText(("" + score).length() > 1 ? ("" + score).substring(0, 3) : ("" + score).substring(0, 1));
        description.setText(product.getDescription());
        productId.setText("#" + product.getId());
        featureValueTable.setItems(FXCollections.observableArrayList(product.getFeatures()));
        featureColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
    }

    public void back(MouseEvent mouseEvent) {
        super.back(null);
    }

    public void rate(MouseEvent mouseEvent) throws ViewCartManager.ProductDoNotExistAtAllException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Rate " + product.getName());
        alert.setHeaderText("How good is this product in your eyes");
        alert.setContentText("Choose your option.");
        ButtonType buttonType[] = new ButtonType[5];
        alert.getButtonTypes().clear();
        for (int i = 0; i < 5; i++) {
            buttonType[i] = new ButtonType("" + (i + 1));
            alert.getButtonTypes().add(buttonType[i]);
        }
        Optional<ButtonType> result = alert.showAndWait();
        for (int i = 0; i < 5; i++) {
            if (result.isPresent() && result.get() == buttonType[i]) {
                if(((ProductPageManager) manager).rateProduct(product.getId(), i + 1)) {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "Rate added to product!", ButtonType.OK);
                    alert2.show();
                } else {
                    Alert alert2 = new Alert(Alert.AlertType.WARNING, "You should buy the product to rate it!", ButtonType.OK);
                    alert2.show();
                }
            }
        }
        initializeProduct();
    }

    public void addToCart(MouseEvent mouseEvent) {
        if (((ProductPageManager) manager).getCustomer() != null && ((ProductPageManager) manager).hasProductInCart()) {
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "You have added this product to your cart before!", ButtonType.OK);
                alert2.show();
            } else {
            ((ProductPageManager) manager).addToCart();
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION, product.getName() + " is added to your cart!", ButtonType.OK);
            alert2.show();
        }
    }

    public void addComment(MouseEvent mouseEvent) {
        ((ProductPageManager) manager).addComment();
    }
}
