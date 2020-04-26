package Models.Shop;

import Control.Manager;
import Control.Seller.OffsManager;
import Models.Account.Seller;

public class EditOffRequest extends Request {
    private Auction auction;

    public EditOffRequest(String id, Seller seller, Manager manager, Auction auction) {
        super(id, seller, manager);
        this.type = RequestType.EDIT_OFF;
        this.auction = auction;
    }

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
