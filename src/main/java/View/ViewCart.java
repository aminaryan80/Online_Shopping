package View;

import Control.Manager;

public class ViewCart extends MainMenu {

    public ViewCart(Manager manager) {
        super(manager);
        viewCart();
    }

    private void viewCart() {
        showCart();
        String input;
        while (!(input = scanner.nextLine().trim()).matches("(?i)back")) {
            if (ConsoleCommand.SHOW_PRODUCTS.getStringMatcher(input).find()) {
                System.out.println(customerManager.showProducts());
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
