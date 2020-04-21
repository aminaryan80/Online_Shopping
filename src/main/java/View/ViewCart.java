package View;

import Control.CustomerManager;
import Control.Manager;
import Control.ProductPageManager;
import Models.Shop.Product;

import java.util.regex.Matcher;

public class ViewCart extends MainMenu {
    CustomerManager customerManager =(CustomerManager) manager;
    public ViewCart(Manager manager) {
        super(manager);
        viewCart();
    }

    private void viewCart() {
        showCart();
        String input;
        Matcher matcher;
        while (!(input = scanner.nextLine().trim()).matches("(?i)back")) {
            if (ConsoleCommand.SHOW_PRODUCTS.getStringMatcher(input).find()) {
                System.out.println(customerManager.showProducts());
            } else if ((matcher = ConsoleCommand.VIEW_PRODUCT_ID.getStringMatcher(input)).find()) {
                Product product;
                if((product = Product.getProductById(matcher.group(1))) != null)
                new ProductPageManager(manager.getAccount(),product);
                else ErrorProcessor.invalidProductId();
            } else if ((matcher = ConsoleCommand.INCREASE_PRODUCT.getStringMatcher(input)).find()) {
                customerManager.ProductQuantity(matcher.group(1),true);
            } else if ((matcher = ConsoleCommand.DECREASE_PRODUCT.getStringMatcher(input)).find()) {
                customerManager.ProductQuantity(matcher.group(1),false);
            } else if ((matcher = ConsoleCommand.SHOW_TOTAL_PRICE.getStringMatcher(input)).find()) {
                customerManager.getTotalPrice();
            } else if ((matcher = ConsoleCommand.PURCHASE.getStringMatcher(input)).find()) {
                
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
