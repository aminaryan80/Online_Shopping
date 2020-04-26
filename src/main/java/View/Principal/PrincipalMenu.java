package View.Principal;

import Control.Manager;
import View.MainMenu;

import java.util.regex.Matcher;

public class PrincipalMenu extends MainMenu {

    public PrincipalMenu(Manager manager) {
        super(manager);
        principalMenu();
    }

    public void principalMenu() {
        while (true) {
            Matcher matcher;
            String input = scanner.nextLine();
            if (getMatcher(input, "^view personal info$").find()) {

            } else if ((matcher = getMatcher(input, "^edit (\\S+)$")).find()) {

            } else if (getMatcher(input, "^manage users$").find()) {

            } else if (getMatcher(input, "^manage all products$").find()) {

            } else if (getMatcher(input, "^create discount code$").find()) {

            } else if (getMatcher(input, "^view discount codes$").find()) {

            } else if (getMatcher(input, "^manage requests$").find()) {

            } else if (getMatcher(input, "^manage categories$").find()) {

            }
        }
    }

    private void manageUsers() {

    }

    private void manageAllProducts() {

    }

    private void createDiscountCode() {

    }

    private void viewDiscountCodes() {
        // prints all discounts

    }

    private void manageRequests() {

    }

    private void manageCategories() {

    }
}
