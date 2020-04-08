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

    private enum AuctionStatus {
        UNDER_REVIEW_FOR_CONSTRUCTION, UNDER_REVIEW_FOR_EDITING, CONFIRMED;
    }
}

