package Client.Control.Principal.ViewDiscountCodes;

import Client.Control.Manager;
import Models.Account.Account;
import Models.Shop.Off.Discount;

import java.time.LocalDate;

public class EditDiscountCodeManager extends Manager {

    private Discount discount;

    public EditDiscountCodeManager(Account account) {
        super(account);
    }

    public EditDiscountCodeManager(Account account, Discount discount) {
        super(account);
        this.discount = discount;
        loadFxml(Addresses.EDIT_DISCOUNTS);
    }

    public int editDiscount(String discountId, String field, String newValue) {
        if (Discount.hasDiscountWithId(discountId)) {
            discount = Discount.getDiscountById(discountId);
            if (field.equals("BEGINNING_DATE")) {
                return editBeginningDate(newValue);
            } else if (field.equals("ENDING_DATE")) {
                return editEndingDate(newValue);
            } else if (field.equals("PERCENT")) {
                return editDiscountPercent(newValue);
            } else if (field.equals("MAXIMUM_AMOUNT")) {
                return editMaximumAmount(newValue);
            } else if (field.equals("USE_COUNT")) {
                return editDiscountUserCount(newValue);
            }
        }
        return 1;
    }

    public int editBeginningDate(String stringDate) {
        if (checkDate(stringDate)) {
            discount.setBeginningDate(LocalDate.parse(stringDate));
            return 0;
        }
        return 1;
    }

    public int editEndingDate(String stringDate) {
        if (checkDate(stringDate)) {
            discount.setEndingDate(LocalDate.parse(stringDate));
            return 0;
        }
        return 1;
    }

    public int editDiscountPercent(String percent) {
        if (checkPercent(percent)) {
            discount.setDiscountPercent(Integer.parseInt(percent));
            return 0;
        }
        return 1;
    }

    public int editMaximumAmount(String maximumAmount) {
        if (checkNumber(maximumAmount)) {
            discount.setMaximumDiscount(Double.parseDouble(maximumAmount));
            return 0;
        }
        return 1;
    }

    public int editDiscountUserCount(String count) {
        if (count.matches("^\\d+$")) {
            discount.setDiscountUseCount(Integer.parseInt(count));
            return 0;
        }
        return 1;
    }
}
