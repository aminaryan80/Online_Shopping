package Models.Shop.Request;

import Models.Account.Seller;
import Models.Shop.Off.Auction;

public class AddOffRequest extends Request {
    private Auction auction;
    private String auctionId;

    public AddOffRequest(Seller seller, Auction auction) {
        super(seller);
        this.type = RequestType.ADD_OFF;
        this.auction = auction;
        this.auctionId = auction.getId();
    }

    public void accept() {
        auction.setStatus(Auction.AuctionStatus.CONFIRMED);
        deleteRequest(this);
    }

    @Override
    public void decline() {
        deleteRequest(this);
    }

    @Override
    protected void loadReference() {
        auction = Auction.getAuctionById(auctionId);
    }

    @Override
    public String toString() {
        return "AddOffRequest : " +
                "\nauction = " + auction +
                "\nid = '" + id + '\'' +
                "\nseller = " + seller +
                "\ntype = " + type;
    }
}
