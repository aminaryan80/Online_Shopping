package View.Principal;

import Control.Manager;
import Control.Principal.ManageUsersManager;
import View.ErrorProcessor;
import View.MainMenu;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class ManageUsersMenu extends MainMenu {
    public ManageUsersMenu(Manager manager) {
        super(manager);
        manageUsers();
    }

    private void manageUsers() {
        showUsers();
        while (true) {
            Matcher matcher;
            String input = scanner.nextLine();
            if ((matcher = getMatcher(input, "^view (\\S+)$")).find()) {
                viewUsername(matcher.group(1));
            } else if ((matcher = getMatcher(input, "^delete user (\\S+)$")).find()) {
                deleteUsername(matcher.group(1));
            } else if (getMatcher(input, "^create manager profile$").find()) {
                createManagerProfile();
            } else if(getMatcher(input, "^back$").find()) {
                return;
            } else if(getMatcher(input, "^help$").find()) {
                help();
            } else ErrorProcessor.invalidInput();
        }
    }

    private void showUsers() {
        for (String string : ((ManageUsersManager) manager).showUsers()) {
            System.out.println(string);
        }
    }

    private void viewUsername(String username) {
        System.out.println(((ManageUsersManager) manager).viewUsername(username));
    }

    private void deleteUsername(String username) {
        ((ManageUsersManager) manager).deleteUsername(username);
    }

    private void createManagerProfile() {
        // TODO This is all wrong. fix it later
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
                "help\n" +
                "back");
    }
}
