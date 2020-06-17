package View.CustomerMenus.customer;

import Control.CustomerManagers.ProductPageManager;
import Control.CustomerManagers.PurchaseManager;
import Control.CustomerManagers.ViewCartManager;
import Control.Manager;
import Models.Shop.Product.Product;
import View.CustomerMenus.ConsoleCommand;
import View.ErrorProcessor;
import View.Menu;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class ViewCartMenu extends Menu {
    ViewCartManager viewCartManager = (ViewCartManager) manager;

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
                Product product = viewCartManager.getProduct(matcher.group(1));
                if (product != null) {
                    if (viewCartManager.doesProductExistInCart(product))
                    new ProductPageManager(manager.getAccount(), product);
                    else ErrorProcessor.productDoesNotExistInCart();
                } else ErrorProcessor.invalidProductId();
            } else if ((matcher = ConsoleCommand.INCREASE_PRODUCT.getStringMatcher(input)).find()) {
                try {
                    viewCartManager.productQuantity(matcher.group(1), true);
                    System.out.println("successful");
                } catch (ViewCartManager.ProductDoNotExistInCartException e) {
                    System.out.println(e.getMessage());
                }
            } else if ((matcher = ConsoleCommand.DECREASE_PRODUCT.getStringMatcher(input)).find()) {
                try {
                    viewCartManager.productQuantity(matcher.group(1), false);
                    System.out.println("successful");
                } catch (ViewCartManager.ProductDoNotExistInCartException e) {
                    System.out.println(e.getMessage());
                }
            } else if ((matcher = ConsoleCommand.SHOW_TOTAL_PRICE.getStringMatcher(input)).find()) {
                System.out.println(viewCartManager.getTotalPrice(null));
            } else if ((matcher = ConsoleCommand.PURCHASE.getStringMatcher(input)).find()) {
                if (!viewCartManager.isCartEmpty())
                    new PurchaseManager(manager.getAccount());
                else ErrorProcessor.emptyCart();
            } else if (ConsoleCommand.HELP.getStringMatcher(input).find()) {
                showCart();
            } else if (input.equals("show available sorts")) {
                showAvailableSorts();
            } else if ((matcher = getMatcher(input, "sort (\\S+)")).find()) {
                sort(matcher.group(1));
            } else if (input.equals("current sort")) {
                currentSort();
            } else if (input.equals("disable sort")) {
                disableSort();
            } else {
                ErrorProcessor.invalidInput();
            }
        }
    }

    private void showAvailableSorts() {
        System.out.println(((ViewCartManager) manager).showAvailableSorts());
    }

    private void sort(String sort) {
        if (((ViewCartManager) manager).isEnteredSortFieldValid(sort)) {
            System.out.println("do you want it to be ascending (answer with true or false)");
            String isAscending = scanner.nextLine();
            ArrayList<Product> sortedProducts = ((ViewCartManager) manager).sort(sort, Boolean.parseBoolean(isAscending));
            for (Product sortedProduct : sortedProducts) {
                System.out.println(sortedProduct.viewProductInShort());
            }
        } else {
            ErrorProcessor.invalidInput();
        }
    }

    private void currentSort() {
        System.out.println(((ViewCartManager) manager).currentSort());
    }

    private void disableSort() {
        ArrayList<String> products = ((ViewCartManager) manager).disableSort();
        for (String product : products) {
            System.out.println(product);
        }
    }

    private void showCart() {
        System.out.println("show products\n" +
                "show available sorts\n" +
                "sort [an available sort]\n" +
                "current sort\n" +
                "disable sort\n" +
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
