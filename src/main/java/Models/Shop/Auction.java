package Models.Shop;

import java.util.Date;
import java.util.List;

public class Auction {
    private String id;
    private List<Product> products;
    private AuctionStatus status;
    private Date beginningDate;
    private Date endingDate;
    private double discountAmount;

    public String getId() {
        return id;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setBeginningDate(Date beginningDate) {
        this.beginningDate = beginningDate;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public void setEndingDate(Date endingDate) {
        this.endingDate = endingDate;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setStatus(AuctionStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Auction{" +
                "id='" + id + '\'' +
                ", products=" + products +
                ", status=" + status +
                ", beginningDate=" + beginningDate +
                ", endingDate=" + endingDate +
                ", discountAmount=" + discountAmount +
                '}';
    }

    private enum AuctionStatus {
        UNDER_REVIEW_FOR_CONSTRUCTION, UNDER_REVIEW_FOR_EDITING, CONFIRMED;
    }
}

