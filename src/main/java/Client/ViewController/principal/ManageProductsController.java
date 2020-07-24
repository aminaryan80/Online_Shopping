package Client.ViewController.principal;

import Models.Shop.Category.Sort;
import Server.Control.Manager;
import Client.ViewController.Controller;
import Models.Shop.Product.Product;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.*;

public class ManageProductsController extends Controller implements Initializable {
    public TableView<Product> productsTable;
    public TableColumn<Product, String> productIdCol;
    public TableColumn<Product, String> productNameCol;
    public TableColumn<Product, String> productPriceCol;
    public TableColumn<Product, String> productSellerCol;
    public TextField productIdField;
    private Sort currentSort;
    private List<Product> products;
    private List<Product> myProducts;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        myProducts = getAllProducts();
        products = new ArrayList<>();
        products.addAll(myProducts);
        productsTable.setItems(FXCollections.observableArrayList(products));
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productSellerCol.setCellValueFactory(new PropertyValueFactory<>("sellerUsername"));
    }

    public void deleteProduct(ActionEvent actionEvent) {
        String productId = productIdField.getText();
        String response = sendRequest("DELETE_PRODUCT " + productId);
        if (response.equals("0")) {
            success("Product deleted successfully.");
        } else error("Something went wrong.");
    }

    public void initTable(ArrayList<Object> tableObjects) {
        ArrayList<Product> tableProducts = new ArrayList<>();
        for (Object tableObject : tableObjects) {
            tableProducts.add((Product) tableObject);
        }
        productsTable.setItems(FXCollections.observableArrayList(tableProducts));
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productSellerCol.setCellValueFactory(new PropertyValueFactory<>("sellerUsername"));
    }

    public void sort(ActionEvent actionEvent) {
        openSort(this);
    }

    public void back() {
        loadFxml(Addresses.PRINCIPAL_MENU);
    }

    public ArrayList<Object> sort(String sort, boolean isAscending) {
        products = new ArrayList<>();
        products.addAll(myProducts);
        currentSort = new Sort(sort, isAscending);
        applySort();
        return new ArrayList<>(products);
    }

    private void applySort() {
        if (currentSort == null) {
            return;
        }
        String field = currentSort.getField();
        if (field.equals("price")) {
            sortByPrice();
        } else if (field.equals("name")) {
            sortByName();
        } else {
            sortByRating();
        }
        if (!currentSort.isAscending()) {
            Collections.reverse(products);
        }
    }

    private void sortByPrice() {
        Product[] productsForSort = products.toArray(new Product[0]);
        for (int i = 0; i < productsForSort.length; i++) {
            for (int j = i + 1; j < productsForSort.length; j++) {
                if (productsForSort[i].getPrice() > productsForSort[j].getPrice()) {
                    Product temp = productsForSort[i];
                    productsForSort[i] = productsForSort[j];
                    productsForSort[j] = temp;
                }
            }
        }
        products = Arrays.asList(productsForSort);
    }

    private void sortByName() {
        Product[] productsForSort = products.toArray(new Product[0]);
        for (int i = 0; i < productsForSort.length; i++) {
            for (int j = i + 1; j < productsForSort.length; j++) {
                if (productsForSort[i].getName().compareTo(productsForSort[j].getName()) > 0) {
                    Product temp = productsForSort[i];
                    productsForSort[i] = productsForSort[j];
                    productsForSort[j] = temp;
                }
            }
        }
        products = Arrays.asList(productsForSort);
    }

    private void sortByRating() {
        Product[] productsForSort = products.toArray(new Product[0]);
        for (int i = 0; i < productsForSort.length; i++) {
            for (int j = i + 1; j < productsForSort.length; j++) {
                if (productsForSort[i].getRate() > productsForSort[j].getRate()) {
                    Product temp = productsForSort[i];
                    productsForSort[i] = productsForSort[j];
                    productsForSort[j] = temp;
                }
            }
        }
        products = Arrays.asList(productsForSort);
    }

    public ArrayList<String> getSortFields() {
        ArrayList<String> fields = new ArrayList<>();
        fields.add("price");
        fields.add("name");
        fields.add("rating");
        return fields;
    }

    public ArrayList<Object> disableSort() {
        currentSort = null;
        return new ArrayList<>(myProducts);
    }
}
