package View.CustomerMenus.purchase;

import Control.CustomerManagers.PurchaseManager;
import Control.Manager;
import View.ErrorProcessor;
import View.Menu;

public class ReceiverInformationMenu extends Menu {
    private PurchaseManager purchaseManager = (PurchaseManager) manager;
    private static String address;
    private static String phoneNum;

    public ReceiverInformationMenu(Manager manager) {
        super(manager);
        receiveInformation();
    }

    private void receiveInformation() {
        System.out.println("Enter address:");
        address = scanner.nextLine();
        System.out.println("Enter phone number:");
        phoneNum = scanner.nextLine();
        if(address.matches("(?i)back") || phoneNum.matches("(?i)back")) return;
        if (!isCorrect(phoneNum)) {
            ErrorProcessor.invalidPhoneNumber();
            receiveInformation();
        } else {
            help();
            String option = scanner.nextLine();
            if (option.matches("(?i)confirm")) {
                new DiscountCodeMenu(manager);
                if(!PaymentMenu.purchaseWasSuccessful)
                receiveInformation();
            } else if (option.matches("(?i)change")) {
                receiveInformation();
            } else if (option.matches("(?i)back")) {
                return;
            } else ErrorProcessor.invalidInput();
        }
    }

    private boolean isCorrect(String phoneNum) {
    return purchaseManager.checkPhoneNumber(phoneNum);
    }

    private void help() {
        System.out.println(" \"confirm\"\t to confirm the information\n" +
                " \"change\"\t to reEnter information\n" +
                " \"back\"\t to exit this menu");
    }

    public static String getAddress() {
        return address;
    }

    public static String getPhoneNum() {
        return phoneNum;
    }
}
