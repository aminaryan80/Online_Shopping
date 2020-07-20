package Client.ViewController.principal;

import Client.ViewController.Controller;
import Models.Account.Account;
import Models.Account.Customer;
import Models.Account.Principal;
import Models.Account.Seller;
import Models.Gson;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
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
    public TableView<Account> usersTable;
    public TableColumn<Account, String> usernameCol;
    public TableColumn<Account, String> userEmailCol;
    public TableColumn<Account, Double> userBalanceCol;
    public TextField viewUserIdField;
    public Label viewUserLabel;
    public TextField usernameField;
    public TextField passwordField;
    public TextField firstNameField;
    public TextField lastNameField;
    public TextField emailField;
    public TextField phoneNumberField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usersTable.setItems(FXCollections.observableArrayList(getAllAccounts()));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        userEmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        userBalanceCol.setCellValueFactory(new PropertyValueFactory<>("balance"));
    }

    public void viewUser(ActionEvent actionEvent) {
        String username = viewUserIdField.getText();
        String[] response = sendRequest("GET_ACCOUNT " + username).split("&&&");
        Account account = null;
        if (response[0].equals("P")) {
            account = Gson.INSTANCE.get().fromJson(response[1], Principal.class);
        } else if (response[0].equals("C")) {
            account = Gson.INSTANCE.get().fromJson(response[1], Customer.class);
        } else if (response[0].equals("S")) {
            account = Gson.INSTANCE.get().fromJson(response[1], Seller.class);
        }
        viewUserLabel.setText(account.toString());
    }

    public void deleteUser(ActionEvent actionEvent) {
        String username = viewUserIdField.getText();
        String response = sendRequest("DELETE_ACCOUNT " + accountUsername + " " + username);
        if (response.equals("0")) {
            success("Account deleted successfully.");
        } else error("Something went wrong.");
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
        String response = sendRequest("CREATE_PROFILE_MANAGER " + Gson.INSTANCE.get().toJson(inputs));
        if (response.equals("0")) {
            success("New principal created successfully.");
        } else error("Invalid inputs.");
    }

    public void sort(ActionEvent actionEvent) {
        manager.openSort(this, manager);
    }
}
