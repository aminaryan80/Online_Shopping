package Client.ViewController.principal;

import Server.Control.Manager;
import Server.Control.Principal.ManageRequestsManager;
import Client.ViewController.Controller;
import Models.Shop.Request.Request;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageRequestsController extends Controller implements Initializable {

    public TableView<Request> requestsTable;
    public TableColumn<Request, String> requestIdCol;
    public TableColumn<Request, String> requestTypeCol;
    public TableColumn<Request, String> requestSenderCol;
    public TextField requestIdField;
    public Label viewRequestLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        requestsTable.setItems(FXCollections.observableArrayList(getAllRequests()));
        requestIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        requestTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        requestSenderCol.setCellValueFactory(new PropertyValueFactory<>("sellerName"));
    }

    public void viewRequest(ActionEvent actionEvent) {
        String requestId = requestIdField.getText();
        viewRequestLabel.setText(sendRequest("GET_REQUEST_DETAILS " + requestId));
    }

    public void acceptRequest(ActionEvent actionEvent) {
        String requestId = requestIdField.getText();
        String response = sendRequest("ACCEPT_REQUEST " + requestId);
        if (response.equals("0")) {
            success("Request accepted successfully.");
        } else error("Something went wrong.");
    }

    public void deleteRequest(ActionEvent actionEvent) {
        String requestId = requestIdField.getText();
        ((ManageRequestsManager) manager).declineRequest(requestId);
        String response = sendRequest("DECLINE_REQUEST " + requestId);
        if (response.equals("0")) {
            success("Request declined successfully.");
        } else error("Something went wrong.");
    }

    public void sort(ActionEvent actionEvent) {
        manager.openSort(this, manager);
    }

    public void back() {
        loadFxml(Addresses.PRINCIPAL_MENU);
    }
}