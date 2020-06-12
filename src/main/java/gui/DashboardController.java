package gui;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    public JFXDrawer navDrawer;
    @FXML
    private JFXHamburger navButton;
    private HamburgerBasicCloseTransition basicTransition;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            basicTransition = new HamburgerBasicCloseTransition(navButton);
            basicTransition.setRate(-1);
            VBox vBox = FXMLLoader.load(getClass().getResource("../view/userPanel/dashboard/principal_nav_menu.fxml"));
            navDrawer.setSidePane(vBox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void navButtonClicked(MouseEvent mouseEvent) {
        if (navDrawer.isShowing() || navDrawer.isHiding()) {
            return;
        }
        if (navDrawer.isShown()) {
            navDrawer.close();
        } else {
            navDrawer.open();
        }
        basicTransition.setRate(basicTransition.getRate() * -1);
        basicTransition.play();
    }
}
