package ViewController.customer;

import Control.CustomerManagers.ViewCartManager;
import Models.Shop.Product.Product;
import ViewController.Controller;
import com.sun.javafx.scene.control.skin.LabeledText;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javax.swing.text.View;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ViewCartController extends Controller {


    public TableView tableView;
    public TableColumn numberColumn;
    public TableColumn idColumn;
    public TableColumn nameColumn;
    public TableColumn descriptionColumn;
    public TableColumn quantityColumn;
    public TableColumn priceColumn;
    public Label selectedProduct;
    public Label totalAmount;

    private ArrayList<CartTableItem> getCartTableItems() {
        ArrayList<CartTableItem> cartTableItems = new ArrayList<>();
        if (manager instanceof ViewCartManager) {
            HashMap<Product, Integer> productsInCart = ((ViewCartManager) manager).getProductsInCart();
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
        }
        return cartTableItems;
    }


    public void back(MouseEvent mouseEvent) {
        super.back(null);
    }

    public void logOut(MouseEvent mouseEvent) {
        super.logout();
    }

    public void select(MouseEvent mouseEvent) {
        System.out.println(mouseEvent.getSource().toString());
        if (!(mouseEvent.getTarget() instanceof LabeledText)) {
            TableRow tableRow = ((TableCell) mouseEvent.getTarget()).getTableRow();
            CartTableItem cartTableItem = (CartTableItem) tableView.getItems().get(tableRow.getIndex());
            if (cartTableItem != null)
                selectedProduct.setText(cartTableItem.getId());
        }
    }


    public void init() {
        totalAmount.setText(((ViewCartManager) manager).getTotalPrice(null) + "$");
        ArrayList<CartTableItem> cartTableItems = getCartTableItems();
        ObservableList<CartTableItem> data = FXCollections.observableList(cartTableItems);

        numberColumn.setCellValueFactory(new PropertyValueFactory<CartTableItem, Integer>("number"));
        idColumn.setCellValueFactory(new PropertyValueFactory<CartTableItem, String>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<CartTableItem, String>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<CartTableItem, String>("description"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<CartTableItem, Integer>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<CartTableItem, Double>("price"));

        tableView.setItems(data);
    }

    public void addProduct(MouseEvent mouseEvent) throws ViewCartManager.ProductDoNotExistInCartException {
        if (selectedProduct.getText().matches("\\S{8}"))
            ((ViewCartManager) manager).productQuantity(selectedProduct.getText(), true);
        init();
    }

    public void reduceProduct(MouseEvent mouseEvent) throws ViewCartManager.ProductDoNotExistInCartException {
        if (selectedProduct.getText().matches("\\S{8}"))
            ((ViewCartManager) manager).productQuantity(selectedProduct.getText(), false);
        init();
    }

    public void clearCart(MouseEvent mouseEvent) {
        ((ViewCartManager) manager).clearCart();
        init();
    }

    public void openProductPage(MouseEvent mouseEvent) {
    }

    public void openPurchasePage(MouseEvent mouseEvent) {
    }
}
