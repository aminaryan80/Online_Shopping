package View;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewCartController implements Initializable {

    public VBox vBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vBox.setSpacing(350);
        for (int i = 0; i < 10; i++) {
            try {
                GridPane item = FXMLLoader.load(getClass().getResource("../view/userPanel/Customer/itemInCart.fxml"));
                vBox.getChildren().add(item);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
