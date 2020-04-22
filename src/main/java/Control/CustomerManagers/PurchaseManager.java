package Control.CustomerManagers;

import Control.CustomerManager;
import Models.Account.Account;
import Models.Shop.Discount;
import View.CustomerMenus.PurchaseMenu;

import java.util.ArrayList;

public class PurchaseManager extends CustomerManager{
    public PurchaseManager(Account account) {
        super(account);
        this.menu = new PurchaseMenu(this);
    }

    public Discount getDiscountById(String id) {
        return Discount.getDiscountById(id);
    }

    public boolean canPay(String discountId) {
        Discount discount = Discount.getDiscountById(discountId);
        return customer.getCart().getTotalPrice(discount)<customer.getBalance();
    }


    // purchase
    public void pay(ArrayList<String> receiverInformation, String discountCode) {

    }

    private double getPaymentAmountDiscountApplied() {
        return 1;
    }
}
