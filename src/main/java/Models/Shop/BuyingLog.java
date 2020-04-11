package Models.Shop;

public class BuyingLog extends Log {
    private double codeDiscountAmount;
    private String sellerName;

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
                ", Address='" + Address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", products=" + products +
                ", customerName='" + customerName + '\'' +
                ", isReceived=" + isReceived +
                '}';
    }
}
