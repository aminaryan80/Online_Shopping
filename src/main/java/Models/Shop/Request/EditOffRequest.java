package Models.Shop.Request;

import Models.Account.Seller;
import Models.Shop.Off.Auction;

import java.io.IOException;

public class EditOffRequest extends Request {
    private String auctionId;

    public EditOffRequest(Seller seller, Auction auction) {
        super(seller);
        this.type = RequestType.EDIT_OFF;
        this.auctionId = auction.getId();
    }

    public Auction getAuction() {
        return Auction.getAuctionById(auctionId);
    }

    @Override
    public void accept() throws IOException {
        getAuction().setStatus(Auction.AuctionStatus.CONFIRMED);
        deleteRequest(this, "edit off requests");
    }

    @Override
    public void decline() throws IOException {
        deleteRequest(this, "edit off requests");
    }

    @Override
    public String toString() {
        return "EditOffRequest : " +
                "\nauction=" + getAuction() +
                "\nid='" + id + '\'' +
                "\nseller=" + seller +
                "\ntype=" + type;
    }
}
