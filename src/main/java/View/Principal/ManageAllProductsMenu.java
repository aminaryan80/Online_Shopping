package View.Principal;

import Control.Manager;
import Control.Principal.ManageAllProductsManager;
import Models.Shop.Product.Product;
import View.ErrorProcessor;
import View.Menu;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class ManageAllProductsMenu extends Menu {
    public ManageAllProductsMenu(Manager manager) {
        super(manager);
        manageAllProducts();
    }

    private void manageAllProducts() {
        while (true) {
            Matcher matcher;
            String command = scanner.nextLine();
            if ((matcher = getMatcher(command, "^remove (\\S+)$")).find()) {
                removeProductById(matcher.group(1));
            } else if (command.equals("show available sorts")) {
                showAvailableSorts();
            } else if ((matcher = getMatcher(command, "sort (\\S+)")).find()) {
                sort(matcher.group(1));
            } else if (command.equals("current sort")) {
                currentSort();
            } else if (command.equals("disable sort")) {
                disableSort();
            } else if (getMatcher(command, "^help$").find()) {
                help();
            } else if (getMatcher(command, "^back$").find()) {
                return;
            } else ErrorProcessor.invalidInput();
        }
    }

    private void showAvailableSorts() {
        System.out.println(((ManageAllProductsManager) manager).showAvailableSorts());
    }

    private void sort(String sort) {
        if (((ManageAllProductsManager) manager).isEnteredSortFieldValid(sort)) {
            System.out.println("do you want it to be ascending? (answer with true or false)");
            String isAscending = scanner.nextLine();
            ArrayList<Product> sortedProducts = ((ManageAllProductsManager) manager).sort(sort, Boolean.parseBoolean(isAscending));
            for (Product sortedProduct : sortedProducts) {
                System.out.println(sortedProduct.viewProductInShort());
            }
        } else {
            ErrorProcessor.invalidInput();
        }
    }

    private void currentSort() {
        System.out.println(((ManageAllProductsManager) manager).currentSort());
    }

    private void disableSort() {
        ArrayList<String> products = ((ManageAllProductsManager) manager).disableSort();
        for (String product : products) {
            System.out.println(product);
        }
    }

    private void removeProductById(String id) {
        if(Product.hasProductWithId(id)) {
            ((ManageAllProductsManager) manager).removeProductById(id);
        } else ErrorProcessor.invalidProductId();
    }

    private void help() {
        System.out.println("remove [productId]\n" +
                "show available sorts\n" +
                "sort [an available sort]\n" +
                "current sort\n" +
                "disable sort\n" +
                "help\n" +
                "back");
    }
}