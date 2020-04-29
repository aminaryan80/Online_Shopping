package Control.Principal.ViewDiscountCodes;

import Control.Manager;
import Models.Account.Account;
import Models.Shop.Discount;
import View.Principal.ViewDiscountCodes.ViewDiscountCodesMenu;

public class ViewDiscountCodesManager extends Manager {
    public ViewDiscountCodesManager(Account account) {
        super(account);
        new ViewDiscountCodesMenu(this);
    }

    public void deleteDiscountCode(String id) {
        Discount.deleteDiscount(Discount.getDiscountById(id));
    }

    public void viewDiscountCode(String id) {
        System.out.println(Discount.getDiscountById(id).toString());
    }

    public void editDiscountCode(String id) {
        new EditDiscountCodeManager(account, Discount.getDiscountById(id));
    }
}
