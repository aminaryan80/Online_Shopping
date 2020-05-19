package View.CustomerMenus.purchase;

import Control.CustomerManagers.PurchaseManager;
import Control.Manager;
import View.ErrorProcessor;
import View.Menu;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PaymentMenu extends Menu {
    private PurchaseManager purchaseManager = (PurchaseManager) manager;
    public static boolean purchaseWasSuccessful = false;
    public PaymentMenu(Manager manager) {
        super(manager);
        pay();
    }

    private void pay() {
        if (canPay(getDiscountCode())) {
            ArrayList<String> info = getInfo();
            pay(info);
        } else ErrorProcessor.somethingWentWrong();
    }

    @NotNull
    private ArrayList<String> getInfo() {
        ArrayList<String> receiverInformation = new ArrayList<>();
        receiverInformation.add(ReceiverInformationMenu.getAddress());
        receiverInformation.add(ReceiverInformationMenu.getPhoneNum());
        return receiverInformation;
    }

    private void pay(ArrayList<String> info) {
        try {
            purchaseManager.pay(info, getDiscountCode());
            purchaseWasSuccessful = true;
            System.out.println("Successfully purchased!");
        } catch (PurchaseManager.WrongDiscountIdException e) {
            ErrorProcessor.invalidDiscountId();
        }
    }

    private String getDiscountCode() {
        return DiscountCodeMenu.getDiscountCode();
    }

    private boolean canPay(String discountId) {
        try {
            if (purchaseManager.canPay(discountId)) return true;
            else ErrorProcessor.notEnoughMoney();
        } catch (PurchaseManager.WrongDiscountIdException | PurchaseManager.UsedDiscountIdException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }
}
