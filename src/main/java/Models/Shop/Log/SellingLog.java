package Models.Shop.Log;

import Models.Shop.Product.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class SellingLog extends Log {

    public SellingLog(LocalDateTime date, double moneyReceived, double amountOfAuctionApplied, ArrayList<Product> soldProducts,
                      String buyerName, String address, String phoneNumber, Status SendingStatus) {
        super(date, moneyReceived, amountOfAuctionApplied, soldProducts, buyerName, address, phoneNumber, SendingStatus);
    }


    @Override
    public String toString() {
        return "SellingLog{" +
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
