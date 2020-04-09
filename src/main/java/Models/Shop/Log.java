package Models.Shop;

import java.util.Date;
import java.util.List;

public class Log {
    //TODO change name to receipt
    protected String id;
    protected Date date;
    protected double paymentAmount;
    protected List<Product> products;
    protected String customerName;
    protected boolean isReceived;
}
