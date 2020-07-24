package Client;

import Client.ViewController.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientMain extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        initialize(stage);
    }

    private static void closeClient() {
        Controller.closeClient();
    }

    private void initialize(Stage stage) {
        Controller.setStage(stage);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(Controller.Addresses.MAIN_MENU.getAddress()));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setTitle("AP Project");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Runtime.getRuntime().addShutdownHook(new Thread(ClientMain::closeClient));
    }
}