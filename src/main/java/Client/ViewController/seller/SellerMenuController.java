package Client.ViewController.seller;

import Server.Control.Manager;
import Client.ViewController.Controller;
import Models.Account.Seller;
import Models.Gson;
import Models.Shop.Log.SellingLog;
import com.google.gson.reflect.TypeToken;
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

public class SellerMenuController extends Controller implements Initializable {

    public Label userName;
    public Label balance;
    public TextField firstName;
    public TextField lastName;
    public TextField emailText;
    public TextField phoneNumberText;
    public TextField company;
    public TableView<SellingLog> salesHistory;
    public TreeView<String> categoriesTable;
    public TableColumn<SellingLog, String> idColumn;
    public TableColumn<SellingLog, LocalDate> dateColumn;
    public TableColumn<SellingLog, Double> amountColumn;
    public TextField moneyToTakeAmountFiled;
    public Button takeButton;
    public TextField moneyToPutAmountFiled;
    public Button putButton;
    public Label moneyInBankField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       init();
    }

    public void init(){
        moneyInBankField.setText(sendRequest("GET_MONEY_IN_BANK"+" "+accountUsername+" "+accountType));
        String[] response = sendRequest("GET_ACCOUNT " + accountUsername).split("&&&");
        Seller seller;
        seller = Gson.INSTANCE.get().fromJson(response[1], Seller.class);
        userName.setText(seller.getUsername());
        balance.setText(""+seller.getWallet().getAmount());
        firstName.setText(seller.getFirstName());
        lastName.setText(seller.getLastName());
        emailText.setText(seller.getEmail());
        company.setText(seller.getCompanyName());
        phoneNumberText.setText(seller.getPhoneNumber());
        initCategories();
        initSalesHistory();
    }

    private void initCategories() {
        categoriesTable.setRoot(getCategoriesInTable());
    }

    private void initSalesHistory() {
        salesHistory.setItems(FXCollections.observableArrayList(getAllLogs()));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("money"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private ArrayList<SellingLog> getAllLogs() {
        return Gson.INSTANCE.get().fromJson(sendRequest("GET_SELLER_LOGS " + accountUsername),
                new TypeToken<ArrayList<SellingLog>>() {
                }.getType());
    }

    public void editPassword(ActionEvent actionEvent) {
        loadFxml(Addresses.EDIT_PASSWORD, true);
    }

    public void updateProfile(ActionEvent actionEvent) {
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(firstName.getText());
        inputs.add(lastName.getText());
        inputs.add(emailText.getText());
        inputs.add(phoneNumberText.getText());
        inputs.add(company.getText());
        String response = sendRequest("UPDATE_PROFILE " + accountUsername + " " + Gson.INSTANCE.get().toJson(inputs));
        if (response.equals("0")) {
            success("Profile changed successfully.");
        } else error("something went wrong.");
    }

    public void manageProducts(ActionEvent actionEvent) {
        loadFxml(Addresses.EDIT_PRODUCTS_MENU);
    }

    public void viewOffs(ActionEvent actionEvent) {
        loadFxml(Addresses.EDIT_OFFS);
    }

    public void back() {
        loadFxml(Addresses.MAIN_MENU);
    }

    public void takeMoney(MouseEvent mouseEvent) {
        if(!moneyToTakeAmountFiled.getText().isEmpty()) {
            Integer moneyToTake = Integer.parseInt(moneyToTakeAmountFiled.getText());
            if(moneyToTake != null && moneyToTake>0 && moneyToTake< Double.parseDouble(balance.getText())){
                System.out.println(sendRequest("TAKE_FROM_WALLET"+" "+accountUsername+" "+moneyToTake));
                init();
            }
        }
    }

    public void putMoney(MouseEvent mouseEvent) {
        if(!moneyToPutAmountFiled.getText().isEmpty()){
            Integer moneyToPut = Integer.parseInt(moneyToPutAmountFiled.getText());
            if(moneyToPut!=null && moneyToPut>0) {
                System.out.println(sendRequest("PUT_TO_WALLET"+" "+accountUsername+" "+accountType+" "+moneyToPut));
                init();
            }
        }
    }
}
