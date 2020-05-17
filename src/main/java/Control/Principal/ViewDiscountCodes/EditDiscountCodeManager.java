package Control.Principal.ViewDiscountCodes;

import Control.Manager;
import Models.Account.Account;
import Models.DateFormat;
import Models.Shop.Off.Discount;
import View.Principal.ViewDiscountCodes.EditDiscountCodeMenu;

import java.time.LocalDateTime;

public class EditDiscountCodeManager extends Manager {

    private Discount discount;

    public EditDiscountCodeManager(Account account, Discount discount) {
        super(account);
        this.discount = discount;
        new EditDiscountCodeMenu(this);
    }

    public void editBeginningDate(String stringDate) {
        discount.setBeginningDate(LocalDateTime.parse(stringDate, DateFormat.formatter));
    }

    public void editEndingDate(String stringDate) {
        discount.setEndingDate(LocalDateTime.parse(stringDate, DateFormat.formatter));
    }

    public void editDiscountPercent(int percent) {
        discount.setDiscountPercent(percent);
    }

    public void editMaximumAmount(double maximumAmount) {
        discount.setMaximumDiscount(maximumAmount);
    }

    public void editDiscountUserCount(int count) {
        discount.setDiscountUseCount(count);
    }
}
