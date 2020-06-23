package ViewController.principal;

import Control.Principal.PrincipalManager;
import Models.Account.Account;
import Models.Account.Principal;
import Models.Shop.Off.Discount;
import Models.Shop.Request.Request;
import ViewController.Controller;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

public class PrincipalController extends Controller {
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
    private TableView<Account> usersTable;
    @FXML
    private TableColumn<Account, String> usernameCol;
    @FXML
    private TableColumn<Account, String> userTypeCol;
    @FXML
    private TableColumn<Account, Double> userBalanceCol;
    @FXML
    private TableView<Discount> discountsTable;
    @FXML
    private TableColumn<Discount, String> discountIdCol;
    @FXML
    private TableColumn<Discount, Integer> discountPercentCol;
    @FXML
    private TableColumn<Discount, LocalDate> discountBeginningDateCol;
    @FXML
    private TableView<Request> requestsTable;
    @FXML
    private TableColumn<Request, String> requestIdCol;
    @FXML
    private TableColumn<Request, String> requestTypeCol;
    @FXML
    private TableColumn<Request, String> requestSenderCol;
    private Principal principal;

    public void setPrincipal(Account principal) {
        this.principal = (Principal) principal;
    }

    public void init() {
        usernameLabel.setText(principal.getUsername());
        firstNameField.setText(principal.getFirstName());
        lastNameField.setText(principal.getLastName());
        emailField.setText(principal.getEmail());
        phoneNumberField.setText(principal.getPhoneNumber());
        initUsers();
        initDiscounts();
        initRequests();
    }

    private void initUsers() {
        usersTable.setItems(FXCollections.observableArrayList(Account.getAllAccounts()));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        userTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        userBalanceCol.setCellValueFactory(new PropertyValueFactory<>("balance"));
    }

    private void initDiscounts() {
        discountsTable.setItems(FXCollections.observableArrayList(Discount.getAllDiscounts()));
        discountIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        discountPercentCol.setCellValueFactory(new PropertyValueFactory<>("discountPercent"));
        discountBeginningDateCol.setCellValueFactory(new PropertyValueFactory<>("beginningDate"));
    }

    private void initRequests() {
        requestsTable.setItems(FXCollections.observableArrayList(Request.getAllRequests()));
        requestIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        requestTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        requestIdCol.setCellValueFactory(new PropertyValueFactory<>("sellerName"));
    }

    public void updateProfile(ActionEvent actionEvent) {
        String email = emailField.getText();
        String phoneNumber = phoneNumberField.getText();
        if (((PrincipalManager) manager).isEnteredInputValid(email, phoneNumber)) {
            principal.setFirstName(firstNameField.getText());
            principal.setLastName(lastNameField.getText());
            principal.setEmail(emailField.getText());
            principal.setPhoneNumber(phoneNumberField.getText());
        }
    }

    public void editPassword(ActionEvent actionEvent) {
        ((PrincipalManager) manager).editPassword();
    }

    public void openManageUsers(ActionEvent actionEvent) {
        ((PrincipalManager) manager).openManageUsers();
    }

    public void openManageProducts(ActionEvent actionEvent) {
        ((PrincipalManager) manager).openManageProducts();
    }

    public void openManageCategories(ActionEvent actionEvent) {
        ((PrincipalManager) manager).openManageCategories();
    }

    public void openManageRequests(ActionEvent actionEvent) {
        ((PrincipalManager) manager).openManageRequests();
    }

    public void createDiscountCode(ActionEvent actionEvent) {
        ((PrincipalManager) manager).openCreateDiscountCode();
    }

    public void openOffsMenu(ActionEvent actionEvent) {
        ((PrincipalManager) manager).openOffsMenu();
    }

    public void openViewDiscountCodes(ActionEvent actionEvent) {
        ((PrincipalManager) manager).openViewDiscountCodes();

    }
}
