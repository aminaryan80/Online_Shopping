package Client.ViewController.customer;

import Client.Control.CustomerManagers.CustomerManager;
import Client.Control.Manager;
import Client.Control.Principal.PrincipalManager;
import Client.ViewController.Controller;
import Models.Account.Account;
import Models.Account.Customer;
import Models.Shop.Log.BuyingLog;
import Models.Shop.Off.Discount;
import Models.Shop.Product.Product;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

public class CustomerController extends Controller {
    @FXML
    private Label balanceLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TableView<Product> cartTable;
    @FXML
    private TableColumn<Product, String> productIdCol;
    @FXML
    private TableColumn<Product, String> productNameCol;
    @FXML
    private TableColumn<Product, Double> priceCol;
    @FXML
    private TableView<Discount> discountsTable;
    @FXML
    private TableColumn<Discount, String> discountIdCol;
    @FXML
    private TableColumn<Discount, Integer> discountPercentCol;
    @FXML
    private TableColumn<Discount, LocalDate> discountBeginningDateCol;
    @FXML
    public TableColumn<Discount, LocalDate> discountEndingDateCol;


    @FXML
    private TableView<BuyingLog> ordersTable;
    @FXML
    private TableColumn<BuyingLog, String> orderIdCol;
    @FXML
    private TableColumn<BuyingLog, LocalDate> orderDate;
    @FXML
    private TableColumn<BuyingLog, Double> orderAmount;

    private Customer customer;

    public void setCustomer(Account customer) {
        this.customer = (Customer) customer;
    }

    public void init() {
        usernameLabel.setText(customer.getUsername());
        balanceLabel.setText(customer.getBalance() + "");
        firstNameField.setText(customer.getFirstName());
        lastNameField.setText(customer.getLastName());
        emailField.setText(customer.getEmail());
        phoneNumberField.setText(customer.getPhoneNumber());
        initCart();
        initDiscounts();
        initOrders();
    }

    private void initCart() {
        cartTable.setItems(FXCollections.observableArrayList(customer.getCart().getProducts()));
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private void initDiscounts() {
        discountsTable.setItems(FXCollections.observableArrayList(Discount.getAllDiscounts()));
        discountIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        discountPercentCol.setCellValueFactory(new PropertyValueFactory<>("discountPercent"));
        discountBeginningDateCol.setCellValueFactory(new PropertyValueFactory<>("beginningDate"));
        discountEndingDateCol.setCellValueFactory(new PropertyValueFactory<>("endingDate"));
    }

    private void initOrders() {
        ordersTable.setItems(FXCollections.observableArrayList(customer.getAllLogs()));
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        orderDate.setCellValueFactory(new PropertyValueFactory<>("stringDate"));
        orderAmount.setCellValueFactory(new PropertyValueFactory<>("money"));
    }

    public void updateProfile(ActionEvent actionEvent) {
        String email = emailField.getText();
        String phoneNumber = phoneNumberField.getText();
        if (((PrincipalManager) manager).isEnteredInputValid(email, phoneNumber)) {
            customer.setFirstName(firstNameField.getText());
            customer.setLastName(lastNameField.getText());
            customer.setEmail(emailField.getText());
            customer.setPhoneNumber(phoneNumberField.getText());
        }
    }

    public void editPassword(ActionEvent actionEvent) {
        manager.editPassword();
    }

    public void openCart(ActionEvent actionEvent) {
        loadFxml(Manager.Addresses.VIEW_CART);
//        ((CustomerManager) manager).openCart();
    }

    public void openOrders(ActionEvent actionEvent) {
        ((CustomerManager) manager).openOrders();
    }
}
