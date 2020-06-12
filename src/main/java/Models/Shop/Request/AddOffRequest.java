package Models.Shop.Request;

import Models.Account.Seller;
import Models.Shop.Off.Auction;

import java.io.IOException;

public class AddOffRequest extends Request {
 //   private Auction auction;
    private String auctionId;

    public AddOffRequest(Seller seller, Auction auction) {
        super(seller);
        this.type = RequestType.ADD_OFF;
//        this.auction = auction;
        this.auctionId = auction.getId();
    }

    public void accept() throws IOException {
        getAuction().setStatus(Auction.AuctionStatus.CONFIRMED);
        deleteRequest(this, "add off requests");
        Auction.addAuction(getAuction());
    }

    public Auction getAuction() {
        return Auction.getAuctionById(auctionId);
    }

    @Override
    public void decline() throws IOException {
        deleteRequest(this, "add off requests");
    }
//
//    @Override
//    protected void loadReference() {
//        auction = Auction.getAuctionById(auctionId);
//    }

    @Override
    public String toString() {
        return "AddOffRequest : " +
                "\nauction = " + getAuction() +
                "\nid = '" + id + '\'' +
                "\nseller = " + seller +
                "\ntype = " + type;
    }
}
