package ViewController;

import Control.Seller.EditProductsManager;
import Models.Shop.Product.Product;
import ViewController.userPanel.Seller.EditProductsController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class SortController extends Controller {

    @FXML
    private CheckBox isAscending;
    @FXML
    private ChoiceBox sortFields;
    private Controller controller;

    public void init(Controller controller) {
        this.controller = controller;
        sortFields.setItems(FXCollections.observableArrayList(manager.getSortFields()));
    }

    public void sort(ActionEvent actionEvent) {
        controller.initTable(manager.sort((String) sortFields.getSelectionModel().getSelectedItem(), isAscending.isSelected()));
    }

    public void disableSort(ActionEvent actionEvent) {
        controller.initTable(manager.disableSort());
    }
}
