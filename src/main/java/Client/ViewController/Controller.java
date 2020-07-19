package Client.ViewController;

import Client.Control.Manager;
import Client.Control.RequestProcessor.RequestProcessor;
import Models.Account.Account;
import Models.Account.Customer;
import Models.Account.Principal;
import Models.Account.Seller;
import Models.Gson;
import Models.Shop.Off.Discount;
import Models.Shop.Request.*;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    protected Manager manager;
    protected static String accountUsername;
    protected static String accountType;
    protected Manager.Addresses backAddress;
    protected static Stage stage;
    protected static Stage popup = new Stage();

    public void init() {

    }

    public void initTable(ArrayList<Object> tableObjects) {

    }

    public static void setStage(Stage stage) {
        Controller.stage = stage;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public void setBack(Manager.Addresses backAddress) {
        this.backAddress = backAddress;
    }

    protected String sendRequest(String request) {
        return RequestProcessor.processRequest(request);
    }

    protected void openUserPanel() {
        if (accountUsername == null) {
            //new UserPanelManager(manager.getAccount(), address, manager, false);
            loadFxml(Manager.Addresses.USER_PANEL);
        }
        if (accountUsername != null) {
            //new DashboardManager(manager.getAccount(), address, manager);
            if (accountType.equals("P")) {
                loadFxml(Manager.Addresses.PRINCIPAL_MENU);
            } else if (accountType.equals("C")) {
                loadFxml(Manager.Addresses.CUSTOMER_MENU);
            } else if (accountType.equals("S")) {
                loadFxml(Manager.Addresses.SELLER_MENU);
            }
        }
    }

    protected ArrayList<Account> getAllAccounts() {
        ArrayList<Principal> principals = new ArrayList<>(Gson.INSTANCE.get().fromJson(
                sendRequest("GET_ALL_PRINCIPALS"), new TypeToken<ArrayList<Principal>>() {}.getType()));
        ArrayList<Customer> customers = new ArrayList<>(Gson.INSTANCE.get().fromJson(
                sendRequest("GET_ALL_CUSTOMERS"), new TypeToken<ArrayList<Customer>>() {}.getType()));
        ArrayList<Seller> sellers = new ArrayList<>(Gson.INSTANCE.get().fromJson(
                sendRequest("GET_ALL_SELLERS"), new TypeToken<ArrayList<Seller>>() {}.getType()));
        ArrayList<Account> accounts = new ArrayList<>();
        accounts.addAll(principals);
        accounts.addAll(customers);
        accounts.addAll(sellers);
        return accounts;
    }

    protected ArrayList<Discount> getAllDiscounts() {
        return new ArrayList<>(Gson.INSTANCE.get().fromJson(
                sendRequest("GET_ALL_DISCOUNTS"), new TypeToken<ArrayList<Discount>>() {}.getType()));
    }

    protected ArrayList<Request> getAllRequests() {
        String[] response = sendRequest("GET_ALL_REQUESTS").split("&&&");
        ArrayList<Request> requests = new ArrayList<>();
        requests.addAll(Gson.INSTANCE.get().fromJson(response[0], new TypeToken<ArrayList<AddOffRequest>>() {}.getType()));
        requests.addAll(Gson.INSTANCE.get().fromJson(response[1], new TypeToken<ArrayList<AddProductRequest>>() {}.getType()));
        requests.addAll(Gson.INSTANCE.get().fromJson(response[2], new TypeToken<ArrayList<AddSellerRequest>>() {}.getType()));
        requests.addAll(Gson.INSTANCE.get().fromJson(response[3], new TypeToken<ArrayList<DeleteProductRequest>>() {}.getType()));
        requests.addAll(Gson.INSTANCE.get().fromJson(response[4], new TypeToken<ArrayList<EditOffRequest>>() {}.getType()));
        requests.addAll(Gson.INSTANCE.get().fromJson(response[5], new TypeToken<ArrayList<EditProductRequest>>() {}.getType()));
        return requests;
    }

    protected void logout() {
        manager.logout();
    }

    public void back(ActionEvent actionEvent) {
        //manager.back();
        System.out.println("Back");
    }

    public void logout(ActionEvent actionEvent) {
        logout();
        System.out.println("LOGOUT");
    }

    public void error(String message) {
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setAlertType(Alert.AlertType.ERROR);
        a.setContentText(message);
        a.show();
    }

    public void success(String message) {
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setAlertType(Alert.AlertType.CONFIRMATION);
        a.setContentText(message);
        a.show();
    }

    public void loadFxml(Manager.Addresses address) {
        loadFxml(address, false);
    }

    public void loadFxml(Manager.Addresses address, boolean isPopup) {
        Stage workingStage;
        if (isPopup)
            workingStage = popup;
        else
            workingStage = stage;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(address.getAddress()));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            workingStage.setTitle("AP Project");
            workingStage.setScene(scene);
            workingStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
