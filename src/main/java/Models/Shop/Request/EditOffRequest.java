package Models.Shop.Request;

import Models.Account.Seller;
import Models.Shop.Off.Auction;

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
        deleteRequest(this);
    }

    @Override
    public void accept() {
        auction.setStatus(Auction.AuctionStatus.CONFIRMED);
    }

    @Override
    public void decline() {
        deleteRequest(this);
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
