package View.CustomerMenus.purchase;

import Control.Manager;
import View.ErrorProcessor;

public class ReceiverInformationMenu extends PurchaseMenu {

    private static String address;
    private static String phoneNum;

    public ReceiverInformationMenu(Manager manager) {
        super(manager);
        ReceiveInformation();
    }

    private void ReceiveInformation() {
        System.out.println("Enter address:");
        address = scanner.nextLine();
        System.out.println("Enter phone number:");
        phoneNum = scanner.nextLine();
        help();
        String option = scanner.nextLine();
        if(option.equals("confirm")){
            new DiscountCodeMenu(manager);
        } else if(option.equals("change")) {
            ReceiveInformation();
        } else if(option.matches("(?i)back")){
            return;
        } else ErrorProcessor.invalidInput();
    }

    private void help() {
        System.out.println("\"confirm\"\t to confirm the information\n" +
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
