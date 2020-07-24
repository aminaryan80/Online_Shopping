package Client.ViewController.principal;

import Client.ViewController.Controller;
import Models.Account.Account;
import Models.Account.Customer;
import Models.Account.Principal;
import Models.Account.Seller;
import Models.Gson;
import Models.Shop.Category.Sort;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.*;


public class ManageUsersController extends Controller implements Initializable {
    public TableView<Account> usersTable;
    public TableColumn<Account, String> usernameCol;
    public TableColumn<Account, String> userEmailCol;
    public TableColumn<Account, Double> userBalanceCol;
    public TableColumn<Account, String> userStatusCol;
    public TextField viewUserIdField;
    public Label viewUserLabel;
    public TextField usernameField;
    public TextField passwordField;
    public TextField firstNameField;
    public TextField lastNameField;
    public TextField emailField;
    public TextField phoneNumberField;
    private List<Account> users;
    private List<Account> myUsers;
    private Sort currentSort;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        myUsers = getAllAccounts();
        users = new ArrayList<>();
        users.addAll(myUsers);
        usersTable.setItems(FXCollections.observableArrayList(myUsers));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        userEmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        userBalanceCol.setCellValueFactory(new PropertyValueFactory<>("balance"));
        userStatusCol.setCellValueFactory(new PropertyValueFactory<>("isOnline"));
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
        openSort(this);
    }

    public void back() {
        loadFxml(Addresses.PRINCIPAL_MENU);
    }

    public ArrayList<Object> sort(String sort, boolean isAscending) {
        users = new ArrayList<>();
        users.addAll(myUsers);
        currentSort = new Sort(sort, isAscending);
        applySort();
        return new ArrayList<>(users);
    }

    public ArrayList<String> getSortFields() {
        ArrayList<String> fields = new ArrayList<>();
        fields.add("name");
        return fields;
    }

    public ArrayList<Object> disableSort() {
        currentSort = null;
        return new ArrayList<>(myUsers);
    }

    private void applySort() {
        if (currentSort == null) {
            return;
        }
        Account[] usersForSort = users.toArray(new Account[0]);
        for (int i = 0; i < usersForSort.length; i++) {
            for (int j = i + 1; j < usersForSort.length; j++) {
                if (usersForSort[i].getName().compareTo(usersForSort[j].getName()) > 0) {
                    Account temp = usersForSort[i];
                    usersForSort[i] = usersForSort[j];
                    usersForSort[j] = temp;
                }
            }
        }
        if (!currentSort.isAscending()) {
            Collections.reverse(users);
        }
        users = Arrays.asList(usersForSort);
    }
}
