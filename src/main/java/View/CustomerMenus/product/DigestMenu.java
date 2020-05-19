package View.CustomerMenus.product;

import Control.CustomerManagers.DigestMenuManager;
import Control.Manager;
import View.CustomerMenus.ConsoleCommand;
import View.CustomerMenus.customer.CustomerMenu;
import View.ErrorProcessor;

import java.util.regex.Matcher;

public class DigestMenu extends CustomerMenu {

    DigestMenuManager digestMenuManager = (DigestMenuManager) manager;

    public DigestMenu(Manager manager) {
        super(manager);
        digestMenu();
    }

    private void digestMenu() {
        String input;
        Matcher matcher;
        while (!(input = scanner.nextLine().trim()).matches("(?i)back")) {
            if (ConsoleCommand.ADD_TO_CART.getStringMatcher(input).find()) {
                //TODO IF NOT LOGGED IN LOG IN FIRST
                //TODO ADD ONLY IF PRODUCT IS AVAILABLE
                digestMenuManager.addToCart();
            } else if ((matcher = ConsoleCommand.SELECT_SELLER.getStringMatcher(input)).find()) {
                System.out.println("\nthis option will be added soon!");
                //digestMenuManager.selectSeller(matcher.group(1));
            } else if (ConsoleCommand.HELP.getStringMatcher(input).find()) {
                System.out.println(help());
            } else ErrorProcessor.invalidInput();
        }
    }



    private String help() {
        return "\n\t⇒ add to cart"+
                "\nhelp" +
                "\nback";
    }
}
