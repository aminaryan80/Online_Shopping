import Control.MainManager;
import Models.Account.Account;
import Models.Shop.Category.Category;
import Models.Shop.Off.Auction;
import Models.Shop.Off.Discount;
import Models.Shop.Product.Product;
import Models.Shop.Request.Request;

public class Main {
    public static void main(String[] args) {
        openFiles();
        connectObjects();
        Runtime.getRuntime().addShutdownHook(new Thread(Main::saveFiles));
        MainManager manager = new MainManager(null);
    }

    private static void openFiles() {
        try {
            Account.open();
            Category.open();
            Request.open();
            Auction.open();
            Discount.open();
            Product.open();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void connectObjects() {
        Account.loadReferences();
        Category.loadReferences();
        Request.loadReferences();
        Auction.loadReferences();
        Discount.loadReferences();
        Product.loadReferences();
    }

    private static void saveFiles() {
        try {
            Account.save();
            Category.save();
            Request.save();
            Auction.save();
            Discount.save();
            Product.save();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}