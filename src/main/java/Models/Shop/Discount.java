package Models.Shop;

import Models.Account.Customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Discount {
    private static ArrayList<Discount> allDiscounts = new ArrayList<>();
    private String id;
    private Date beginningDate;
    private Date endingDate;
    private int discountPercent;
    private double maximumDiscount;
    private int discountUseCount;
    private List<Customer> allCustomers;

    public Discount(String id, Date beginningDate, Date endingDate, int discountPercent, double maximumDiscount, int discountUseCount, List<Customer> allCustomers) {
        this.id = id;
        this.beginningDate = beginningDate;
        this.endingDate = endingDate;
        this.discountPercent = discountPercent;
        this.maximumDiscount = maximumDiscount;
        this.discountUseCount = discountUseCount;
        this.allCustomers = allCustomers;
    }

    public static boolean hasDiscountWithId(String id) {
        return true;
    }

    public static Discount getDiscountById(String id) {
        return null;
    }

    public static void deleteDiscount(Discount discount) {

    }

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

    public static ArrayList<String> viewDiscountInShort() {
        return null;
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
