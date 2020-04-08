import Models.Account.Account;
import Models.Account.Customer;

import java.util.Scanner;

public class CommandProcessor {
    private Manager manager;
    private String command;
    private Scanner scanner = new Scanner(System.in);
    public void mainMenu() {

    }

    public void productsMenu() {

    }

    public void accountMenu() {
        String password = "Jesus";
        String newPassword = "Christ";
        manager.changePassword(password, newPassword);
    }

    public void productPage() {

    }

    public void auctionMenu() {

    }

    private void userMenu() {

    }

    private void register() {

    }

    private void showCartProducts() {

    }
}
