package ViewController.customer;

import Control.CustomerManagers.ViewOrdersManager;
import Models.Shop.Product.Product;
import ViewController.Controller;
import ViewController.customer.cart.CartTableItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowOrderProductsController extends Controller {

    public TableView tableView;
    public TableColumn numberColumn;
    public TableColumn idColumn;
    public TableColumn nameColumn;
    public TableColumn descriptionColumn;
    public TableColumn quantityColumn;
    public TableColumn priceColumn;

    @Override
    public void init() {
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

    private ArrayList<CartTableItem> getCartTableItems() {
        ArrayList<CartTableItem> cartTableItems = new ArrayList<>();
        if (manager instanceof ViewOrdersManager) {
            HashMap<Product, Integer> productsInCart = ((ViewOrdersManager) manager).getOrderProductsToShow();
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
}
