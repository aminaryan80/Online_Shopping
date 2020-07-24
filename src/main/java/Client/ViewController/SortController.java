package Client.ViewController;

import Models.Account.Account;
import Models.Gson;
import Models.Shop.Log.BuyingLog;
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
    private String type;

    public void init(Controller controller, String type) {
        this.controller = controller;
        this.type = type;
        String fields = sendRequest("GET_SORT_FIELDS " + type);
        sortFields.setItems(FXCollections.observableArrayList(Arrays.asList(fields.split("  "))));
    }

    public void sort(ActionEvent actionEvent) {
        String sorted = sendRequest("SORT " + type + " " + sortFields.getSelectionModel().getSelectedItem() + " " + isAscending.isSelected());
        String[] things = sorted.split("  ");
        Class myClass = null;
        if (type.equals("customerViewOrders")) {
            myClass = BuyingLog.class;
        } else if (type.equals("viewCart") || type.equals("principalManageProducts") || type.equals("products") || type.equals("sellerManageProducts")) {
            myClass = Product.class;
        } else if (type.equals("principalManageUsers")) {
            myClass = Account.class;
        } else if (type.equals("principalViewDiscounts")) {
            myClass = Discount.class;
        } else if (type.equals("sellerViewOffs")) {

        }
        ArrayList<Object> objects = new ArrayList<>();
        for (String thing : things) {
            System.out.println(thing);
            objects.add(Gson.INSTANCE.get().fromJson(thing, myClass));
        }
        controller.initTable(objects);
    }

    public void disableSort(ActionEvent actionEvent) {
        String fields = sendRequest("GET_SORT_FIELDS " + type);
        String[] sortFields = fields.split("  ");
        ArrayList<Object> objects = new ArrayList<>();
        for (String sortField : sortFields) {
            objects.add(sortField);
        }
        controller.initTable(objects);
    }
}
