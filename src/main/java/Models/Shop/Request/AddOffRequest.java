package Models.Shop.Request;

import Models.Account.Seller;
import Models.Shop.Off.Auction;

import java.io.IOException;

public class AddOffRequest extends Request {
    private Auction auction;

    public AddOffRequest(Seller seller, Auction auction) {
        super(seller);
        this.type = RequestType.ADD_OFF;
        this.auction = auction;
    }

    public void accept() throws IOException {
        auction.setStatus(Auction.AuctionStatus.CONFIRMED);
        deleteRequest(this, "add off requests");
        Auction.addAuction(auction);
    }

    @Override
    public void decline() throws IOException {
        deleteRequest(this, "add off requests");
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
