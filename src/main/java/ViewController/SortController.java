package ViewController;

import Control.Seller.EditProductsManager;
import ViewController.userPanel.Seller.EditProductsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

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
        if (!((EditProductsManager) manager).isEnteredSortFieldValid(sortField.getText())) {
            manager.error("wrong sort field");
        } else {
            ((EditProductsController) controller).initProducts(((EditProductsManager) manager).sort(sortField.getText(), isAscending.isSelected()));
        }
    }

    public void showAvailableSorts(ActionEvent actionEvent) {
        textField.setText("Available sorts:\n" + ((EditProductsManager) manager).showAvailableSorts());
    }

    public void currentSort(ActionEvent actionEvent) {
        textField.setText("Current sort:\n" + ((EditProductsManager) manager).currentSort());
    }

    public void disableSort(ActionEvent actionEvent) {
        ((EditProductsController) controller).initProducts(((EditProductsManager) manager).disableSort());
    }
}
