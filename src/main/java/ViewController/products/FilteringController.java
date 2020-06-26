package ViewController.products;

import Control.Products.ProductsManager;
import Models.Shop.Category.Filter;
import ViewController.Controller;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class FilteringController extends Controller {

    @FXML
    private ChoiceBox filterTypes;
    @FXML
    private TextField filterValue;
    @FXML
    private TableView<Filter> currentFilters;
    @FXML
    private TableColumn<Filter, String> filterTypeColumn;
    @FXML
    private TableColumn<Filter, String> filterValueColumn;
    private Controller controller;

    public void init(Controller controller) {
        this.controller = controller;
        filterTypes.setItems(FXCollections.observableArrayList(((ProductsManager) manager).getFilterTypes()));
    }

    public void disableFilter(ActionEvent actionEvent) {

    }

    public void filter(ActionEvent actionEvent) {
        ((ProductsController) controller).initTable(((ProductsManager) manager).applyFilter((String) filterTypes.getSelectionModel().getSelectedItem(), filterValue.getText()));

    }
}
