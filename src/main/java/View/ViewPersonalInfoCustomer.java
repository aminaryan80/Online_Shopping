package View;

import Control.CustomerManager;
import Control.Manager;

import java.util.regex.Matcher;

public class ViewPersonalInfoCustomer extends MainMenu {
    CustomerManager customerManager =(CustomerManager) manager;
    public ViewPersonalInfoCustomer(Manager manager) {
        super(manager);
        viewPersonalInfoCustomer();
    }

    public void viewPersonalInfoCustomer(){
        customerManager.viewPersonalInfo();
        String input;
        Matcher matcher;
        while (!(input=scanner.nextLine().trim()).matches("(?i)back") ) {
            if((matcher=ConsoleCommand.EDIT.getStringMatcher(input)).find()) {
                String field = matcher.group(1);
                editInfoCustomer(field);
            }
        }
    }

    private void editInfoCustomer(String field){
        if(customerManager.isEnteredAccountFieldValid(field)){
            System.out.println("Enter new Field :");
            String newAttribute = scanner.nextLine();
            if(customerManager.isNewFieldAcceptable(field,newAttribute)){
                customerManager.editAccountAttribute(field,newAttribute);
                System.out.println("successful!");
            } else ErrorProcessor.invalidEnteredInEditField();
        } else ErrorProcessor.invalidEditField();
    }
}
