package Client.ViewController.customer;

import Server.Control.Manager;
import Client.ViewController.Controller;
import Models.Account.Customer;
import Models.Gson;
import Models.Shop.Log.BuyingLog;
import Models.Shop.Off.Discount;
import Models.Shop.Product.Product;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class CustomerController extends Controller implements Initializable {
    public Label balanceLabel;
    public Label usernameLabel;
    public TextField firstNameField;
    public TextField lastNameField;
    public TextField emailField;
    public TextField phoneNumberField;
    public TableView<Product> cartTable;
    public TableColumn<Product, String> productIdCol;
    public TableColumn<Product, String> productNameCol;
    public TableColumn<Product, Double> priceCol;
    public TableView<Discount> discountsTable;
    public TableColumn<Discount, String> discountIdCol;
    public TableColumn<Discount, Integer> discountPercentCol;
    public TableColumn<Discount, LocalDate> discountBeginningDateCol;
    public TableColumn<Discount, LocalDate> discountEndingDateCol;

    public TableView<BuyingLog> ordersTable;
    public TableColumn<BuyingLog, String> orderIdCol;
    public TableColumn<BuyingLog, LocalDate> orderDate;
    public TableColumn<BuyingLog, Double> orderAmount;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String[] response = sendRequest("GET_ACCOUNT " + accountUsername).split("&&&");
        Customer customer;
        customer = Gson.INSTANCE.get().fromJson(response[1], Customer.class);
        usernameLabel.setText(customer.getUsername());
        balanceLabel.setText(customer.getWallet().getAmount() + "");
        firstNameField.setText(customer.getFirstName());
        lastNameField.setText(customer.getLastName());
        emailField.setText(customer.getEmail());
        phoneNumberField.setText(customer.getPhoneNumber());
        initCart();
        initDiscounts();
        initOrders();
    }

    private void initCart() {
        HashMap<Product, Integer> hashMap = Gson.INSTANCE.get().fromJson(sendRequest("GET_CART_PRODUCTS" + " " + accountUsername),
                new TypeToken<HashMap<Product, Integer>>() {
                }.getType());
        cartTable.setItems(FXCollections.observableArrayList(hashMap.keySet()));
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private void initDiscounts() {
        discountsTable.setItems(FXCollections.observableArrayList(getAllDiscounts()));
        discountIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        discountPercentCol.setCellValueFactory(new PropertyValueFactory<>("discountPercent"));
        discountBeginningDateCol.setCellValueFactory(new PropertyValueFactory<>("beginningDate"));
        discountEndingDateCol.setCellValueFactory(new PropertyValueFactory<>("endingDate"));
    }

    private void initOrders() {
        ordersTable.setItems(FXCollections.observableArrayList(getAllLogs()));
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        orderDate.setCellValueFactory(new PropertyValueFactory<>("stringDate"));
        orderAmount.setCellValueFactory(new PropertyValueFactory<>("money"));
    }

    private ArrayList<BuyingLog> getAllLogs() {
        return Gson.INSTANCE.get().fromJson(sendRequest("GET_CUSTOMER_LOGS " + accountUsername),
                new TypeToken<ArrayList<BuyingLog>>() {
                }.getType());
    }

    public void updateProfile(ActionEvent actionEvent) {
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(firstNameField.getText());
        inputs.add(lastNameField.getText());
        inputs.add(emailField.getText());
        inputs.add(phoneNumberField.getText());
        String response = sendRequest("UPDATE_PROFILE " + accountUsername + " " + Gson.INSTANCE.get().toJson(inputs));
        if (response.equals("0")) {
            success("Profile changed successfully.");
        } else error("something went wrong.");
    }

    public void editPassword(ActionEvent actionEvent) {
        loadFxml(Addresses.EDIT_PASSWORD, true);
    }

    public void openCart(ActionEvent actionEvent) {
        loadFxml(Addresses.VIEW_CART);
    }

    public void openOrders(ActionEvent actionEvent) {
        loadFxml(Addresses.VIEW_ORDERS);
    }

    public void back() {
        loadFxml(Addresses.MAIN_MENU);
    }
}
