package Models.Shop;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Auction {
    private static List<Auction> allAuctions = new ArrayList<>();
    private String id;
    private List<Product> products;
    private AuctionStatus status;
    private Date beginningDate;
    private Date endingDate;
    private double discountAmount;

    public Auction(String id, List<Product> products, Date beginningDate, Date endingDate, double discountAmount) {
        this.id = id;
        this.products = products;
        this.status = AuctionStatus.UNDER_REVIEW_FOR_CONSTRUCTION;
        this.beginningDate = beginningDate;
        this.endingDate = endingDate;
        this.discountAmount = discountAmount;
        allAuctions.add(this);
    }

    public static List<Auction> getAllAuctions() {
        return allAuctions;
    }

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

    public String viewInShort() {
        return null;
    }

    public static AuctionStatus parseAuctionStatus(String status) {
        if (status.equals("UNDER_REVIEW_FOR_CONSTRUCTION")) {
            return AuctionStatus.UNDER_REVIEW_FOR_CONSTRUCTION;
        } else if (status.equals("UNDER_REVIEW_FOR_EDITING")) {
            return AuctionStatus.UNDER_REVIEW_FOR_EDITING;
        } else if (status.equals("CONFIRMED")) {
            return AuctionStatus.CONFIRMED;
        } else {
            return null;
        }
    }

    public AuctionStatus getStatus() {
        return status;
    }

    public Date getBeginningDate() {
        return beginningDate;
    }

    public Date getEndingDate() {
        return endingDate;
    }

    public enum AuctionStatus {
        UNDER_REVIEW_FOR_CONSTRUCTION, UNDER_REVIEW_FOR_EDITING, CONFIRMED;
    }
}

