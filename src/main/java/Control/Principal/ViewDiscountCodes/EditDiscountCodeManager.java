package Control.Principal.ViewDiscountCodes;

import Control.Manager;
import Models.Account.Account;
import Models.Shop.Off.Discount;
import View.Principal.ViewDiscountCodes.EditDiscountCodeMenu;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EditDiscountCodeManager extends Manager {

    private Discount discount;

    public EditDiscountCodeManager(Account account, Discount discount) {
        super(account);
        this.discount = discount;
        new EditDiscountCodeMenu(this);
    }

    public void editBeginningDate(String stringDate) {
        discount.setBeginningDate(LocalDate.parse(stringDate));
    }

    public void editEndingDate(String stringDate) {
        discount.setEndingDate(LocalDate.parse(stringDate));
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
