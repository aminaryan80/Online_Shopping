package ViewController.principal;

import Control.Principal.ManageRequestsManager;
import Models.Shop.Request.Request;
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
import java.util.ResourceBundle;

public class ManageRequestsController extends Controller implements Initializable {

    @FXML
    private TableView<Request> requestsTable;
    @FXML
    private TableColumn<Request, String> requestIdCol;
    @FXML
    private TableColumn<Request, String> requestTypeCol;
    @FXML
    private TableColumn<Request, String> requestSenderCol;
    @FXML
    private TextField requestIdField;
    @FXML
    private Label viewRequestLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        requestsTable.setItems(FXCollections.observableArrayList(Request.getAllRequests()));
        requestIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        requestTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        requestSenderCol.setCellValueFactory(new PropertyValueFactory<>("sellerName"));
    }

    public void viewRequest(ActionEvent actionEvent) {
        String requestId = requestIdField.getText();
        viewRequestLabel.setText(((ManageRequestsManager) manager).showRequestDetails(requestId));
    }

    public void acceptRequest(ActionEvent actionEvent) {
        ((ManageRequestsManager) manager).acceptRequest(requestIdField.getText());
    }

    public void deleteRequest(ActionEvent actionEvent) {
        ((ManageRequestsManager) manager).declineRequest(requestIdField.getText());
    }
}
