package View;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewCartController implements Initializable {

    public GridPane gridPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for(int i=0;i<10;i++) {
            try {
                GridPane item = FXMLLoader.load(getClass().getResource("../view/userPanel/Customer/itemInCart.fxml"));
                gridPane.add(item,0,i);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        gridPane.setPrefHeight(450);
    }
}
