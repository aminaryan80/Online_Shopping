package Models.Shop.Log;

import java.util.Date;

public class BuyingLog extends Log {
    private double codeDiscountAmount;
    private String sellerName;

    public BuyingLog(String id, Date date, double paymentAmount, String address, String phoneNumber, String customerName, boolean isReceived, double codeDiscountAmount, String sellerName) {
        super(id, date, paymentAmount, address, phoneNumber, customerName, isReceived);
        this.codeDiscountAmount = codeDiscountAmount;
        this.sellerName = sellerName;
    }

    @Override
    public String viewLogInShort() {
        return id + date + paymentAmount;
    }

    @Override
    public String toString() {
        return "BuyingLog{" +
                "codeDiscountAmount=" + codeDiscountAmount +
                ", sellerName='" + sellerName + '\'' +
                ", id='" + id + '\'' +
                ", date=" + date +
                ", paymentAmount=" + paymentAmount +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", products=" + products +
                ", customerName='" + customerName + '\'' +
                ", isReceived=" + isReceived +
                '}';
    }
}
