package Client.ViewController;

import Models.Account.Account;
import Models.Gson;
import Models.Shop.Log.BuyingLog;
import Models.Shop.Off.Auction;
import Models.Shop.Off.Discount;
import Models.Shop.Product.Product;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;

import java.util.ArrayList;
import java.util.Arrays;

public class SortController extends Controller {

    public CheckBox isAscending;
    public ChoiceBox sortFields;
    private Controller controller;

    public void init(Controller controller) {
        this.controller = controller;
        sortFields.setItems(FXCollections.observableArrayList(controller.getSortFields()));
    }

    public void sort(ActionEvent actionEvent) {
        controller.initTable(controller.sort((String) sortFields.getSelectionModel().getSelectedItem(), isAscending.isSelected()));
    }

    public void disableSort(ActionEvent actionEvent) {
        controller.initTable(controller.disableSort());
    }
}
