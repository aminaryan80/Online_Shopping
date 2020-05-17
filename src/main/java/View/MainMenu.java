package View;

import Control.Manager;
import Control.Products.ProductsManager;

public class MainMenu extends Menu {

    public MainMenu(Manager manager) {
        super(manager);
        mainMenu();
    }

    private void mainMenu() {
        while (true) {
            String input = scanner.nextLine();
            if (getMatcher(input, "^user panel$").find()) {
                openUserPanel();
            } else if (getMatcher(input, "^products$").find()) {
                openProductsMenu();
            } else if (getMatcher(input, "^offs$").find()) {
                //TODO
            } else if (getMatcher(input, "^help$").find()) {
                help();
            } else if (getMatcher(input, "^exit$").find()) {
                return;
            } else ErrorProcessor.invalidInput();
        }
    }

    private void openProductsMenu() {
        new ProductsManager(manager.getAccount());
    }

    private void help() {
        System.out.println("user panel\n" +
                "products\n" +
                "offs\n" +
                "help\n" +
                "exit");
    }
}
