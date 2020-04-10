package Models.Shop;

import Models.Account.Customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Discount {
    private static ArrayList<Discount> allDiscounts = new ArrayList<Discount>();
    private String id;
    private Date beginningDate;
    private Date endingDate;
    private int discountPercent;
    private double maximumDiscount;
    private int discountUseCount;
    private List<Customer> allCustomers;

    public void setBeginningDate(Date beginningDate) {
        this.beginningDate = beginningDate;
    }

    public void setEndingDate(Date endingDate) {
        this.endingDate = endingDate;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public void setMaximumDiscount(double maximumDiscount) {
        this.maximumDiscount = maximumDiscount;
    }

    public void setDiscountUseCount(int discountUseCount) {
        this.discountUseCount = discountUseCount;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public ArrayList<String> viewDiscountInShort() {

    }
    public boolean hasDiscountWithId(String id) {

    }

    public Discount getDiscountById(String id) {

    }

    public static void deleteDiscount(Discount discount) {

    }

    @Override
    public String toString() {
        return "Discount{" +
                "id='" + id + '\'' +
                ", beginningDate=" + beginningDate +
                ", endingDate=" + endingDate +
                ", discountPercent=" + discountPercent +
                ", maximumDiscount=" + maximumDiscount +
                ", discountUseCount=" + discountUseCount +
                ", allCustomers=" + allCustomers +
                '}';
    }
}
