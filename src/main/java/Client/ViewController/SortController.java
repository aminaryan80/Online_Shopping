package Client.ViewController;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;

public class SortController extends Controller {

    public CheckBox isAscending;
    public ChoiceBox sortFields;
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
