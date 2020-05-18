package View.CustomerMenus.purchase;

import Control.CustomerManagers.PurchaseManager;
import Control.Manager;
import View.ErrorProcessor;
import View.Menu;

import java.util.ArrayList;

public class PaymentMenu extends Menu {
    private PurchaseManager purchaseManager = (PurchaseManager) manager;
    public static boolean purchaseWasSuccessful = false;
    public PaymentMenu(Manager manager) {
        super(manager);
        pay();
    }

    private void pay() {
        if (canPay(DiscountCodeMenu.getDiscountCode())) {
            ArrayList<String> receiverInformation = new ArrayList<>();
            receiverInformation.add(ReceiverInformationMenu.getAddress());
            receiverInformation.add(ReceiverInformationMenu.getPhoneNum());
            try {
                purchaseManager.pay(receiverInformation, DiscountCodeMenu.getDiscountCode());
                purchaseWasSuccessful = true;
                System.out.println("Successfully purchased!");
            } catch (PurchaseManager.WrongDiscountIdException e) {
                ErrorProcessor.invalidDiscountId();
            }
        } else ErrorProcessor.somethingWentWrong();
    }

    private boolean canPay(String discountId) {
        try {
            return purchaseManager.canPay(discountId);
        } catch (PurchaseManager.WrongDiscountIdException| PurchaseManager.UsedDiscountIdException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
