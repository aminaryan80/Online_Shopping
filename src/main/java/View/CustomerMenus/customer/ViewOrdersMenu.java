package View.CustomerMenus.customer;

import Control.CustomerManagers.ViewCartManager;
import Control.CustomerManagers.ViewOrdersManager;
import Control.Manager;
import Control.Seller.SellerManager;
import Models.Shop.Product.Product;
import View.CustomerMenus.ConsoleCommand;
import View.ErrorProcessor;
import View.Menu;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class ViewOrdersMenu extends Menu {

    ViewOrdersManager viewOrdersManager = (ViewOrdersManager) manager;

    public ViewOrdersMenu(Manager manager) {
        super(manager);
        seeOrders();
    }

    private void seeOrders() {
        String command;
        Matcher matcher;
        while (true) {
            command = scanner.nextLine().trim();
            if (command.matches("(?i)back")) {
                return;
            } else if(command.matches("(?i)help")){
                System.out.println(help());
            } else if ((matcher = ConsoleCommand.SHOW_ORDER.getStringMatcher(command)).find()) {
                if(viewOrdersManager.doesLogExist(matcher.group(1)))
                    System.out.println(viewOrdersManager.showOrderById(matcher.group(1)));
                else ErrorProcessor.invalidLogId();
            } else if ((matcher = ConsoleCommand.RATE.getStringMatcher(command)).find()) {
                try {
                    viewOrdersManager.rateProduct(matcher.group(1),Integer.parseInt(matcher.group(2)));
                } catch (ViewCartManager.ProductDoNotExistAtAllException e) {
                    ErrorProcessor.invalidProductId();
                }
            } else if (command.equals("show available sorts")) {
                showAvailableSorts();
            } else if ((matcher = getMatcher(command, "sort (\\S+)")).find()) {
                sort(matcher.group(1));
            } else if (command.equals("current sort")) {
                currentSort();
            } else if (command.equals("disable sort")) {
                disableSort();
            } else {
                ErrorProcessor.invalidInput();
            }
        }
    }

    private void showAvailableSorts() {
        System.out.println(((SellerManager) manager).showAvailableSorts());
    }

    private void sort(String sort) {
        if (((ViewOrdersManager) manager).isEnteredSortFieldValid(sort)) {
            System.out.println("do you want it to be ascending (answer with true or false)");
            String isAscending = scanner.nextLine();
            ArrayList<String> sortedLogs = ((ViewOrdersManager) manager).sort(sort, Boolean.parseBoolean(isAscending));
            for (String sortedLog : sortedLogs) {
                System.out.println(sortedLog);
            }
        } else {
            ErrorProcessor.invalidInput();
        }
    }

    private void currentSort() {
        System.out.println(((ViewOrdersManager) manager).currentSort());
    }

    private void disableSort() {
        ArrayList<String> Logs = ((ViewOrdersManager) manager).disableSort();
        for (String Log : Logs) {
            System.out.println(Log);
        }
    }

    private String help() {
        return "⇒ show order [orderId]\n" +
                "⇒ rate [productId] [1-5]\n"+
                "show available sorts\n" +
                "sort [an available sort]\n" +
                "current sort\n" +
                "disable sort\n" +
                "help\nback";
    }
}
