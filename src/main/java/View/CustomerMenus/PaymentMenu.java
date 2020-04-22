package View.CustomerMenus;

import Control.Manager;
import View.ErrorProcessor;

import java.util.ArrayList;

public class PaymentMenu extends PurchaseMenu {
    public PaymentMenu(Manager manager) {
        super(manager);
        pay();
    }

    private void pay() {
        if(canPay(DiscountCodeMenu.getDiscountCode())){
            ArrayList<String> receiverInformation = new ArrayList<>();
            receiverInformation.add(ReceiverInformationMenu.getAddress());
            receiverInformation.add(ReceiverInformationMenu.getPhoneNum());
            purchaseManager.pay(receiverInformation,DiscountCodeMenu.getDiscountCode());
        } else ErrorProcessor.notEnoughMoney();
    }

    private boolean canPay(String discountId) {
       return purchaseManager.canPay(discountId);
    }
}
