package Models.Shop.Request;

import Models.Account.Seller;
import Models.Shop.Off.Auction;

import java.io.IOException;

public class EditOffRequest extends Request {
    private Auction auction;
    private String auctionId;

    public EditOffRequest(Seller seller, Auction auction) {
        super(seller);
        this.type = RequestType.EDIT_OFF;
        this.auction = auction;
        this.auctionId = auction.getId();
    }

    @Override
    protected void loadReference() {
        auction = Auction.getAuctionById(auctionId);
    }

    @Override
    public void accept() throws IOException {
        auction.setStatus(Auction.AuctionStatus.CONFIRMED);
        deleteRequest(this, "edit off requests");
    }

    @Override
    public void decline() throws IOException {
        deleteRequest(this, "edit off requests");
    }

    @Override
    public String toString() {
        return "EditOffRequest : " +
                "\nauction=" + auction +
                "\nid='" + id + '\'' +
                "\nseller=" + seller +
                "\ntype=" + type;
    }
}
