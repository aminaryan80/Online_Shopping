package View.CustomerMenus.customer;

import Control.CustomerManagers.ViewPersonalInfoCustomerManager;
import Control.Manager;
import View.CustomerMenus.ConsoleCommand;
import View.ErrorProcessor;
import View.Menu;

import javax.imageio.plugins.tiff.ExifInteroperabilityTagSet;
import java.util.regex.Matcher;

public class ViewPersonalInfoCustomerMenu extends Menu {
    ViewPersonalInfoCustomerManager viewPersonalInfoCustomerManager = (ViewPersonalInfoCustomerManager) manager;

    public ViewPersonalInfoCustomerMenu(Manager manager) {
        super(manager);
        viewPersonalInfoCustomer();
    }

    public void viewPersonalInfoCustomer() {
        System.out.println(viewPersonalInfoCustomerManager.viewCustomerPersonalInfo());
        String input;
        Matcher matcher;
        while (!(input = scanner.nextLine().trim()).matches("(?i)back")) {
            if ((matcher = ConsoleCommand.EDIT.getStringMatcher(input)).find()) {
                String field = matcher.group(1);
                editInfoCustomer(field);
            } else if(ConsoleCommand.HELP.getStringMatcher(input).find()){
                System.out.println(help());
            } else ErrorProcessor.invalidInput();
        }
    }

    private String help() {
        return "\t⇒ edit [field]\n"+
                "-field : (name | last name | phone number | email | password | balance)"
                +"\n"
                +"help"+
                "\n"+
                "back";
    }


    private void editInfoCustomer(String field) {
        if (viewPersonalInfoCustomerManager.isEnteredAccountFieldValid(field)) {
            if (field.matches("(i?)password")) {
                System.out.println("enter old password :");
                String oldPassword = scanner.nextLine().trim();
                System.out.println("enter new password :");
                String newPassword = scanner.nextLine().trim();
                if (viewPersonalInfoCustomerManager.checkPassword(oldPassword)) {
                    succussfulChange(field, newPassword);
                } else ErrorProcessor.wrongPassword();
            } else {
                System.out.println("Enter " + field + " replacement :");
                String newAttribute = scanner.nextLine();
                if (viewPersonalInfoCustomerManager.isNewFieldAcceptable(field, newAttribute)) {
                    succussfulChange(field, newAttribute);
                } else ErrorProcessor.invalidEnteredInEditField();
            }
        } else ErrorProcessor.invalidEditField();
    }

    private void succussfulChange(String field, String newPassword) {
        viewPersonalInfoCustomerManager.editAccountAttribute(field, newPassword);
        System.out.println("successful!");
    }
}
