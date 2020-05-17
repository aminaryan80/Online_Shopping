package View.UserPanel;

import Control.Manager;
import Control.UserPanel.CreateNewAccountManager;
import View.ErrorProcessor;
import View.Menu;

import java.util.ArrayList;

public class CreateNewAccountMenu extends Menu {

    public CreateNewAccountMenu(Manager manager, String username, String type) {
        super(manager);
        createNewAccount(username, type);
    }

    private void createNewAccount(String username, String type) {
        if (((CreateNewAccountManager) manager).canCreateNewAccount(username, type))
            ((CreateNewAccountManager) manager).createNewAccount(getNewManagerInput(type), username, type);
    }

    private ArrayList<String> getNewManagerInput(String type) {
        while (true) {
            ArrayList<String> inputs = new ArrayList<>();
            String input;
            System.out.println("Enter password:");
            inputs.add(scanner.nextLine());
            System.out.println("Enter email:");
            input = scanner.nextLine();
            if (!manager.checkEmail(input)) {
                ErrorProcessor.invalidInput();
                continue;
            }
            inputs.add(input);
            System.out.println("Enter phone number:");
            input = scanner.nextLine();
            if (!manager.checkPhoneNumber(input)) {
                ErrorProcessor.invalidInput();
                continue;
            }
            inputs.add(input);
            System.out.println("Enter first name:");
            inputs.add(scanner.nextLine());
            System.out.println("Enter last name:");
            inputs.add(scanner.nextLine());
            if (type.equals("customer") || type.equals("seller")) {
                System.out.println("Enter balance:");
                input = scanner.nextLine();
                if (!manager.checkNumber(input)) {
                    ErrorProcessor.invalidInput();
                    continue;
                }
                inputs.add(input);
                if (type.equals("seller")) {
                    System.out.println("Enter company name:");
                    inputs.add(scanner.nextLine());
                }
            }
            return inputs;
        }
    }
}
