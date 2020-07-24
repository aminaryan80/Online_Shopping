package Server;

import Models.Account.Account;
import Models.Shop.Category.Category;
import Models.Shop.Off.Auction;
import Models.Shop.Off.Discount;
import Models.Shop.Product.Comment;
import Models.Shop.Product.Product;
import Models.Shop.Product.Rate;
import Models.Shop.Request.Request;
import Server.Control.Identity;
import Server.Control.RequestProcessor.RequestProcessor;

public class ServerMain {

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


    public static void main(String[] args) {
        openFiles();
        Runtime.getRuntime().addShutdownHook(new Thread(ServerMain::saveFiles));
        RequestProcessor requestProcessor = new RequestProcessor();
    }
}
