package Control.Principal.ViewDiscountCodes;

import Control.Manager;
import Models.Account.Account;
import Models.Shop.Off.Discount;
import View.Principal.ViewDiscountCodes.ViewDiscountCodesMenu;

import java.io.IOException;

public class ViewDiscountCodesManager extends Manager {
    public ViewDiscountCodesManager(Account account) {
        super(account);
        new ViewDiscountCodesMenu(this);
    }

    public void showDiscounts() {
        for (String discountInShort : Discount.getDiscountInShort()) {
            System.out.println(discountInShort);
        }
    }

    public void deleteDiscountCode(String id) throws IOException {
        Discount.getDiscountById(id).deleteDiscount();
    }

    public String viewDiscountCode(String id) {
        return Discount.getDiscountById(id).toString();
    }

    public void editDiscountCode(String id) {
        new EditDiscountCodeManager(account, Discount.getDiscountById(id));
    }
}
