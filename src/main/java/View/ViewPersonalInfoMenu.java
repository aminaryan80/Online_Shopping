package View;

import Control.Manager;
import Control.ViewPersonalInfoManager;

import java.util.regex.Matcher;

public class ViewPersonalInfoMenu extends Menu {

    public ViewPersonalInfoMenu(Manager manager) {
        super(manager);
        principalMenu();
    }

    public void principalMenu() {
        while (true) {
            String input = scanner.nextLine();
            Matcher matcher;
            if ((matcher = getMatcher(input, "^edit (.+)$")).find()) {
                editField(matcher.group(1));
            } else if (getMatcher(input, "^help$").find()) {
                help();
            } else if (getMatcher(input, "^back$").find()) {
                return;
            } else ErrorProcessor.invalidInput();
        }
    }

    private void editField(String field) {
        if (((ViewPersonalInfoManager) manager).isEnteredFieldValid(field)) {
            System.out.println("Enter your new Value:");
            String newValue = scanner.nextLine();
            ((ViewPersonalInfoManager) manager).editField(field, newValue);
        } else ErrorProcessor.invalidEditField();
    }


    private void help() {
        System.out.println("edit [field]\n" +
                "help\n" +
                "back");
    }
}
