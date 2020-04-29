package View.CustomerMenus;

import Control.Manager;

public class ReceiverInformationMenu extends PurchaseMenu{

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
        System.out.println("type \"confirm\" to confirm the information\n" +
                " \"change\" to enter information again\n" +
                " \"back\" to exit this menu");
        String option = scanner.nextLine();
        if(option.equals("confirm")){
            new DiscountCodeMenu(manager);
        } else if(option.equals("change")) {
            ReceiveInformation();
        } else return;
    }

    public static String getAddress() {
        return address;
    }

    public static String getPhoneNum() {
        return phoneNum;
    }
}
