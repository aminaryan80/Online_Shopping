package Models.Shop.Log;

import Models.Shop.Product.Product;

import java.util.Date;
import java.util.List;

public abstract class Log {
    //TODO change name to receipt
    protected String id;
    protected Date date;
    protected double paymentAmount;
    protected String address;
    protected String phoneNumber;
    protected List<Product> products;
    protected String customerName;
    protected boolean isReceived;

    public Log(String id, Date date, double paymentAmount, String address, String phoneNumber, String customerName, boolean isReceived) {
        this.id = id;
        this.date = date;
        this.paymentAmount = paymentAmount;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.customerName = customerName;
        this.isReceived = isReceived;
    }

    public abstract String viewLogInShort();

    @Override
    public abstract String toString();

    public boolean hasProductById(String id) {
return true;

    }

    public String getId() {
        return id;
    }
}
