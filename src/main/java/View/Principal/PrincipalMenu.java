package View.Principal;

import Control.Manager;
import Control.Principal.ManageAllProductsManager;
import Control.Principal.ManageCategories.ManageCategoriesManager;
import Control.Principal.ManageRequestsManager;
import Control.Principal.ManageUsersManager;
import Control.Principal.PrincipalManager;
import Control.Principal.ViewDiscountCodes.ViewDiscountCodesManager;
import Models.Account.Account;
import Models.Account.Customer;
import View.ErrorProcessor;
import View.Menu;

import java.util.ArrayList;

public class PrincipalMenu extends Menu {

    public PrincipalMenu(Manager manager) {
        super(manager);
        principalMenu();
    }

    public void principalMenu() {
        while (true) {
            String input = scanner.nextLine();
            if (getMatcher(input, "^view personal info$").find()) {
                viewPersonalInfo();
            } else if (getMatcher(input, "^manage users$").find()) {
                manageUsers();
            } else if (getMatcher(input, "^manage all products$").find()) {
                manageAllProducts();
            } else if (getMatcher(input, "^create discount code$").find()) {
                createDiscountCode();
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
        ((PrincipalManager) manager).createDiscountCode(getNewDiscountInput(), getAllowedCustomersNames());
    }

    private ArrayList<String> getNewDiscountInput() {
        while (true) {
            ArrayList<String> inputs = new ArrayList<>();
            System.out.println("Enter discount percent:");
            String input = scanner.nextLine();
            if (!manager.checkPercent(input)) {
                ErrorProcessor.invalidInput();
                continue;
            }
            inputs.add(input);
            System.out.println("Enter maximum discount amount:");
            input = scanner.nextLine();
            if (!manager.checkNumber(input)) {
                ErrorProcessor.invalidInput();
                continue;
            }
            inputs.add(input);
            System.out.println("Enter discount allowed use count:"); // :/
            input = scanner.nextLine();
            if (!manager.checkNumber(input)) {
                ErrorProcessor.invalidInput();
                continue;
            }
            inputs.add(input);
            System.out.println("Enter beginning date(yyyy-MM-dd):");
            input = scanner.nextLine().trim();
            if (!manager.checkDate(input)) {
                ErrorProcessor.invalidInput();
                continue;
            }
            inputs.add(input);
            System.out.println("Enter ending date(yyyy-MM-dd):");
            input = scanner.nextLine().trim();
            if (!manager.checkDate(input)) {
                ErrorProcessor.invalidInput();
                continue;
            }
            inputs.add(input);
            return inputs;
        }
    }

    // TODO FUCK
    private ArrayList<String> getAllowedCustomersNames() {
        System.out.println("Enter names of customers whom are allowed to use discount(Enter 0 to stop):");
        ArrayList<String> customerNames = new ArrayList<>();
        while (true) {
            String customerName = scanner.nextLine();
            Account account;
            if (customerName.equals("0"))
                break;
            if ((account = Account.getAccountByUsername(customerName)) != null) {
                if (account instanceof Customer) {
                    customerNames.add(customerName);
                } else ErrorProcessor.noCustomerWithUsername();
            } else ErrorProcessor.wrongUsername();
        }
        return customerNames;
    }

    private void viewPersonalInfo() {
        manager.viewPersonalInfo();
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
                "back");
    }
}
