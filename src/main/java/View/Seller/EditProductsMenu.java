package View.Seller;

import Control.Manager;
import Control.Seller.EditProductsManager;
import Models.Account.Seller;
import Models.Shop.EditProductRequest;
import View.ErrorProcessor;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class EditProductsMenu extends SellerMenu{

    public EditProductsMenu(Manager manager) {
        super(manager);
        productsMenu();
    }

    private void productsMenu() {
        Matcher matcher;
        while (true) {
            if ((matcher = getMatcher(command, "^view (\\d+)$")).find()) {
                viewProduct(matcher.group(1));
            } else if ((matcher = getMatcher(command, "^view buyers (\\d+)$")).find()) {
                viewProductBuyers(matcher.group(1));
            } else if ((matcher = getMatcher(command, "^edit (\\d+)$")).find()) {
                editProduct(matcher.group(1));
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
        System.out.println("enter the field you want to change");
        String field = scanner.nextLine();
        if (!((EditProductsManager) manager).isEnteredProductFieldValid(field)) {
            ErrorProcessor.invalidEditField();
            return;
        }
        System.out.println("enter the new value");
        String newValue = scanner.nextLine();
        new EditProductRequest("random id", (Seller) manager.getAccount(), manager, id, field, newValue);
    }

    private void help() {
        System.out.println("view [product id]%n" +
                "view buyers [product id]%n" +
                "edit [product id]%n" +
                "back%n" +
                "help");
    }
}
