package View;

import Control.Manager;
import Control.ProductPageManager;
import Control.ViewCartManager;
import Models.Shop.Product;

import java.util.regex.Matcher;

public class ViewCartMenu extends MainMenu {
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
                viewCartManager.getTotalPrice();
            } else if ((matcher = ConsoleCommand.PURCHASE.getStringMatcher(input)).find()) {
                new PurchaseManager;
            }
        }
    }

    private void showCart() {
        System.out.println("⇒ show products\n" +
                "⇒ view [productId]\n" +
                "⇒ increase [productId]\n" +
                "⇒ decrease [productId]\n" +
                "⇒ show total price\n" +
                "⇒ purchase\n"
        );
    }
}
