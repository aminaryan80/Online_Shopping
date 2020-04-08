package Models.Shop;

import java.util.Date;
import java.util.List;

public class Log {
    //TODO change name to receipt
    private String id;
    private Date date;
    private double paymentAmount;
    private double discountAmount;
    private List<Product> products;
    private String customerName;
    private String sellerName;
    private boolean isReceived;
}
