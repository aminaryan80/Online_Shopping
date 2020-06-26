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
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

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
    private ArrayList<String> filterTypesNames;

    public void init(Controller controller) {
        this.controller = controller;
        filterTypesNames = ((ProductsManager) manager).getFilterTypes();
        filterTypes.setItems(FXCollections.observableArrayList(filterTypesNames));
    }

    public void disableFilter(ActionEvent actionEvent) {
        controller.initTable(((ProductsManager) manager).disableFilter((String) filterTypes.getSelectionModel().getSelectedItem()));
        initCurrentFiltersTable();
        filterTypesNames.add((String) filterTypes.getSelectionModel().getSelectedItem());
        filterTypes.setItems(FXCollections.observableArrayList(filterTypesNames));
    }

    public void filter(ActionEvent actionEvent) {
        controller.initTable(((ProductsManager) manager).applyFilter((String) filterTypes.getSelectionModel().getSelectedItem(), filterValue.getText()));
        initCurrentFiltersTable();
        filterTypesNames.remove(filterTypes.getSelectionModel().getSelectedItem());
        filterTypes.setItems(FXCollections.observableArrayList(filterTypesNames));
    }

    public void initCurrentFiltersTable() {
        currentFilters.setItems(FXCollections.observableArrayList(((ProductsManager) manager).currentFilters()));
        filterTypeColumn.setCellValueFactory(new PropertyValueFactory<>("field"));
        filterValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
    }
}
