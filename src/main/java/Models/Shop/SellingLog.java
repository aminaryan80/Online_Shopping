package Models.Shop;

import java.util.Date;

public class SellingLog extends Log {
    public SellingLog(String id, Date date, double paymentAmount, String address, String phoneNumber, String customerName, boolean isReceived, double auctionAmount, String buyerName) {
        super(id, date, paymentAmount, address, phoneNumber, customerName, isReceived);
        this.auctionAmount = auctionAmount;
        this.buyerName = buyerName;
    }

    private double auctionAmount;
    private String buyerName;

    @Override
    public String toString() {
        return "SellingLog{" +
                "auctionAmount=" + auctionAmount +
                ", buyerName='" + buyerName + '\'' +
                ", id='" + id + '\'' +
                ", date=" + date +
                ", paymentAmount=" + paymentAmount +
                ", Address='" + Address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", products=" + products +
                ", customerName='" + customerName + '\'' +
                ", isReceived=" + isReceived +
                '}';
    }

    @Override
    public String viewLogInShort() {

    }
}
