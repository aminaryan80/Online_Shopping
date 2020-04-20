package Control.Principal.ViewDiscountCodes;

import Control.Manager;
import Models.Account.Account;
import Models.Shop.Discount;
import View.Principal.ViewDiscountCodes.EditDiscountCodeMenu;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditDiscountCodeManager extends Manager {

    private Discount discount;

    public EditDiscountCodeManager(Account account, Discount discount) {
        super(account);
        this.discount = discount;
        new EditDiscountCodeMenu(this);
    }

    public void editBeginningDate(String stringDate) {
        discount.setBeginningDate(editDate(stringDate));
    }

    public void editEndingDate(String stringDate) {
        discount.setEndingDate(editDate(stringDate));
    }

    private Date editDate(String stringDate) {
        DateFormat format = new SimpleDateFormat("dd-mm-yyyy");
        Date date = null;
        try {
            date = format.parse(stringDate);
        } catch (Exception ignored) {

        }
        return date;
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
