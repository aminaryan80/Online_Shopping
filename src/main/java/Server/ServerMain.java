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

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.net.Socket;

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
            Socket socket = new Socket("127.0.0.1", 5555);
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            dataOutputStream.writeUTF("exit_all");
            dataOutputStream.flush();
            socket.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public static void main(String[] args) {
        openFiles();
        Runtime.getRuntime().addShutdownHook(new Thread(ServerMain::saveFiles));
        new RequestProcessor();
    }
}
