package ViewController;

import Control.Seller.EditProductsManager;
import Models.Shop.Product.Product;
import ViewController.userPanel.Seller.EditProductsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class SortController extends Controller {

    @FXML
    private CheckBox isAscending;
    @FXML
    private TextField sortField;
    @FXML
    private TextField textField;
    private Controller controller;

    public void init(Controller controller) {
        this.controller = controller;
    }

    public void sort(ActionEvent actionEvent) {
        if (!manager.isEnteredSortFieldValid(sortField.getText())) {
            manager.error("wrong sort field");
        } else {
            controller.initTable(manager.sort(sortField.getText(), isAscending.isSelected()));
        }
    }

    public void showAvailableSorts(ActionEvent actionEvent) {
        textField.setText("Available sorts:\n" + manager.showAvailableSorts());
    }

    public void currentSort(ActionEvent actionEvent) {
        textField.setText("Current sort:\n" + manager.currentSort());
    }

    public void disableSort(ActionEvent actionEvent) {
        controller.initTable(manager.disableSort());
    }
}
