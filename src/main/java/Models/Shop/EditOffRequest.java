package Models.Shop;

import Control.Manager;
import Control.Seller.OffsManager;
import Models.Account.Seller;

public class EditOffRequest extends Request {
    private Auction auction;
    private String auctionId;

    public EditOffRequest(String id, Seller seller, Auction auction) {
        super(id, seller);
        this.type = RequestType.EDIT_OFF;
        this.auction = auction;
        this.auctionId = auction.getId();
    }

    @Override
    protected void loadReference() {
        auction = Auction.getAuctionById(auctionId);
    }

    @Override
    public void accept() {
        auction.setStatus(Auction.AuctionStatus.CONFIRMED);
    }

    @Override
    public String toString() {
        return "EditOffRequest{" +
                "auction=" + auction +
                ", id='" + id + '\'' +
                ", seller=" + seller +
                ", type=" + type +
                '}';
    }
}
