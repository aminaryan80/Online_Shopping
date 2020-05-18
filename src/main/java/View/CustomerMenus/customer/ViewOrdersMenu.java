package View.CustomerMenus.customer;

import Control.CustomerManagers.ViewCartManager;
import Control.CustomerManagers.ViewOrdersManager;
import Control.Manager;
import Models.Shop.Product.Product;
import View.CustomerMenus.ConsoleCommand;
import View.ErrorProcessor;
import View.Menu;

import java.util.regex.Matcher;

public class ViewOrdersMenu extends Menu {

    ViewOrdersManager viewOrdersManager = (ViewOrdersManager) manager;

    public ViewOrdersMenu(Manager manager) {
        super(manager);
        seeOrders();
    }

    private void seeOrders() {
        String input;
        Matcher matcher;
        while (true) {
            input = scanner.nextLine().trim();
            if (input.matches("(?i)back")) {
                return;
            } else if(input.matches("(?i)help")){
                System.out.println(help());
            } else if ((matcher = ConsoleCommand.SHOW_ORDER.getStringMatcher(input)).find()) {
                System.out.println(viewOrdersManager.showOrderById(matcher.group(1)));
            } else if ((matcher = ConsoleCommand.RATE.getStringMatcher(input)).find()) {
                try {
                    viewOrdersManager.rateProduct(matcher.group(1),Integer.parseInt(matcher.group(2)));
                } catch (ViewCartManager.ProductDoNotExistAtAllException e) {
                    ErrorProcessor.invalidProductId();
                }
            }
        }
    }

    private String help() {
        return "⇒ show order [orderId]\n" +
                "⇒ rate [productId] [1-5]\n"+
                "help\nback";
    }
}
