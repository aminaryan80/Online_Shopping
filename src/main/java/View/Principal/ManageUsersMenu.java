package View.Principal;

import Control.Manager;
import Control.Principal.ManageUsersManager;
import Control.Products.ProductsManager;
import View.ErrorProcessor;
import View.Menu;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class ManageUsersMenu extends Menu {
    public ManageUsersMenu(Manager manager) {
        super(manager);
        manageUsers();
    }

    private void manageUsers() {
        showUsers();
        while (true) {
            Matcher matcher;
            String command = scanner.nextLine();
            if ((matcher = getMatcher(command, "^view (\\S+)$")).find()) {
                viewUsername(matcher.group(1));
            } else if ((matcher = getMatcher(command, "^delete user (\\S+)$")).find()) {
                deleteUsername(matcher.group(1));
            } else if (getMatcher(command, "^create manager profile$").find()) {
                createManagerProfile();
            } else if (command.equals("show available sorts")) {
                showAvailableSorts();
            } else if ((matcher = getMatcher(command, "sort (\\S+)")).find()) {
                sort(matcher.group(1));
            } else if (command.equals("current sort")) {
                currentSort();
            } else if (command.equals("disable sort")) {
                disableSort();
            } else if(getMatcher(command, "^help$").find()) {
                help();
            } else if (command.equals("back")) {
                return;
            } else {
                ErrorProcessor.invalidInput();
            }
        }
    }

    private void currentSort() {
        System.out.println(((ManageUsersManager) manager).currentSort());
    }

    private void disableSort() {
        ArrayList<String> users = ((ManageUsersManager) manager).disableSort();
        for (String user : users) {
            System.out.println(user);
        }
    }

    private void showAvailableSorts() {
        System.out.println(((ManageUsersManager) manager).showAvailableSorts());
    }

    private void sort(String sort) {
        if (((ManageUsersManager) manager).isEnteredSortFieldValid(sort)) {
            System.out.println("do you want it to be ascending (answer with true or false)");
            String isAscending = scanner.nextLine();
            ArrayList<String> sortedUsers = ((ManageUsersManager) manager).sort(sort, Boolean.parseBoolean(isAscending));
            for (String sortedUser : sortedUsers) {
                System.out.println(sortedUser);
            }
        } else {
            ErrorProcessor.invalidInput();
        }
    }

    private void showUsers() {
        for (String string : ((ManageUsersManager) manager).showUsers()) {
            System.out.println(string);
        }
    }

    private void viewUsername(String username) {
        String usernameInfo = ((ManageUsersManager) manager).viewUsername(username);
        if (usernameInfo != null)
            System.out.println(usernameInfo);
        else ErrorProcessor.wrongUsername();
    }

    private void deleteUsername(String username) {
        ((ManageUsersManager) manager).deleteUsername(username);
    }

    private void createManagerProfile() {
        ((ManageUsersManager) manager).createManagerProfile(getNewManagerInput());
    }

    private ArrayList<String> getNewManagerInput() {
        while (true) {
            ArrayList<String> inputs = new ArrayList<>();
            System.out.println("Enter username:");
            String input = scanner.nextLine();
            if (manager.userExistsWithUsername(input)) {
                ErrorProcessor.invalidInput();
                continue;
            }
            inputs.add(input);
            System.out.println("Enter password:");
            inputs.add(scanner.nextLine());
            System.out.println("Enter email:");
            input = scanner.nextLine();
            if(manager.checkEmail(input)) {
                ErrorProcessor.invalidInput();
                continue;
            }
            inputs.add(input);
            System.out.println("Enter phone number:");
            input = scanner.nextLine();
            if(manager.checkPhoneNumber(input)) {
                ErrorProcessor.invalidInput();
                continue;
            }
            inputs.add(input);
            System.out.println("Enter first name:");
            inputs.add(scanner.nextLine());
            System.out.println("Enter last name:");
            inputs.add(scanner.nextLine());
            return inputs;
        }
    }

    private void help() {
        System.out.println("view [username]\n" +
                "delete user [username]\n" +
                "create manager profile\n" +
                "show available sorts\n" +
                "sort [an available sort]\n" +
                "current sort\n" +
                "disable sort\n" +
                "help\n" +
                "back");
    }
}
