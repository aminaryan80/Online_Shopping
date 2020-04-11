package Models.Shop;

import java.util.Date;
import java.util.List;

public class Log {
    //TODO change name to receipt
    protected String id;
    protected Date date;
    protected double paymentAmount;
    protected String address;
    protected String phoneNumber;
    protected List<Product> products;
    protected String customerName;
    protected boolean isReceived;

    public abstract String viewLogInShort();
    @Override
    public abstract String toString();

    public boolean hasProductById(String id) {

    }
}
