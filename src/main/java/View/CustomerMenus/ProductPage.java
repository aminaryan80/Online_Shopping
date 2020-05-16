package View.CustomerMenus;

import Control.CustomerManagers.DigestMenuManager;
import Control.CustomerManagers.ProductPageManager;
import Control.Manager;
import View.ErrorProcessor;
import View.Menu;

import java.util.regex.Matcher;

public class ProductPage extends Menu {

    private ProductPageManager productPageManager = (ProductPageManager) manager;

    public ProductPage(Manager manager) {
        super(manager);
        productPage();
    }

    private void productPage() {
        String input;
        Matcher matcher;
        while (!(input = scanner.nextLine().trim()).matches("(?i)back")) {
            if (input.matches("(?i)digest")) {
                System.out.println(productPageManager.digest());
                new DigestMenuManager(manager.getAccount(), productPageManager.getProduct());
            } else if (input.matches("(?i)comments")) {
                productPageManager.comments().forEach(System.out::println);
            } else if (input.matches("(?i)attributes")) {
                System.out.println(productPageManager.attributes());
            } else if ((matcher = ConsoleCommand.COMPARE.getStringMatcher(input)).find()) {
                System.out.println(productPageManager.compare(matcher.group(1)));
            } else if (input.matches("(?i)add comment")) {
                productPageManager.addComment(getComment(true),getComment(false));
            } else ErrorProcessor.invalidInput();
        }
    }

    private String getComment(boolean isTitle) {
        if (isTitle) {
            System.out.println("Title:");
        } else {
            System.out.println("Content");
        }
        return scanner.nextLine();
    }

    private void digest() {

    }

    private void addToCart() {

    }

    private void selectSeller(String sellerUsername) {

    }

    private void attributes() {

    }

    private void compare(String id) {

    }

    private void comments() {

    }

    private void addComment() {

    }
}
