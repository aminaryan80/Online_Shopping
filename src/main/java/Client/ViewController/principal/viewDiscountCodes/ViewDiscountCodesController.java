package Client.ViewController.principal.viewDiscountCodes;

import Client.Control.Manager;
import Client.ViewController.Controller;
import Models.Shop.Off.Discount;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ViewDiscountCodesController extends Controller implements Initializable {

    protected static String discountId;
    public TextField discountIdField;
    public Label viewDiscountLabel;
    public TableView<Discount> discountsTable;
    public TableColumn<Discount, String> discountIdCol;
    public TableColumn<Discount, Double> discountPercentCol;
    public TableColumn<Discount, Integer> discountUseCountLimit;
    public TableColumn<Discount, LocalDate> beginningDateCol;
    public TableColumn<Discount, LocalDate> endingDateCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initialize();
    }

    private void initialize() {
        discountsTable.setItems(FXCollections.observableArrayList(getAllDiscounts()));
        discountIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        discountPercentCol.setCellValueFactory(new PropertyValueFactory<>("discountPercent"));
        discountUseCountLimit.setCellValueFactory(new PropertyValueFactory<>("discountUseCount"));
        beginningDateCol.setCellValueFactory(new PropertyValueFactory<>("beginningDate"));
        endingDateCol.setCellValueFactory(new PropertyValueFactory<>("endingDate"));
    }

    public void viewDiscount(ActionEvent actionEvent) {
        viewDiscountLabel.setText(sendRequest("GET_DISCOUNT_DETAILS " + discountIdField.getText()));
    }

    public void editDiscount(ActionEvent actionEvent) {
        discountId = discountIdField.getText();
        loadFxml(Manager.Addresses.EDIT_DISCOUNTS, true);
    }

    public void deleteDiscount(ActionEvent actionEvent) {
        discountId = discountIdField.getText();
        String response = sendRequest("DELETE_DISCOUNT " + discountId);
        if (response.equals("0")) {
            success("Discount deleted successfully.");
        } else error("Something went wrong.");
        initialize();
    }

    public void sort(ActionEvent actionEvent) {
        manager.openSort(this, manager);
    }

    public void back() {
        loadFxml(Manager.Addresses.PRINCIPAL_MENU);
    }
}
