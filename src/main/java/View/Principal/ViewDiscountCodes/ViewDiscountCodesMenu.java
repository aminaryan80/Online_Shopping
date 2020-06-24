package View.Principal.ViewDiscountCodes;

import Control.Manager;
import Control.Principal.ViewDiscountCodes.ViewDiscountCodesManager;
import Models.Shop.Off.Discount;
import View.ErrorProcessor;
import View.Menu;

import java.io.IOException;
import java.util.ArrayList;
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
            String command = scanner.nextLine();
            if ((matcher = getMatcher(command, "^view discount code (\\S+)$")).find()) {
                viewDiscountCode(matcher.group(1));
            } else if ((matcher = getMatcher(command, "^edit discount code (\\S+)$")).find()) {
                editDiscountCode(matcher.group(1));
            } else if (command.equals("show available sorts")) {
                showAvailableSorts();
            } else if ((matcher = getMatcher(command, "^remove discount code (\\S+)$")).find()) {
                deleteDiscountCode(matcher.group(1));
            } else if ((matcher = getMatcher(command, "sort (\\S+)")).find()) {
                sort(matcher.group(1));
            } else if (command.equals("current sort")) {
                currentSort();
            } else if (command.equals("disable sort")) {
                disableSort();
            } else if (getMatcher(command, "^help$").find()) {
                help();
            } else if (getMatcher(command, "^back$").find()) {
                return;
            } else ErrorProcessor.invalidInput();
        }
    }

    private void currentSort() {
        System.out.println(((ViewDiscountCodesManager) manager).currentSort());
    }

    private void disableSort() {
        ArrayList<String> discounts = ((ViewDiscountCodesManager) manager).disableSort();
        for (String discount : discounts) {
            System.out.println(discount);
        }
    }

    private void showAvailableSorts() {
        System.out.println(((ViewDiscountCodesManager) manager).showAvailableSorts());
    }

    private void sort(String sort) {
        if (((ViewDiscountCodesManager) manager).isEnteredSortFieldValid(sort)) {
            System.out.println("do you want it to be ascending (answer with true or false)");
            String isAscending = scanner.nextLine();
            ArrayList<Discount> sortedDiscounts = ((ViewDiscountCodesManager) manager).sort(sort, Boolean.parseBoolean(isAscending));
            for (Discount sortedDiscount : sortedDiscounts) {
                System.out.println(sortedDiscount);
            }
        } else {
            ErrorProcessor.invalidInput();
        }
    }

    private void showDiscounts() {
        ((ViewDiscountCodesManager) manager).showDiscounts();
    }

    private void deleteDiscountCode(String id) {
        if (Discount.hasDiscountWithId(id)) {
            try {
                ((ViewDiscountCodesManager) manager).deleteDiscountCode(id);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else ErrorProcessor.invalidDiscountId();
    }

    private void editDiscountCode(String id) {
        if (Discount.hasDiscountWithId(id)) {
            ((ViewDiscountCodesManager) manager).editDiscountCode(id);
        } else ErrorProcessor.invalidDiscountId();
    }

    private void viewDiscountCode(String id) {
        if (Discount.hasDiscountWithId(id)) {
            System.out.println(((ViewDiscountCodesManager) manager).viewDiscountCode(id));
        } else ErrorProcessor.invalidDiscountId();
    }

    private void help() {
        System.out.println("view discount code [code]\n" +
                "edit discount code [code]\n" +
                "remove discount code [code]\n" +
                "show available sorts\n" +
                "sort [an available sort]\n" +
                "current sort\n" +
                "disable sort\n" +
                "help\n" +
                "back");
    }
}
