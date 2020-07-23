package Client.ViewController.customer.cart;

import Client.Control.CustomerManagers.ViewCartManager;
import Client.Control.Manager;
import Client.ViewController.Controller;
import Client.ViewController.products.ProductPageController;
import Models.Gson;
import Models.Shop.Off.Auction;
import Models.Shop.Product.Product;
import com.google.gson.reflect.TypeToken;
import com.sun.javafx.scene.control.skin.LabeledText;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

public class ViewCartController extends Controller implements Initializable {

    public TableView tableView;
    public TableColumn numberColumn;
    public TableColumn idColumn;
    public TableColumn nameColumn;
    public TableColumn descriptionColumn;
    public TableColumn quantityColumn;
    public TableColumn priceColumn;
    public Label selectedProduct;
    public Label totalAmount;

    public void init() {
//        totalAmount.setText(((ViewCartManager) manager).getTotalPrice(null) + "$");
        totalAmount.setText(sendRequest("TOTAL_AMOUNT" + " " + accountUsername));
        ArrayList<CartTableItem> cartTableItems = getCartTableItems();
        ArrayList<Object> objects = new ArrayList<>();
        objects.addAll(cartTableItems);
        initTable(objects);
    }

    public void initTable(ArrayList<Object> tableObjects) {
        ArrayList<CartTableItem> cartTableItems = new ArrayList<>();
        for (Object tableProduct : tableObjects) {
            cartTableItems.add((CartTableItem) tableProduct);
        }
        ObservableList<CartTableItem> data = FXCollections.observableList(cartTableItems);
        numberColumn.setCellValueFactory(new PropertyValueFactory<CartTableItem, Integer>("number"));
        idColumn.setCellValueFactory(new PropertyValueFactory<CartTableItem, String>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<CartTableItem, String>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<CartTableItem, String>("description"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<CartTableItem, Integer>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<CartTableItem, Double>("price"));
        tableView.setItems(data);
    }

    private ArrayList<CartTableItem> getCartTableItems() {
        ArrayList<CartTableItem> cartTableItems = new ArrayList<>();
//        if (manager instanceof ViewCartManager) {
//            HashMap<Product, Integer> productsInCart = ((ViewCartManager) manager).getProductsInCart();
        HashMap<Product, Integer> productsInCart = Gson.INSTANCE.get().fromJson(sendRequest("GET_CART_PRODUCTS" + " " + accountUsername), new TypeToken<HashMap<Product, Integer>>() {
        }.getType());
        int number = 1;
        for (Product product : productsInCart.keySet()) {
            cartTableItems.add(new CartTableItem(
                    number,
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    productsInCart.get(product),
                    product.getPrice()
            ));
            number++;
        }
        return cartTableItems;
    }


    public void back(ActionEvent actionEvent) {
        loadFxml(Manager.Addresses.CUSTOMER_MENU);
    }

    public void logOut(ActionEvent actionEvent) {
        super.logout();
    }

    public void select(MouseEvent mouseEvent) {
        if (!(mouseEvent.getTarget() instanceof LabeledText)) {
            TableRow tableRow = ((TableCell) mouseEvent.getTarget()).getTableRow();
            if (tableRow.getIndex() < tableView.getItems().size()) {
                CartTableItem cartTableItem = (CartTableItem) tableView.getItems().get(tableRow.getIndex());
                if (cartTableItem != null)
                    selectedProduct.setText(cartTableItem.getId());
            }
        }
    }


    public void addProduct(ActionEvent actionEvent) {
        if (selectedProduct.getText().matches("\\S{8}")) {
            System.out.println(sendRequest("PRODUCT_QUANTITY" + " " + "INCREASE" + " " + selectedProduct.getText() + " " + accountUsername));
            init();
        }
    }

    public void reduceProduct(ActionEvent actionEvent) {
        if (selectedProduct.getText().matches("\\S{8}")) {
            System.out.println(sendRequest("PRODUCT_QUANTITY" + " " + "DECREASE" + " " + selectedProduct.getText() + " " + accountUsername));
            init();
        }
    }

    public void clearCart(ActionEvent actionEvent) {
        if (sendRequest("IS_CART_EMPTY" + " " + accountUsername).equals("NO")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you really wanna clear your cart?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                System.out.println(sendRequest("CLEAR_CART" + " " + accountUsername));
                init();
            } else alert.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "cart is empty!", ButtonType.OK);
            alert.show();
        }
    }

    public void openProductPage(ActionEvent actionEvent) {
        if (selectedProduct.getText().matches("\\S{8}")) {
            //((ViewCartManager) manager).showProduct(selectedProduct.getText());
            showProduct(selectedProduct.getText());
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "None of the products is selected", ButtonType.OK);
            alert.show();
        }
    }

    private void showProduct(String productId) {
        try {
            FXMLLoader loader = getLoader(Manager.Addresses.PRODUCT_PAGE);
            Parent root = loader.load();
            ProductPageController controller = loader.getController();
            Product product = Gson.INSTANCE.get().fromJson(sendRequest("GET_PRODUCT " + productId), Product.class);
            Auction auction = Gson.INSTANCE.get().fromJson(sendRequest("GET_PRODUCT_AUCTION " + productId), Auction.class);
            controller.setInfos(product, auction);
            controller.init();
            Scene scene = new Scene(root);
            stage.setTitle("AP Project");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openPurchasePage(ActionEvent actionEvent) {
        if (sendRequest("IS_CART_EMPTY" + " " + accountUsername).equals("NO")) {
//            ((ViewCartManager)manager).purchase();
            loadFxml(Manager.Addresses.PURCHASE_PAGE);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "cart is empty \nYou wanna buy air?", ButtonType.OK);
            alert.show();
        }
    }


    public void sort(ActionEvent actionEvent) {
        manager.openSort(this, manager);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }
}
