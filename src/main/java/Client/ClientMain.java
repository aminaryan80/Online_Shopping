package Client;

import Client.Control.Identity;
import Client.Control.Manager;
import Client.ViewController.Controller;
import Models.Account.Account;
import Models.Shop.Category.Category;
import Models.Shop.Off.Auction;
import Models.Shop.Off.Discount;
import Models.Shop.Product.Comment;
import Models.Shop.Product.Product;
import Models.Shop.Product.Rate;
import Models.Shop.Request.Request;
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

    private static void openFiles() {
        try {
            Comment.open();
            Rate.open();
            Account.open();
            Category.open();
            Request.open();
            Auction.open();
            Discount.open();
            Product.open();
            Identity.open();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private static void saveFiles() {
        try {
            Rate.save();
            Comment.save();
            Account.save();
            Category.save();
            Request.save();
            Auction.save();
            Discount.save();
            Product.save();
            Identity.save();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    @Override
    public void start(Stage stage) {
        openFiles();
        Runtime.getRuntime().addShutdownHook(new Thread(ClientMain::saveFiles));
        initialize(stage);
    }

    private void initialize(Stage stage) {
        /*Manager.setStage(stage);
        new MainManager(null);*/
        Controller.setStage(stage);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(Manager.Addresses.MAIN_MENU.getAddress()));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setTitle("AP Project");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}