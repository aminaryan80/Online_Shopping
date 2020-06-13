package View;

import Control.Products.ProductsManager;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

public class MainController extends Controller {

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
        openUserPanel(false);
    }

    public void openOffsMenu(ActionEvent actionEvent) {
        System.out.println("Offs is open now.");
    }

    public void openProductsMenu(ActionEvent actionEvent) {
        new ProductsManager(manager.getAccount());
    }

}
