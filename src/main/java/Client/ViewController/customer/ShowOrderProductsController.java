package Client.ViewController.customer;

import Client.Control.CustomerManagers.ViewOrdersManager;
import Client.ViewController.Controller;
import Client.ViewController.customer.cart.CartTableItem;
import Models.Gson;
import Models.Shop.Product.Product;
import com.google.gson.reflect.TypeToken;
import com.sun.org.apache.xml.internal.security.Init;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ShowOrderProductsController extends Controller {

    public TableView tableView;
    public TableColumn numberColumn;
    public TableColumn idColumn;
    public TableColumn nameColumn;
    public TableColumn descriptionColumn;
    public TableColumn quantityColumn;
    public TableColumn priceColumn;

    public void init(String boughtProducts) {
        ArrayList<CartTableItem> cartTableItems = getCartTableItems(boughtProducts);
        ObservableList<CartTableItem> data = FXCollections.observableList(cartTableItems);

        numberColumn.setCellValueFactory(new PropertyValueFactory<CartTableItem, Integer>("number"));
        idColumn.setCellValueFactory(new PropertyValueFactory<CartTableItem, String>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<CartTableItem, String>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<CartTableItem, String>("description"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<CartTableItem, Integer>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<CartTableItem, Double>("price"));

        tableView.setItems(data);
    }

    private ArrayList<CartTableItem> getCartTableItems(String boughtProducts) {
        ArrayList<CartTableItem> cartTableItems = new ArrayList<>();
//        if (manager instanceof ViewOrdersManager) {
//            HashMap<Product, Integer> productsInCart = ((ViewOrdersManager) manager).getOrderProductsToShow();
        HashMap<Product, Integer> productsInCart = Gson.INSTANCE.get().fromJson(boughtProducts, new TypeToken<HashMap<Product,Integer>>() {}.getType());
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

}
