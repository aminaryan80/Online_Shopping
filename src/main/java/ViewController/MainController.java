package ViewController;

import Control.MainManager;
import Control.Manager;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

public class MainController extends Controller {

    public Label test;

    private void help() {
        System.out.println("user panel\n" +
                "products\n" +
                "offs\n" +
                "help\n" +
                "exit");
    }

    public void aboutUs(ActionEvent actionEvent) {
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setAlertType(Alert.AlertType.CONFIRMATION);
        a.show();
    }

    public void openUserPanel(ActionEvent actionEvent) {
        openUserPanel(false, Manager.Addresses.MAIN_MENU);
    }

    public void openOffsMenu(ActionEvent actionEvent) {
        System.out.println("Offs is open now.");
    }

    public void openProductsMenu(ActionEvent actionEvent) {
        ((MainManager) manager).openProductsMenu();
    }

}
