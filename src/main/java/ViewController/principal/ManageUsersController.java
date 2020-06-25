package ViewController.principal;

import Control.Principal.ManageUsersManager;
import Models.Account.Account;
import Models.Shop.Product.Product;
import ViewController.Controller;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class ManageUsersController extends Controller implements Initializable {
    @FXML
    private TableView<Account> usersTable;
    @FXML
    private TableColumn<Account, String> usernameCol;
    @FXML
    private TableColumn<Account, String> userEmailCol;
    @FXML
    private TableColumn<Account, Double> userBalanceCol;
    @FXML
    private TextField viewUserIdField;
    @FXML
    private Label viewUserLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneNumberField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<Object> objects = new ArrayList<>();
        objects.addAll(Account.getAllAccounts());
        initTable(objects);
    }

    public void initTable(ArrayList<Object> tableObjects) {
        ArrayList<Account> tableAccounts = new ArrayList<>();
        for (Object tableProduct : tableObjects) {
            tableAccounts.add((Account) tableProduct);
        }
        usersTable.setItems(FXCollections.observableArrayList(tableAccounts));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        userEmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        userBalanceCol.setCellValueFactory(new PropertyValueFactory<>("balance"));
    }

    public void viewUser(ActionEvent actionEvent) {
        String username = viewUserIdField.getText();
        viewUserLabel.setText(((ManageUsersManager) manager).viewUsername(username));
    }

    public void deleteUser(ActionEvent actionEvent) {
        String username = viewUserIdField.getText();
        ((ManageUsersManager) manager).deleteUsername(username);
    }

    public void createNewPrincipal(ActionEvent actionEvent) {
        // username, password, email, phoneNumber, firstName, lastName
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(usernameField.getText());
        inputs.add(passwordField.getText());
        inputs.add(emailField.getText());
        inputs.add(phoneNumberField.getText());
        inputs.add(firstNameField.getText());
        inputs.add(lastNameField.getText());
        ((ManageUsersManager) manager).createManagerProfile(inputs);
    }

    public void sort(ActionEvent actionEvent) {
        manager.openSort(this, manager);
    }
}
