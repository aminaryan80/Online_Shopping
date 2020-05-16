import Control.MainManager;
import Models.Account.Account;
import Models.Shop.*;

public class Main {
    public static void main(String[] args) {
        openFiles();
        connectObjects();
        MainManager manager = new MainManager(null);
        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {
                saveFiles();
            }

        });
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
            e.getStackTrace();
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