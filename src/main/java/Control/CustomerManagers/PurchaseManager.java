package Control.CustomerManagers;

import Control.CustomerManager;
import Models.Account.Account;
import Models.Shop.Discount;
import View.CustomerMenus.PurchaseMenu;

public class PurchaseManager extends CustomerManager{
    public PurchaseManager(Account account) {
        super(account);
        this.menu = new PurchaseMenu(this);
    }

    public Discount getDiscountById(String id) {
        return Discount.getDiscountById(id);
    }

    public boolean canPay() {
        return customer.getCart().getTotalPrice()<customer.getBalance();
    }
}
