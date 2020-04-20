package Models.Account;

import Models.Shop.Auction;
import Models.Shop.Product;
import Models.Shop.SellingLog;

import java.util.ArrayList;
import java.util.List;

public class Seller extends Account {
    private String companyName;
    private ArrayList<SellingLog> allLogs;
    private List<Auction> auctions = new ArrayList<>();

    public Seller(String username, String firstName, String lastName, String email, String phoneNumber, String password, double balance, String companyName) {
        super(username, firstName, lastName, email, phoneNumber, password, balance);
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public ArrayList<String> viewOffsInShort() {
        return null;
    }

    public boolean hasAuctionWithId(String id) {
return true;
    }

    public boolean hasProductInAuctions(Product product) {
return true;
    }

    public Auction getAuctionById(String id) {
        return null;
    }

    public void addAuction(Auction auction) {

    }

    @Override
    public String toString() {
//        return super.toString() + "Seller{" +
//                "companyName='" + companyName + '\'' +
//                '}';
        return null;
    }
}
