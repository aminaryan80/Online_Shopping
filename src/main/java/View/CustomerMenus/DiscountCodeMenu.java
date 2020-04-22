package View.CustomerMenus;

import Control.CustomerManagers.PurchaseManager;
import Control.Manager;
import View.ErrorProcessor;

public class DiscountCodeMenu extends PurchaseMenu {

    private static String discountCode = "";
    PurchaseManager purchaseManager = (PurchaseManager) manager;

    public DiscountCodeMenu(Manager manager) {
        super(manager);
        checkDiscountCode();
    }

    private void checkDiscountCode() {
        String input;
        while (true) {
            System.out.println("Enter discount code ID\n" +
                    "type \"no\" to pass\n" +
                    " type \"back\" to go to last menu");
            input = scanner.nextLine();
            if (input.matches("(?i)back")) {
                new ReceiverInformationMenu(manager);
                break;
            } else if (input.matches("(?i)no")) {
                new PaymentMenu(manager);
                break;
            } else {
                if (isDiscountCodeValid(input)) {
                    discountCode = input;
                    new PaymentMenu(manager);
                    break;
                } else ErrorProcessor.invalidDiscountId();
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
