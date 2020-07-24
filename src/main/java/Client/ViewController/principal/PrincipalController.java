package Client.ViewController.principal;

import Client.ViewController.MainController;
import Server.Control.Manager;
import Client.ViewController.Controller;
import Models.Account.Account;
import Models.Account.Principal;
import Models.Gson;
import Models.Shop.Off.Discount;
import Models.Shop.Request.Request;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PrincipalController extends Controller implements Initializable {
    public Label usernameLabel;
    public TextField firstNameField;
    public TextField lastNameField;
    public TextField emailField;
    public TextField phoneNumberField;
    public TableView<Account> usersTable;
    public TableColumn<Account, String> usernameCol;
    public TableColumn<Account, String> userTypeCol;
    public TableColumn<Account, Double> userBalanceCol;
    public TableView<Discount> discountsTable;
    public TableColumn<Discount, String> discountIdCol;
    public TableColumn<Discount, Integer> discountPercentCol;
    public TableColumn<Discount, LocalDate> discountBeginningDateCol;
    public TableView<Request> requestsTable;
    public TableColumn<Request, String> requestIdCol;
    public TableColumn<Request, String> requestTypeCol;
    public TableColumn<Request, String> requestSenderCol;
    public TextField minAmountField;
    public TextField wageField;
    public Button minAmountButton;
    public Button wageButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        minAmountField.setPromptText(sendRequest("GET_MINIMUM_AMOUNT_IN_WALLET"));
        wageField.setPromptText(sendRequest("GET_WAGE_IN_WALLET"));
        String[] response = sendRequest("GET_ACCOUNT " + accountUsername).split("&&&");
        Principal principal;
        principal = Gson.INSTANCE.get().fromJson(response[1], Principal.class);
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
        usersTable.setItems(FXCollections.observableArrayList(getAllAccounts()));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        userTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        userBalanceCol.setCellValueFactory(new PropertyValueFactory<>("balance"));
    }

    private void initDiscounts() {
        discountsTable.setItems(FXCollections.observableArrayList(getAllDiscounts()));
        discountIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        discountPercentCol.setCellValueFactory(new PropertyValueFactory<>("discountPercent"));
        discountBeginningDateCol.setCellValueFactory(new PropertyValueFactory<>("beginningDate"));
    }

    private void initRequests() {
        requestsTable.setItems(FXCollections.observableArrayList(getAllRequests()));
        requestIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        requestTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        requestSenderCol.setCellValueFactory(new PropertyValueFactory<>("sellerName"));
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

    public void openManageUsers(ActionEvent actionEvent) {
        loadFxml(Addresses.MANAGE_USERS);
    }

    public void openManageProducts(ActionEvent actionEvent) {
        loadFxml(Addresses.MANAGE_PRODUCTS);
    }

    public void openManageRequests(ActionEvent actionEvent) {
        loadFxml(Addresses.MANAGE_REQUESTS);
    }

    public void openManageCategories(ActionEvent actionEvent) {
        loadFxml(Addresses.MANAGE_CATEGORIES);
    }

    public void createDiscountCode(ActionEvent actionEvent) {
        loadFxml(Addresses.CREATE_DISCOUNT_CODE, true);
    }

    public void openOffsMenu(ActionEvent actionEvent) {
        openOffsMenu();
    }

    public void openViewDiscountCodes(ActionEvent actionEvent) {
        loadFxml(Addresses.VIEW_DISCOUNT_CODES);
    }

    public void back() {
        loadFxml(Addresses.MAIN_MENU);
    }

    public void minAmountSet(MouseEvent mouseEvent) {
        if(!minAmountField.getText().isEmpty()) {
            if(minAmountField.getText().matches("\\d+")) {
                int minAmount = Integer.parseInt(minAmountField.getText());
                System.out.println(sendRequest("SET_MINIMUM_AMOUNT_IN_WALLET"+" "+minAmount));
                minAmountField.setPromptText(minAmount+"");
            }
        }
    }

    public void wageSet(MouseEvent mouseEvent) {
        if(!wageField.getText().isEmpty()){
            if(Double.parseDouble(wageField.getText())>0 && Double.parseDouble(wageField.getText()) < 1){
                System.out.println(sendRequest("SET_WAGE_IN_WALLET"+" "+wageField.getText()));
                wageField.setPromptText(wageField.getText());
            }
        }
    }
}
