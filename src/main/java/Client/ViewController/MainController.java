package Client.ViewController;

import Client.Control.Manager;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

public class MainController extends Controller {

    public Label test;

    public void aboutUs(ActionEvent actionEvent) {
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setAlertType(Alert.AlertType.CONFIRMATION);
        a.show();
    }

    public void openUserPanel(ActionEvent actionEvent) {
        openUserPanel();
    }

    public void openOffsMenu(ActionEvent actionEvent) {
        manager.openOffsMenu();
    }

    public void openProductsMenu(ActionEvent actionEvent) {
        manager.openProductsMenu();
    }

    public void exit(ActionEvent actionEvent) {
        //Manager.exit();
        System.exit(0);
    }
}
