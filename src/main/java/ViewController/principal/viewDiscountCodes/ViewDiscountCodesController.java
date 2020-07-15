package ViewController.principal.viewDiscountCodes;

import Control.Principal.ViewDiscountCodes.ViewDiscountCodesManager;
import Models.Shop.Off.Discount;
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

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ViewDiscountCodesController extends Controller implements Initializable {


    @FXML
    private TextField discountIdField;
    @FXML
    private Label viewDiscountLabel;
    @FXML
    private TableView<Discount> discountsTable;
    @FXML
    private TableColumn<Discount, String> discountIdCol;
    @FXML
    private TableColumn<Discount, Double> discountPercentCol;
    @FXML
    private TableColumn<Discount, Integer> discountUseCountLimit;
    @FXML
    private TableColumn<Discount, LocalDate> beginningDateCol;
    @FXML
    private TableColumn<Discount, LocalDate> endingDateCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<Object> objects = new ArrayList<>(Discount.getAllDiscounts());
        initTable(objects);
    }

    public void initTable(ArrayList<Object> tableObjects) {
        ArrayList<Discount> tableDiscounts = new ArrayList<>();
        for (Object tableProduct : tableObjects) {
            tableDiscounts.add((Discount) tableProduct);
        }
        discountsTable.setItems(FXCollections.observableArrayList(tableDiscounts));
        discountIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        discountPercentCol.setCellValueFactory(new PropertyValueFactory<>("discountPercent"));
        discountUseCountLimit.setCellValueFactory(new PropertyValueFactory<>("discountUseCount"));
        beginningDateCol.setCellValueFactory(new PropertyValueFactory<>("beginningDate"));
        endingDateCol.setCellValueFactory(new PropertyValueFactory<>("endingDate"));
    }

    public void viewDiscount(ActionEvent actionEvent) {
        if (((ViewDiscountCodesManager) manager).viewDiscountCode(discountIdField.getText()) != null) {
            viewDiscountLabel.setText(((ViewDiscountCodesManager) manager).viewDiscountCode(discountIdField.getText()));
        }

    }

    public void editDiscount(ActionEvent actionEvent) {
        ((ViewDiscountCodesManager) manager).editDiscountCode(discountIdField.getText());
    }

    public void deleteDiscount(ActionEvent actionEvent) {
        try {
            ((ViewDiscountCodesManager) manager).deleteDiscountCode(discountIdField.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sort(ActionEvent actionEvent) {
        manager.openSort(this, manager);
    }
}
