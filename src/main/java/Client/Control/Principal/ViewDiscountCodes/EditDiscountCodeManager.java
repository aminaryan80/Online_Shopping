package Client.Control.Principal.ViewDiscountCodes;

import Client.Control.Manager;
import Models.Account.Account;
import Models.Shop.Off.Discount;

import java.time.LocalDate;

public class EditDiscountCodeManager extends Manager {

    private Discount discount;

    public EditDiscountCodeManager(Account account, Discount discount) {
        super(account);
        this.discount = discount;
        loadFxml(Addresses.EDIT_DISCOUNTS);
    }

    public void editBeginningDate(String stringDate) {
        if (checkDate(stringDate)) {
            discount.setBeginningDate(LocalDate.parse(stringDate));
            success("Discount changed successfully.");
        } else error("Invalid date input");
    }

    public void editEndingDate(String stringDate) {
        if (checkDate(stringDate)) {
            discount.setEndingDate(LocalDate.parse(stringDate));
            success("Discount changed successfully.");
        } else error("Invalid date input");
    }

    public void editDiscountPercent(String percent) {
        if (checkPercent(percent)) {
            discount.setDiscountPercent(Integer.parseInt(percent));
            success("Discount changed successfully.");
        } else error("Invalid input");
    }

    public void editMaximumAmount(String maximumAmount) {
        if (checkNumber(maximumAmount)) {
            discount.setMaximumDiscount(Double.parseDouble(maximumAmount));
            success("Discount changed successfully.");
        } else error("Invalid input");
    }

    public void editDiscountUserCount(String count) {
        if (count.matches("^\\d+$")) {
            discount.setDiscountUseCount(Integer.parseInt(count));
            success("Discount changed successfully.");
        } else error("Invalid input");
    }
}
