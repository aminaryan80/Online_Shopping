package Models.Account;

import Models.Shop.Auction;
import Models.Shop.Product;
import Models.Shop.SellingLog;

import java.util.ArrayList;
import java.util.List;

public class Seller extends Account {
    private String companyName;
    private ArrayList<SellingLog> allLogs = new ArrayList<SellingLog>();
    private List<Auction> auctions = new ArrayList<Auction>();
    private List<String> auctionsId = new ArrayList<>();

    public Seller(String username, String firstName, String lastName, String email, String phoneNumber, String password, double balance, String companyName) {
        super(username, firstName, lastName, email, phoneNumber, password, balance);
        this.companyName = companyName;
    }

    public ArrayList<SellingLog> getAllLogs() {
        return allLogs;
    }

    public String getCompanyName() {
        return companyName;
    }

    public ArrayList<String> viewOffsInShort() {
        ArrayList<String> offsNames = new ArrayList<String>();
        for (Auction auction : auctions) {
            offsNames.add("" + auction.getId() + " : " + auction.getDiscountAmount());
        }
        return offsNames;
    }

    public boolean hasAuctionWithId(String id) {
        for (Auction auction : auctions) {
            if (auction.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasProductInAuctions(Product product) {
return true;
    }

    public Auction getAuctionById(String id) {
        for (Auction auction : auctions) {
            if (auction.getId().equals(id)) {
                return auction;
            }
        }
        return null;
    }

    public void addAuction(Auction auction) {
        auctions.add(auction);
        auctionsId.add(auction.getId());
    }

    @Override
    protected void loadReference() {
        auctions = new ArrayList<>();
        for (String auctionId : auctionsId) {
            auctions.add(Auction.getAuctionById(auctionId));
        }
    }

    @Override
    public String toString() {
//        return super.toString() + "Seller{" +
//                "companyName='" + companyName + '\'' +
//                '}';
        return null;
    }
}
