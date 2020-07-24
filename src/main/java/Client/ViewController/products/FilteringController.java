package Client.ViewController.products;

import Client.ViewController.Controller;
import Models.Shop.Category.Filter;
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
        filterTypesNames = ((ProductsController) controller).getFilterTypes();
        filterTypes.setItems(FXCollections.observableArrayList(filterTypesNames));
    }

    public void disableFilter(ActionEvent actionEvent) {
        controller.initTable(((ProductsController) controller).disableFilter(currentFilters.getSelectionModel().getSelectedItem().getField()));
        initCurrentFiltersTable();
        filterTypesNames.add((String) filterTypes.getSelectionModel().getSelectedItem());
        filterTypes.setItems(FXCollections.observableArrayList(filterTypesNames));
    }

    public void filter(ActionEvent actionEvent) {
        controller.initTable(((ProductsController) controller).applyFilter((String) filterTypes.getSelectionModel().getSelectedItem(), filterValue.getText()));
        initCurrentFiltersTable();
        filterTypesNames.remove(filterTypes.getSelectionModel().getSelectedItem());
        filterTypes.setItems(FXCollections.observableArrayList(filterTypesNames));
    }

    public void initCurrentFiltersTable() {
        currentFilters.setItems(FXCollections.observableArrayList(((ProductsController) controller).currentFilters()));
        filterTypeColumn.setCellValueFactory(new PropertyValueFactory<>("field"));
        filterValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
    }
}
