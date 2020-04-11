package Models.Shop;

public class SellingLog extends Log {
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
