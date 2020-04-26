package Models.Shop;

import Control.Manager;
import Control.Seller.OffsManager;
import Models.Account.Seller;

import java.util.ArrayList;
import java.util.Date;

public class AddOffRequest extends Request{
    private Auction auction;

    public AddOffRequest(String id, Seller seller, Manager manager, Auction auction) {
        super(id, seller, manager);
        this.type = RequestType.ADD_OFF;
        this.auction = auction;
    }

    public void accept() {
        auction.setStatus(Auction.AuctionStatus.CONFIRMED);
    }

    @Override
    public String toString() {
        return "AddOffRequest{" +
                "auction=" + auction +
                ", id='" + id + '\'' +
                ", seller=" + seller +
                ", type=" + type +
                '}';
    }
}
