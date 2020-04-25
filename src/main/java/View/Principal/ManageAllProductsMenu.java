package View.Principal;

import Control.Manager;
import Control.Principal.ManageAllProductsManager;
import Control.Principal.ManageUsersManager;
import Models.Shop.Product;
import View.ErrorProcessor;
import View.MainMenu;

import java.util.regex.Matcher;

public class ManageAllProductsMenu extends MainMenu {
    public ManageAllProductsMenu(Manager manager) {
        super(manager);
        manageAllProducts();
    }

    private void manageAllProducts() {
        while (true) {
            Matcher matcher;
            String input = scanner.nextLine();
            if ((matcher = getMatcher(input, "^remove (\\S+)$")).find()) {
                removeProductById(matcher.group(1));
            } else if(getMatcher(input, "^help$").find()) {
                help();
            } else if(getMatcher(input, "^back$").find()) {
                return;
            } else ErrorProcessor.invalidInput();
        }
    }

    private void removeProductById(String id) {
        if(Product.hasProductWithId(id)) {
            ((ManageAllProductsManager) manager).removeProductById(id);
        } else ErrorProcessor.invalidProductId();
    }

    private void help() {
        System.out.println("remove [productId]\n" +
                "help\n" +
                "back");
    }
}