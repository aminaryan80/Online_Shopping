package View.CustomerMenus.purchase;

import Control.CustomerManagers.PurchaseManager;
import Control.Manager;
import View.ErrorProcessor;
import View.Menu;

public class DiscountCodeMenu extends Menu {
    private PurchaseManager purchaseManager = (PurchaseManager) manager;
    private static String discountCode = null;

    public DiscountCodeMenu(Manager manager) {
        super(manager);
        checkDiscountCode();
    }

    private void checkDiscountCode() {
        String input;
            System.out.println(" Enter discount code ID\n" +
                    " type \"no\" to pass\n" +
                    " type \"back\" to go to last menu");
            input = scanner.nextLine();
            if (input.matches("(?i)back")) {
                return;
            } else if (input.matches("(?i)no")) {
                new PaymentMenu(manager);
                if(!PaymentMenu.purchaseWasSuccessful)
                checkDiscountCode();
            } else {
                if (isDiscountCodeValid(input)) {
                    discountCode = input;
                    new PaymentMenu(manager);
                    if(!PaymentMenu.purchaseWasSuccessful)
                    checkDiscountCode();
                } else {
                    ErrorProcessor.invalidDiscountId();
                    checkDiscountCode();
                }
            }

    }

    private boolean isDiscountCodeValid(String input){
        return purchaseManager.isDiscountCodeValid(input);
    }

    public static String getDiscountCode() {
        return discountCode;
    }
}
