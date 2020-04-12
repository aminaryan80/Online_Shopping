package Models.Account;

import Models.Shop.Auction;
import Models.Shop.Product;
import Models.Shop.SellingLog;

import java.util.ArrayList;
import java.util.List;

public class Seller extends Account {
    public Seller(String username, String firstName, String lastName, String email, String phoneNumber, String password, double balance, String companyName) {
        super(username, firstName, lastName, email, phoneNumber, password, balance);
        this.companyName = companyName;
    }

    private String companyName;
    private ArrayList<SellingLog> allLogs;
    private List<Auction> auctions = new ArrayList<Auction>();

    public String getCompanyName() {
        return companyName;
    }

    public ArrayList<String> viewOffsInShort() {

    }

    public boolean hasAuctionWithId(String id) {

    }

    public boolean hasProductInAuctions(Product product) {

    }

    public Auction getAuctionById(String id) {

    }

    public void addAuction(Auction auction){

    }

    @Override
    public String toString() {
//        return super.toString() + "Seller{" +
//                "companyName='" + companyName + '\'' +
//                '}';
    }
}
