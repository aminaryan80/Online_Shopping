package Models.Shop;

import Models.Account.Customer;

import java.util.Date;
import java.util.List;

public class Discount {
    private String id;
    private Date beginningDate;
    private Date endingDate;
    private int discountPercent;
    private double maximumDiscount;
    private int discountUseCount;
    private List<Customer> allCustomers;

}
