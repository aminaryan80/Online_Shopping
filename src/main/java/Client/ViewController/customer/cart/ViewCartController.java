package Client.ViewController.customer.cart;

import Client.Control.CustomerManagers.ViewCartManager;
import Client.ViewController.Controller;
import Models.Shop.Product.Product;
import com.sun.javafx.scene.control.skin.LabeledText;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

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

    public void init() {
        totalAmount.setText(((ViewCartManager) manager).getTotalPrice(null) + "$");
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


    public void back(ActionEvent actionEvent) {
        super.back(null);
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


    public void addProduct(ActionEvent actionEvent) throws ViewCartManager.ProductDoNotExistInCartException {
        if (selectedProduct.getText().matches("\\S{8}")) {
            ((ViewCartManager) manager).productQuantity(selectedProduct.getText(), true);
            init();
        }
    }

    public void reduceProduct(ActionEvent actionEvent) throws ViewCartManager.ProductDoNotExistInCartException {
        if (selectedProduct.getText().matches("\\S{8}")) {
            ((ViewCartManager) manager).productQuantity(selectedProduct.getText(), false);
            init();
        }
    }

    public void clearCart(ActionEvent actionEvent) {
        if(!((ViewCartManager)manager).isCartEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you really wanna clear your cart?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                ((ViewCartManager) manager).clearCart();
                init();
            } else alert.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "cart is empty!", ButtonType.OK);
            alert.show();
        }
    }

    public void openProductPage(ActionEvent actionEvent) {
        if (selectedProduct.getText().matches("\\S{8}")) {
            ((ViewCartManager) manager).showProduct(selectedProduct.getText());
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "None of the products is selected", ButtonType.OK);
            alert.show();
        }
    }

    public void openPurchasePage(ActionEvent actionEvent) {
        if(!((ViewCartManager)manager).isCartEmpty())
        ((ViewCartManager)manager).purchase();
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "cart is empty \nYou wanna buy air?", ButtonType.OK);
            alert.show();
        }
    }

    public void sort(ActionEvent actionEvent) {
        manager.openSort(this, manager);
    }
}
