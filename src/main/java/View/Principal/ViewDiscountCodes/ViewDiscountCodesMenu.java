package View.Principal.ViewDiscountCodes;

import Control.Manager;
import Control.Principal.ViewDiscountCodes.ViewDiscountCodesManager;
import Models.Shop.Off.Discount;
import View.ErrorProcessor;
import View.Menu;

import java.util.regex.Matcher;

public class ViewDiscountCodesMenu extends Menu {
    public ViewDiscountCodesMenu(Manager manager) {
        super(manager);
        viewDiscountCodesMenu();
    }

    private void viewDiscountCodesMenu() {
        showDiscounts();
        while (true) {
            Matcher matcher;
            String input = scanner.nextLine();
            if ((matcher = getMatcher(input, "^view discount code (\\S+)$")).find()) {
                viewDiscountCode(matcher.group(1));
            } else if ((matcher = getMatcher(input, "^edit discount code (\\S+)$")).find()) {
                editDiscountCode(matcher.group(1));
            } else if ((matcher = getMatcher(input, "^remove discount code (\\S+)$")).find()) {
                deleteDiscountCode(matcher.group(1));
            } else if (getMatcher(input, "^help$").find()) {
                help();
            } else if (getMatcher(input, "^back$").find()) {
                return;
            } else ErrorProcessor.invalidInput();
        }
    }

    private void showDiscounts() {
        ((ViewDiscountCodesManager) manager).showDiscounts();
    }

    private void deleteDiscountCode(String id) {
        if (Discount.hasDiscountWithId(id)) {
            ((ViewDiscountCodesManager) manager).deleteDiscountCode(id);
        } else ErrorProcessor.invalidDiscountId();
    }

    private void editDiscountCode(String id) {
        if (Discount.hasDiscountWithId(id)) {
            ((ViewDiscountCodesManager) manager).editDiscountCode(id);
        } else ErrorProcessor.invalidDiscountId();
    }

    private void viewDiscountCode(String id) {
        if (Discount.hasDiscountWithId(id)) {
            ((ViewDiscountCodesManager) manager).viewDiscountCode(id);
        } else ErrorProcessor.invalidDiscountId();
    }

    private void help() {
        System.out.println("view discount code [code]\n" +
                "edit discount code [code]\n" +
                "remove discount code [code]\n" +
                "help\n" +
                "back");
    }
}
