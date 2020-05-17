package View.CustomerMenus.purchase;

import Control.CustomerManagers.WrongDiscountIdException;
import Control.Manager;
import View.ErrorProcessor;

import java.util.ArrayList;

public class PaymentMenu extends PurchaseMenu {
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
            } catch (WrongDiscountIdException e) {
                ErrorProcessor.invalidDiscountId();
            }
        } else ErrorProcessor.somethingWentWrong();
    }

    private boolean canPay(String discountId) {
        try {
            return purchaseManager.canPay(discountId);
        } catch (WrongDiscountIdException e) {
            ErrorProcessor.invalidDiscountId();
            return false;
        }
    }
}
