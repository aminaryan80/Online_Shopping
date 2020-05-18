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

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        openFiles();
        connectObjects();
        Runtime.getRuntime().addShutdownHook(new Thread(Main::saveFiles));
        MainManager manager = new MainManager(null);
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
        try {
            Request.loadReferences();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Auction.loadReferences();
        //Discount.loadReferences();
        //Product.loadReferences();
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
}