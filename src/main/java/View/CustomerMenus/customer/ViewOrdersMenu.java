package View.CustomerMenus.customer;

import Control.CustomerManagers.ViewOrdersManager;
import Control.Manager;
import Models.Shop.Product.Product;
import View.CustomerMenus.ConsoleCommand;
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
            } else if (input.matches("(?i)help")) {
                help();
            } else if ((matcher = ConsoleCommand.SHOW_ORDER.getStringMatcher(input)).find()) {
                viewOrder(matcher.group(1));
            } else if ((matcher = ConsoleCommand.RATE.getStringMatcher(input)).find()) {
                rateProduct(matcher.group(1), Integer.parseInt(matcher.group(2)));
            }
        }
    }

    private void rateProduct(String productId, int score) {
        if (Product.hasProductWithId(productId)) {
            viewOrdersManager.rateProduct(productId, score);
        }
    }

    private void viewOrder(String id) {
        if (viewOrdersManager.canShowOrderWithId(id))
            System.out.println(viewOrdersManager.showOrderById(id));
    }

    private void help() {
        System.out.println("show order [orderId]\n" +
                "rate [productId] [1-5]");
    }
}
