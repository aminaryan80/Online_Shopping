package Client.ViewController.userPanel.Seller;

import Client.Control.Principal.PrincipalManager;
import Client.Control.Seller.SellerManager;
import Client.ViewController.Controller;
import Models.Account.Account;
import Models.Account.Seller;
import Models.Shop.Log.SellingLog;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

public class SellerMenuController extends Controller {

    @FXML
    private Label userName;
    @FXML
    private Label balance;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField emailText;
    @FXML
    private TextField phoneNumberText;
    @FXML
    private TextField company;
    @FXML
    private TableView<SellingLog> salesHistory;
    @FXML
    private TreeView<String> categories;
    @FXML
    private TableColumn<SellingLog, String> idColumn;
    @FXML
    private TableColumn<SellingLog, LocalDate> dateColumn;
    @FXML
    private TableColumn<SellingLog, Double> amountColumn;
    private Seller seller;

    public void setSeller(Account seller) {
        this.seller = (Seller) seller;
    }

    public void init() {
        userName.setText(seller.getUsername());
        balance.setText(String.valueOf(seller.getBalance()));
        firstName.setText(seller.getFirstName());
        lastName.setText(seller.getLastName());
        emailText.setText(seller.getEmail());
        company.setText(seller.getCompanyName());
        phoneNumberText.setText(seller.getPhoneNumber());
        initCategories();
        initSalesHistory();
    }

    private void initSalesHistory() {
        salesHistory.setItems(FXCollections.observableArrayList(seller.getAllLogs()));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("money"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void initCategories() {
        categories.setRoot(manager.getCategoriesInTable());
    }

    public void editPassword(ActionEvent actionEvent) {
        manager.editPassword();
    }

    public void updateProfile(ActionEvent actionEvent) {
        String email = emailText.getText();
        String phoneNumber = phoneNumberText.getText();
        if (((PrincipalManager) manager).isEnteredInputValid(email, phoneNumber)) {
            seller.setFirstName(firstName.getText());
            seller.setLastName(lastName.getText());
            seller.setEmail(emailText.getText());
            seller.setPhoneNumber(phoneNumberText.getText());
            seller.setCompanyName(company.getText());
        }
    }

    public void manageProducts(ActionEvent actionEvent) {
        ((SellerManager) manager).openManageProducts();
    }

    public void viewOffs(ActionEvent actionEvent) {
        ((SellerManager) manager).openManageOffs();
    }
}
