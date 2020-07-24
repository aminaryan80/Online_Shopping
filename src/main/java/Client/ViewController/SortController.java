package Client.ViewController;

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
        ArrayList<Object> objects = new ArrayList<>();
        for (String thing : things) {
            objects.add(thing);
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
