package View.CustomerMenus;

import Control.CustomerManagers.ViewOrdersManager;
import Control.Manager;

import java.util.regex.Matcher;

public class ViewOrdersMenu extends CustomerMenu {

    ViewOrdersManager viewOrdersManager = (ViewOrdersManager) manager;
    public ViewOrdersMenu(Manager manager) {
        super(manager);
        seeOrders();
    }

    private void seeOrders() {
        String input;
        Matcher matcher;
        while((input=scanner.nextLine().trim()).matches("(?i)back")){
            if(input.matches("(?i)help")){
                System.out.println("⇒ show order [orderId]\n" +
                        "⇒ rate [productId] [1-5]");
            } else if ((matcher = ConsoleCommand.SHOW_ORDER.getStringMatcher(input)).find()){
                System.out.println(viewOrdersManager.showOrderById(matcher.group(1)));
            } else if ((ConsoleCommand.RATE.getStringMatcher(input)).find()) {
                viewOrdersManager.rateProduct(matcher.group(1),Integer.parseInt(matcher.group(2)));
            }
        }
    }
}
