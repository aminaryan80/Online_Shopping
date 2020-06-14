import Control.Identity;
import Control.MainManager;
import Models.Account.Account;
import Models.Shop.Category.Category;
import Models.Shop.Off.Auction;
import Models.Shop.Off.Discount;
import Models.Shop.Product.Comment;
import Models.Shop.Product.Product;
import Models.Shop.Product.Rate;
import Models.Shop.Request.Request;
import View.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
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

    private static void connectObjects() {
        //Account.loadReferences();
        //Category.loadReferences();
//        try {
//            Request.loadReferences();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //Auction.loadReferences();
        //Discount.loadReferences();
        //Product.loadReferences();
    }

    /*public static void main(String[] args) {
        openFiles();
        connectObjects();
        Runtime.getRuntime().addShutdownHook(new Thread(Main::saveFiles));
        MainManager manager = new MainManager(null);
    }*/

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
    public void start(Stage stage) throws Exception {
        openFiles();
        connectObjects();
        Runtime.getRuntime().addShutdownHook(new Thread(Main::saveFiles));
        //initialize(stage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/userPanel/Customer/ViewCart.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("AP Project");
        stage.setScene(scene);
        stage.show();
    }

    private void initialize(Stage stage) throws IOException {
        MainManager manager = new MainManager(null);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/main_menu.fxml"));
        Parent root = loader.load();
        MainController mainController = loader.getController();
        mainController.setManager(manager);
        Scene scene = new Scene(root);
        stage.setTitle("AP Project");
        stage.setScene(scene);
        stage.show();
    }
}