package View.CustomerMenus.customer;

import Control.CustomerManagers.ViewCartManager;
import Control.CustomerManagers.ViewOrdersManager;
import Control.Manager;
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
        while((input=scanner.nextLine().trim()).matches("(?i)back")){
            if(ConsoleCommand.HELP.getStringMatcher(input).find()){
                System.out.println(help());
            } else if ((matcher = ConsoleCommand.SHOW_ORDER.getStringMatcher(input)).find()){
                System.out.println(viewOrdersManager.showOrderById(matcher.group(1)));
            } else if ((ConsoleCommand.RATE.getStringMatcher(input)).find()) {
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
