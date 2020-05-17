package Models.Shop.Log;

import Models.Shop.Product.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class BuyingLog extends Log {

    public BuyingLog(String id, LocalDateTime date, double moneyPaid, double amountOfDiscountCodeApplied, ArrayList<Product> products,
                     String sellerName, String address, String phoneNumber, Status ReceivingStatus) {
        super(id, date, moneyPaid, amountOfDiscountCodeApplied, products, sellerName, address, phoneNumber, ReceivingStatus);
    }

    @Override
    public String viewLogInShort() {
        return id + date + money;
    }

    @Override
    public String toString() {
        return "BuyingLog{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", money=" + money +
                ", amount=" + amount +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", products=" + products +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}
