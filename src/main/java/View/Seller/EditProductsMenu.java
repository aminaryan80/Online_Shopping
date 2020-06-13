package View.Seller;

import Control.Manager;
import Control.Seller.EditProductsManager;
import Models.Account.Seller;
import Models.Shop.Product.Product;
import Models.Shop.Request.EditProductRequest;
import View.ErrorProcessor;
import View.Menu;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class EditProductsMenu extends Menu {

    public EditProductsMenu(Manager manager) {
        super(manager);
        productsMenu();
    }

    private void productsMenu() {
        Matcher matcher;
        while (true) {
            String command = scanner.nextLine();
            if ((matcher = getMatcher(command, "^view (\\S+)$")).find()) {
                viewProduct(matcher.group(1));
            } else if ((matcher = getMatcher(command, "^view buyers (\\S+)$")).find()) {
                viewProductBuyers(matcher.group(1));
            } else if ((matcher = getMatcher(command, "^edit (\\S+)$")).find()) {
                editProduct(matcher.group(1));
            } else if (command.equals("show available sorts")) {
                showAvailableSorts();
            } else if ((matcher = getMatcher(command, "sort (\\S+)")).find()) {
                sort(matcher.group(1));
            } else if (command.equals("current sort")) {
                currentSort();
            } else if (command.equals("disable sort")) {
                disableSort();
            } else if ((matcher = getMatcher(command, "^back$")).find()) {
                return;
            } else if ((matcher = getMatcher(command, "^help$")).find()) {
                help();
            } else {
                ErrorProcessor.invalidInput();
            }
        }
    }

    private void viewProduct(String id) {
        if (!((EditProductsManager) manager).hasProductWithId(id)) {
            ErrorProcessor.invalidProductId();
            return;
        }
        System.out.println(((EditProductsManager) manager).viewProductDetails(id));
    }

    private void viewProductBuyers(String id) {
        ArrayList<String> allBuyers = ((EditProductsManager) manager).viewProductBuyers(id);
        for (String buyer : allBuyers) {
            System.out.println(buyer);
        }
    }

    private void editProduct(String id) {
        if (!((EditProductsManager) manager).hasProductWithId(id)) {
            ErrorProcessor.invalidProductId();
            return;
        }
        System.out.println("enter the field you want to change");
        String field = scanner.nextLine();
        if (!((EditProductsManager) manager).isEnteredProductEditFieldValid(field)) {
            ErrorProcessor.invalidEditField();
            return;
        }
        String feature = "";
        if (field.equals("features")) {
            System.out.println("enter which field you want to change");
            feature = scanner.nextLine();
        }
        if (field.equals("features") && !((EditProductsManager) manager).isFeatureFieldValid(feature, id)) {
            ErrorProcessor.invalidInput();
            return;
        }
        System.out.println("enter the new value");
        String newValue = scanner.nextLine();
        Product product;
        if (field.equals("features")) {
            product = ((EditProductsManager) manager).editProduct(id, feature, newValue);
        } else {
            product = ((EditProductsManager) manager).editProduct(id, field, newValue);
        }
        new EditProductRequest((Seller) manager.getAccount(), product);
    }

    private void showAvailableSorts() {
        System.out.println(((EditProductsManager) manager).showAvailableSorts());
    }

    private void sort(String sort) {
        if (((EditProductsManager) manager).isEnteredSortFieldValid(sort)) {
            System.out.println("do you want it to be ascending (answer with true or false)");
            String isAscending = scanner.nextLine();
            ArrayList<String> sortedProducts = ((EditProductsManager) manager).sort(sort, Boolean.parseBoolean(isAscending));
            for (String sortedProduct : sortedProducts) {
                System.out.println(sortedProduct);
            }
        } else {
            ErrorProcessor.invalidInput();
        }
    }

    private void currentSort() {
        System.out.println(((EditProductsManager) manager).currentSort());
    }

    private void disableSort() {
        ArrayList<String> products = ((EditProductsManager) manager).disableSort();
        for (String product : products) {
            System.out.println(product);
        }
    }

    private void help() {
        System.out.println("view [product id]\n" +
                "view buyers [product id]\n" +
                "edit [product id]\n" +
                "show available sorts\n" +
                "sort [an available sort]\n" +
                "current sort\n" +
                "disable sort\n" +
                "back\n" +
                "help");
    }
}
