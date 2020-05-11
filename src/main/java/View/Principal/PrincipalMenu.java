package View.Principal;

import Control.Manager;
import Control.Principal.ManageAllProductsManager;
import Control.Principal.ManageCategories.ManageCategoriesManager;
import Control.Principal.ManageRequestsManager;
import Control.Principal.ManageUsersManager;
import Control.Principal.ViewDiscountCodes.ViewDiscountCodesManager;
import View.ErrorProcessor;
import View.Menu;

import java.util.regex.Matcher;

public class PrincipalMenu extends Menu {

    public PrincipalMenu(Manager manager) {
        super(manager);
        principalMenu();
    }

    public void principalMenu() {
        while (true) {
            String input = scanner.nextLine();
            if (getMatcher(input, "^view personal info$").find()) {
                // TODO
            } else if (getMatcher(input, "^manage users$").find()) {
                manageUsers();
            } else if (getMatcher(input, "^manage all products$").find()) {
                manageAllProducts();
            } else if (getMatcher(input, "^create discount code$").find()) {
                createDiscountCode(); // TODO
            } else if (getMatcher(input, "^view discount codes$").find()) {
                viewDiscountCodes();
            } else if (getMatcher(input, "^manage requests$").find()) {
                manageRequests();
            } else if (getMatcher(input, "^manage categories$").find()) {
                manageCategories();
            } else if (getMatcher(input, "^help$").find()) {
                help();
            } else if (getMatcher(input, "^back$").find()) {
                return;
            } else ErrorProcessor.invalidInput();
        }
    }

    private void manageUsers() {
        new ManageUsersManager(manager.getAccount());
    }

    private void manageAllProducts() {
        new ManageAllProductsManager(manager.getAccount());
    }

    private void createDiscountCode() {
        // TODO
    }

    private void viewDiscountCodes() {
        new ViewDiscountCodesManager(manager.getAccount());
    }

    private void manageRequests() {
        new ManageRequestsManager(manager.getAccount());
    }

    private void manageCategories() {
        new ManageCategoriesManager(manager.getAccount());
    }

    private void help() {
        System.out.println("view personal info\n" +
                "manage users\n" +
                "manage all products\n" +
                "create discount code\n" +
                "view discount codes\n" +
                "manage requests\n" +
                "manage categories\n" +
                "help\n" +
                "exit");
    }
}
