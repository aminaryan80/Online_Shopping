package Models.Account;

import Models.Shop.Log.SellingLog;
import Models.Shop.Off.Auction;
import Models.Shop.Product.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Seller extends Account {
    private final String[] changeableFields = {"password", "email", "firstName", "lastName", "phoneNumber", "companyName"};
    private String companyName;
    private ArrayList<SellingLog> allLogs = new ArrayList<SellingLog>();
//    private List<Auction> auctions = new ArrayList<Auction>();
    private static List<String> auctionsId = new ArrayList<String>();

    public Seller(String username, String firstName, String lastName, String email, String phoneNumber, String password, double balance, String companyName) {
        super(username, firstName, lastName, email, phoneNumber, password, balance);
        this.companyName = companyName;
    }

    public ArrayList<String> getChangeableFields() {
        return new ArrayList<>(Arrays.asList(changeableFields));
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

    public ArrayList<String> viewOffsInShort() {
        ArrayList<String> offsNames = new ArrayList<>();
        for (Auction auction : getAuctions()) {
            offsNames.add("" + auction.getId() + " : " + auction.getDiscountAmount());
        }
        return offsNames;
    }

    public boolean hasAuctionWithId(String id) {
        for (Auction auction : getAuctions()) {
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

    protected static ArrayList<Auction> getAuctions() {
        ArrayList<Auction> auctions = new ArrayList<>();
        for (String auctionId : auctionsId) {
            auctions.add(Auction.getAuctionById(auctionId));
        }
        return auctions;
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
