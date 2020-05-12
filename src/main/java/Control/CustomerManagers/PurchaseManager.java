package Control.CustomerManagers;

import Models.Account.Account;
import Models.Shop.BuyingLog;
import Models.Shop.Discount;
import Models.Shop.Product;
import View.CustomerMenus.purchase.PurchaseMenu;

import java.util.ArrayList;
import java.util.Date;

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
        Discount discount = Discount.getDiscountById(discountCode);
        double paymentAmount = customer.getCart().getTotalPrice(discount);
        customer.payMoney(paymentAmount);
        ArrayList<Product> boughtProducts = customer.getCart().getProducts();
        //TODO date should be received via receiverInfo
        BuyingLog buyingLog = new BuyingLog("0", new Date(2000, 1, 1), paymentAmount,
                receiverInformation.get(0), receiverInformation.get(1), customer.getName(), false,
                customer.getCart().amountOfDiscount(customer.getCart().getTotalAmountWithoutDiscount(), discount),
                boughtProducts.get(0).getSeller().getName());
        customer.addLog(buyingLog);
    }

    private double getPaymentAmountDiscountApplied() {
        return 1;
    }
}
