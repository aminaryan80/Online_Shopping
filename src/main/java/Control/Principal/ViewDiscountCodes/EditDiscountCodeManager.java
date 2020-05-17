package Control.Principal.ViewDiscountCodes;

import Control.Manager;
import Models.Account.Account;
import Models.Shop.Off.Discount;
import View.Principal.ViewDiscountCodes.EditDiscountCodeMenu;

public class EditDiscountCodeManager extends Manager {

    private Discount discount;

    public EditDiscountCodeManager(Account account, Discount discount) {
        super(account);
        this.discount = discount;
        new EditDiscountCodeMenu(this);
    }

    public void editBeginningDate(String stringDate) {
        discount.setBeginningDate(parseDate(stringDate));
    }

    public void editEndingDate(String stringDate) {
        discount.setEndingDate(parseDate(stringDate));
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
