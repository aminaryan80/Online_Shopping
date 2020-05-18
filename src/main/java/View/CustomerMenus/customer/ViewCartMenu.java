package View.CustomerMenus.customer;

import Control.CustomerManagers.ProductPageManager;
import Control.CustomerManagers.PurchaseManager;
import Control.CustomerManagers.ViewCartManager;
import Control.Manager;
import Models.Shop.Product.Product;
import View.CustomerMenus.ConsoleCommand;
import View.ErrorProcessor;
import View.Menu;

import java.util.regex.Matcher;

public class ViewCartMenu extends Menu {
    ViewCartManager viewCartManager =(ViewCartManager) manager;

    public ViewCartMenu(Manager manager) {
        super(manager);
        viewCart();
    }

    private void viewCart() {
        showCart();
        String input;
        Matcher matcher;
        while (!(input = scanner.nextLine().trim()).matches("(?i)back")) {
            if (ConsoleCommand.SHOW_PRODUCTS.getStringMatcher(input).find()) {
                System.out.println(viewCartManager.showProducts());
            } else if ((matcher = ConsoleCommand.VIEW_PRODUCT_ID.getStringMatcher(input)).find()) {
                Product product;
                if((product = Product.getProductById(matcher.group(1))) != null)
                new ProductPageManager(manager.getAccount(),product);
                else ErrorProcessor.invalidProductId();
            } else if ((matcher = ConsoleCommand.INCREASE_PRODUCT.getStringMatcher(input)).find()) {
                try {
                    viewCartManager.ProductQuantity(matcher.group(1), true);
                    System.out.println("successful");
                } catch (ViewCartManager.ProductDoNotExistAtAllException|ViewCartManager.ProductDoNotExistInCartException e) {
                    System.out.println(e.getMessage());
                }
            } else if ((matcher = ConsoleCommand.DECREASE_PRODUCT.getStringMatcher(input)).find()) {
                try {
                    viewCartManager.ProductQuantity(matcher.group(1), false);
                    System.out.println("successful");
                } catch (ViewCartManager.ProductDoNotExistAtAllException|ViewCartManager.ProductDoNotExistInCartException e) {
                    System.out.println(e.getMessage());
                }
            } else if ((matcher = ConsoleCommand.SHOW_TOTAL_PRICE.getStringMatcher(input)).find()) {
                viewCartManager.getTotalPrice(null);
            } else if ((matcher = ConsoleCommand.PURCHASE.getStringMatcher(input)).find()) {
                new PurchaseManager(manager.getAccount());
            } else if (ConsoleCommand.HELP.getStringMatcher(input).find()) {
                showCart();
            }
        }
    }

    private void showCart() {
        System.out.println("show products\n" +
                "view [productId]\n" +
                "increase [productId]\n" +
                "decrease [productId]\n" +
                "show total price\n" +
                "purchase\n" +
                "user panel\n" +
                "help\n" +
                "back"
        );
    }
}
