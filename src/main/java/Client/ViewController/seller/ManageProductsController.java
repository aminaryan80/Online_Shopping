package Client.ViewController.seller;

import Models.Shop.Category.Sort;
import Server.Control.Manager;
import Client.ViewController.Controller;
import Models.Account.Account;
import Models.Account.Customer;
import Models.Gson;
import Models.Shop.Category.Feature;
import Models.Shop.Product.Product;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.*;

public class ManageProductsController extends Controller implements Initializable {

    public TableView<Product> productTableView;
    public TableView<Feature> features;
    public TableView<Account> buyers;
    public TableColumn<Product, String> idColumn;
    public TableColumn<Product, String> productsNameColumn;
    public TableColumn<Product, Double> priceColumn;
    public TableColumn<Account, String> buyersNameColumn;
    public TableColumn<Feature, String> featureColumn;
    public TableColumn<Feature, String> valueColumn;
    public TextField id;
    public TextField name;
    public TextField price;
    public TextField category;
    public TextField description;
    public TextField rate;
    public TextField isAvailable;
    public ChoiceBox changeableFeatures;
    public TextField changeableFeatureValue;
    private Sort currentSort;
    private List<Product> products;
    private List<Product> myProducts;
    private Product product;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        myProducts = new ArrayList<>(Gson.INSTANCE.get().fromJson(sendRequest("GET_SELLER_PRODUCTS " + accountUsername),
                new TypeToken<ArrayList<Product>>() {
                }.getType()));
        products = new ArrayList<>();
        products.addAll(myProducts);
        productTableView.setItems(FXCollections.observableArrayList(products));
        productsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    public void remove(ActionEvent actionEvent) {
        sendRequest("DELETE_PRODUCT " + accountUsername + " " + id.getText());
    }

    public void update(ActionEvent actionEvent) {
        if (!product.getName().equals(name.getText())) {
            sendRequest("EDIT_PRODUCT " + accountUsername + " " + id.getText() + " NAME " + name.getText());
        }
        if (product.getPrice() != Double.parseDouble(price.getText())) {
            sendRequest("EDIT_PRODUCT " + accountUsername + " " + id.getText() + " PRICE " + price.getText());
        }
        if (product.isAvailable() != Boolean.parseBoolean(isAvailable.getText())) {
            sendRequest("EDIT_PRODUCT " + accountUsername + " " + id.getText() + " IS_AVAILABLE " + isAvailable.getText());
        }
        if (!product.getDescription().equals(description.getText())) {
            sendRequest("EDIT_PRODUCT " + accountUsername + " " + id.getText() + " DESCRIPTION " + description.getText());
        }
    }

    public void add(ActionEvent actionEvent) {
        if (!price.getText().matches("^\\d+(\\.\\d+)?$")) {
            error("wrong price format");
            return;
        }
        //((ManageProductsManager) manager).featuresPopUp(this);
        ((FeaturesPopUpController) loadFxml(Addresses.ADD_PRODUCT_POP_UP, true)).setController(this);
    }

    public void addProduct(ArrayList<Feature> allFeatures) {
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(name.getText());
        inputs.add(category.getText());
        inputs.add(price.getText());
        inputs.add(isAvailable.getText());
        inputs.add(description.getText());
        sendRequest("CREATE_PRODUCT " + Gson.INSTANCE.get().toJson(inputs) + "&&&" + Gson.INSTANCE.get().toJson(allFeatures));
        success("Product added successfully.");
    }

    public void updateScene(MouseEvent mouseEvent) {
        product = productTableView.getSelectionModel().getSelectedItem();
        initBuyers();
        initFeatures();
        initFields();
    }

    private void initFields() {
        id.setText(product.getId());
        name.setText(product.getName());
        description.setText(product.getDescription());
        rate.setText(String.valueOf(product.getRate()));
        price.setText(String.valueOf(product.getPrice()));
        category.setText(product.getCategory().getName());
        isAvailable.setText(String.valueOf(product.isAvailable()));
    }

    private void initFeatures() {
        features.setItems(FXCollections.observableArrayList(product.getFeatures()));
        featureColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        changeableFeatures.setItems(FXCollections.observableList(product.getCategory().getFeaturesNames()));
    }

    private void initBuyers() {
        buyers.setItems(FXCollections.observableArrayList(getProductBuyers()));
        buyersNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
    }

    private ArrayList<Customer> getProductBuyers() {
        return Gson.INSTANCE.get().fromJson(sendRequest("GET_PRODUCT_BUYERS " + product.getId()),
                new TypeToken<ArrayList<Customer>>() {
                }.getType());
    }

    public void updateFeatures() {
        sendRequest("EDIT_PRODUCT " + accountUsername + " " + id.getText() + " " +
                changeableFeatures.getSelectionModel().getSelectedItem() + " " + changeableFeatureValue.getText());
    }

    public void sort(ActionEvent actionEvent) {
        openSort(this);
    }

    public void back() {
        loadFxml(Addresses.SELLER_MENU);
    }

    public ArrayList<String> getSortFields() {
        ArrayList<String> fields = new ArrayList<>();
        fields.add("price");
        fields.add("name");
        fields.add("rating");
        return fields;
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

    public ArrayList<Object> disableSort() {
        currentSort = null;
        return new ArrayList<>(myProducts);
    }

    public void initTable(ArrayList<Object> tableObjects) {
        ArrayList<Product> tableProducts = new ArrayList<>();
        for (Object tableProduct : tableObjects) {
            tableProducts.add((Product) tableProduct);
        }
        productTableView.setItems(FXCollections.observableArrayList(tableProducts));
        productsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        //categoryColumn.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    }
}

