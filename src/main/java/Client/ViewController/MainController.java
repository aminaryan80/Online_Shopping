package Client.ViewController;

import Client.Control.Manager;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

public class MainController extends Controller {

    protected static boolean isOffsMenu;

    public void aboutUs(ActionEvent actionEvent) {
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setAlertType(Alert.AlertType.CONFIRMATION);
        a.show();
    }

    public void openUserPanel(ActionEvent actionEvent) {
        openUserPanel();
    }

    public void openOffsMenu(ActionEvent actionEvent) {
        isOffsMenu = true;
        loadFxml(Manager.Addresses.PRODUCTS_MENU);
    }

    public void openProductsMenu(ActionEvent actionEvent) {
        isOffsMenu = false;
        loadFxml(Manager.Addresses.PRODUCTS_MENU);
    }

    public void exit(ActionEvent actionEvent) {
        //Manager.exit();
        System.exit(0);
    }
}