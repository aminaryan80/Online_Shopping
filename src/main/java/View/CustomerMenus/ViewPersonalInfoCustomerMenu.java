package View.CustomerMenus;

import Control.CustomerManagers.ViewPersonalInfoCustomerManager;
import Control.Manager;
import View.ConsoleCommand;
import View.CustomerMenu;
import View.ErrorProcessor;

import java.util.regex.Matcher;

public class ViewPersonalInfoCustomerMenu extends CustomerMenu {
    ViewPersonalInfoCustomerManager viewPersonalInfoCustomerManager = (ViewPersonalInfoCustomerManager) manager;

    public ViewPersonalInfoCustomerMenu(Manager manager) {
        super(manager);
        viewPersonalInfoCustomer();
    }

    public void viewPersonalInfoCustomer() {
        viewPersonalInfoCustomerManager.viewPersonalInfo();
        String input;
        Matcher matcher;
        while (!(input = scanner.nextLine().trim()).matches("(?i)back")) {
            if ((matcher = ConsoleCommand.EDIT.getStringMatcher(input)).find()) {
                String field = matcher.group(1);
                editInfoCustomer(field);
            }
        }
    }

    private void editInfoCustomer(String field) {
        if (viewPersonalInfoCustomerManager.isEnteredAccountFieldValid(field)) {
            System.out.println("Enter new Field :");
            String newAttribute = scanner.nextLine();
            if (viewPersonalInfoCustomerManager.isNewFieldAcceptable(field, newAttribute)) {
                viewPersonalInfoCustomerManager.editAccountAttribute(field, newAttribute);
                System.out.println("successful!");
            } else ErrorProcessor.invalidEnteredInEditField();
        } else ErrorProcessor.invalidEditField();
    }
}
