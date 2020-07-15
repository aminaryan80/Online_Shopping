package Models.Account;

import Models.Shop.Log.SellingLog;
import Models.Shop.Off.Auction;
import Models.Shop.Product.Product;

import java.util.ArrayList;
import java.util.List;

public class Seller extends Account {
    private List<String> auctionsId = new ArrayList<>();
    private String companyName;
    private ArrayList<SellingLog> allLogs = new ArrayList<>();

    public Seller(String username, String firstName, String lastName, String email, String phoneNumber, String password, double balance, String companyName) {
        super(username, firstName, lastName, email, phoneNumber, password, balance);
        this.companyName = companyName;
    }

    public ArrayList<Auction> getAuctions() {
        ArrayList<Auction> auctions = new ArrayList<>();
        for (String auctionId : auctionsId) {
            auctions.add(Auction.getAuctionById(auctionId));
        }
        return auctions;
    }

    public ArrayList<SellingLog> getAllLogs() {
        return allLogs;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    public Auction getAuctionById(String id) {
        for (Auction auction : getAuctions()) {
            if (auction.getId().equals(id)) {
                return auction;
            }
        }
        return null;
    }

    public void addAuction(Auction auction) {
        auctionsId.add(auction.getId());
    }

    @Override
    public String toString() {
        return username + " : " +
                "\nfirstName = " + firstName +
                "\nlastName = " + lastName +
                "\nemail = " + email +
                "\nphoneNumber = " + phoneNumber +
                "\npassword = " + password +
                "\nbalance = " + balance +
                "\ncompany name = " + companyName;
    }

    public void addLog(SellingLog log) {
        allLogs.add(log);
    }

    public void receiveProductMoney(Product product) {
        balance += product.getAuctionedPrice();
    }
}
