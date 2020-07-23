package Client.ViewController;

import Client.Control.Manager;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends Controller implements Initializable {

    protected static boolean isPrincipalExists;
    protected static boolean isOffsMenu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (sendRequest("IS_PRINCIPAL_EXISTS").equals("true")) {
            isPrincipalExists = true;
        } else {
            isPrincipalExists = false;
            loadFxml(Manager.Addresses.REGISTER, true);
        }
    }

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