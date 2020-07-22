package Client.ViewController.products;

import Client.Control.Manager;
import Client.ViewController.Controller;
import Models.Account.Account;
import Models.Account.Customer;
import Models.Gson;
import Models.Shop.Category.Feature;
import Models.Shop.Off.Auction;
import Models.Shop.Product.Product;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
    public GridPane comments;
    public Pane discountPane;
    public Label beginning;
    public Label ending;
    public Label amount;

    protected static Product product;
    protected static Auction auction;
    int row;

    public void init() {
        row = 0;
        initializeProduct();
        initializeComments();
        if (product.hasAuction()) {
            discountPane.setVisible(true);
            beginning.setText(auction.getBeginningDate().toString());
            ending.setText(auction.getEndingDate().toString());
            amount.setText(String.valueOf(auction.getDiscountAmount()));
        }
    }

    public void initializeComments() {
        HashMap<String, String> commentsHashMap = new HashMap<>(Gson.INSTANCE.get().fromJson(sendRequest("GET_PRODUCT_COMMENTS " + product.getId()),
                new TypeToken<HashMap<String, String>>() {
                }.getType()));
        for (String senderName : commentsHashMap.keySet()) {
            try {
                AnchorPane commentPane = FXMLLoader.load(getClass().getClassLoader().getResource(Manager.Addresses.COMMENT.getAddress()));
                Label senderNameLabel = (Label) commentPane.getChildren().get(0);
                Label commentInfo = (Label) commentPane.getChildren().get(1);
                senderNameLabel.setText(senderName);
                commentInfo.setText(commentsHashMap.get(senderName));
                comments.add(commentPane, 0, row);
                comments.setVgap(10);
                row++;
                comments.setPrefHeight(comments.getPrefHeight() + 300);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initializeProduct() {
        productName.setText(product.getName());
        price.setText(product.getPrice() + "$");
        sellerName.setText(product.getSellerUsername());
        companyName.setText(product.getCompanyName());
        double score = Double.parseDouble(sendRequest("GET_PRODUCT_RATE " + product.getId()));
        rate.setText(("" + score).length() > 1 ? ("" + score).substring(0, 3) : ("" + score).substring(0, 1));
        description.setText(product.getDescription());
        productId.setText("#" + product.getId());
        ArrayList<Feature> features = new ArrayList<>(Gson.INSTANCE.get().fromJson(sendRequest("GET_PRODUCT_FEATURES " + product.getId()),
                new TypeToken<ArrayList<Feature>>() {
                }.getType()));
        featureValueTable.setItems(FXCollections.observableArrayList(features));
        featureColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
    }

    public void setInfos(Product product, Auction auction) {
        ProductPageController.product = product;
        ProductPageController.auction = auction;
    }

    public void rate(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Rate " + product.getName());
        alert.setHeaderText("How good is this product in your eyes");
        alert.setContentText("Choose your option.");
        ButtonType[] buttonType = new ButtonType[5];
        alert.getButtonTypes().clear();
        for (int i = 0; i < 5; i++) {
            buttonType[i] = new ButtonType("" + (i + 1));
            alert.getButtonTypes().add(buttonType[i]);
        }
        Optional<ButtonType> result = alert.showAndWait();
        for (int i = 0; i < 5; i++) {
            if (result.isPresent() && result.get() == buttonType[i]) {
                String response = sendRequest("RATE_PRODUCT " + product.getId() + " " + i + 1);
                if (response.equals("0")) {
                    success("Rate added to product!");
                } else {
                    error("You should buy the product to rate it!");
                }
            }
        }
        initializeProduct();
    }

    public void addToCart(MouseEvent mouseEvent) {
        Account account = Account.getAccountByUsername(accountUsername);
        if (account instanceof Customer) {
            //((ProductPageManager) manager).hasProductInCart()
            if (!sendRequest("HAS_PRODUCT_IN_CART " + account.getUsername() + " " + product.getId()).equals("0")) {
                error("You have added this product to your cart before!");
            } else {
                //((ProductPageManager) manager).addToCart();
                sendRequest("ADD_PRODUCT_TO_CART " + account.getUsername() + " " + product.getId());
                success(product.getName() + " is added to your cart!");
            }
        } else error("Something went wrong.");
    }

    public void addComment(MouseEvent mouseEvent) {
        loadFxml(Manager.Addresses.ADD_COMMENT, true);
    }

    public void compare(ActionEvent actionEvent) {
        //((ProductPageManager) manager).compare();
        /*try {
            FXMLLoader loader = getLoader(Manager.Addresses.COMPARE);
            Parent root = loader.load();
            CompareController controller = loader.getController();
            controller.setManager(manager);
            Scene scene = new Scene(root);
            popup.setTitle("AP Project");
            popup.setScene(scene);
            popup.show();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        loadFxml(Manager.Addresses.COMPARE, true);
    }

    public void back() {
        loadFxml(Manager.Addresses.PRODUCTS_MENU);
    }
}
