package View.CustomerMenus.product;

import Control.CustomerManagers.DigestMenuManager;
import Control.CustomerManagers.ProductPageManager;
import Control.Manager;
import Models.Shop.Product.Product;
import View.CustomerMenus.ConsoleCommand;
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
                Product otherProduct = Product.getProductById(matcher.group(1));
                if(otherProduct==null){
                    ErrorProcessor.invalidProductId();
                } else System.out.println(productPageManager.compare(otherProduct));
            } else if (input.matches("(?i)add comment")) {
                productPageManager.addComment(getComment(true), getComment(false));
            } else if (ConsoleCommand.HELP.getStringMatcher(input).find()) {
                System.out.println(help());
            } else ErrorProcessor.invalidInput();
        }
    }

    private String help() {
        return "Product " + productPageManager.getProduct().getName() + " page :" +
                "\n" +
                "digest" +
                "\n" +
                "\t⇒ add to cart" +
                "\n" +
                "\t⇒ select seller [seller_username]" +
                "\n" +
                "attributes" +
                "\n" +
                "compare [product_ID]" +
                "\n" +
                "comments" +
                "\t⇒ Add comment\n" +
                "\tTitle:\n" +
                "\tContent:"
                +"\n"
                ;
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
