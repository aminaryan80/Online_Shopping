package View.Principal.ViewDiscountCodes;

import Control.Manager;
import Control.Principal.ViewDiscountCodes.EditDiscountCodeManager;
import View.ErrorProcessor;
import View.Menu;

public class EditDiscountCodeMenu extends Menu {
    public EditDiscountCodeMenu(Manager manager) {
        super(manager);
        editDiscountCode();
    }

    private void editDiscountCode() {
        System.out.println("Enter field:");
        showFilters();
        String input = scanner.nextLine();
        switch (input) {
            case "beginning date":
                editBeginningDate();
                break;
            case "ending date":
                editEndingDate();
                break;
            case "discount percent":
                editDiscountPercent();
                break;
            case "maximum amount":
                editMaximumAmount();
                break;
            case "discount user count":
                editDiscountUserCount();
                break;
            default:
                ErrorProcessor.invalidInput();
        }
    }

    private void showFilters() {
        System.out.println("beginning date\n" +
                "ending date\n" +
                "discount percent\n" +
                "maximum amount\n" +
                "discount user count");
    }

    private void editBeginningDate() {
        System.out.println("Enter date(yyyy-MM-dd HH:mm:ss AM|PM):");
        String input = scanner.nextLine();
        if (manager.checkDate(input)) {
            ((EditDiscountCodeManager) manager).editBeginningDate(input);
        } else ErrorProcessor.invalidInput();
    }

    private void editEndingDate() {
        System.out.println("Enter date(yyyy-MM-dd HH:mm:ss AM|PM):");
        String input = scanner.nextLine();
        if (manager.checkDate(input)) {
            ((EditDiscountCodeManager) manager).editEndingDate(input);
        } else ErrorProcessor.invalidInput();

    }

    private void editDiscountPercent() {
        System.out.println("Enter discount percent:");
        String input = scanner.nextLine();
        if (manager.checkPercent(input)) {
            ((EditDiscountCodeManager) manager).editDiscountPercent(Integer.parseInt(input));
        } else ErrorProcessor.invalidInput();
    }

    private void editMaximumAmount() {
        System.out.println("Enter maximum amount:");
        String input = scanner.nextLine();
        if (manager.checkNumber(input)) {
            ((EditDiscountCodeManager) manager).editMaximumAmount(Double.parseDouble(input));
        } else ErrorProcessor.invalidInput();
    }

    private void editDiscountUserCount() {
        System.out.println("Enter discount user count:");
        String input = scanner.nextLine();
        if(getMatcher(input, "^\\d+$").find()) {
            ((EditDiscountCodeManager) manager).editDiscountUserCount(Integer.parseInt(input));
        } else ErrorProcessor.invalidInput();
    }
}