package View.Products;

import Control.Manager;
import View.ErrorProcessor;
import View.Menu;

import java.util.regex.Matcher;

public class OffsPageMenu extends Menu {

    public OffsPageMenu(Manager manager) {
        super(manager);
        offsPageMenu();
    }

    public void offsPageMenu() {
        while (true) {
            Matcher matcher;
            String input = scanner.nextLine();
            if ((matcher = getMatcher(input, "^show product (\\S+)$")).find()) {

            } else if (getMatcher(input, "^sorting$").find()) {
                // TODO
            } else if (getMatcher(input, "^sorting$").find()) {
                // TODO
            } else if (getMatcher(input, "^help$").find()) {
                help();
            } else if (getMatcher(input, "^back$").find()) {
                return;
            } else ErrorProcessor.invalidInput();
        }
    }

    private void help() {
        System.out.println("show product [productId]\n" +
                "filtering\n" +
                "sorting\n" +
                "help\n" +
                "back");
    }
}
