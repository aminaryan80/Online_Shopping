package Models.Account;

import Models.Shop.Log.SellingLog;
import Models.Shop.Off.Auction;
import Models.Shop.Product.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Seller extends Account {
    private final String[] changeableFields = {"password", "email", "firstname", "lastname", "phone number", "company name"};
    private String companyName;
    private ArrayList<SellingLog> allLogs = new ArrayList<SellingLog>();
    private List<Auction> auctions = new ArrayList<Auction>();
    private List<String> auctionsId = new ArrayList<>();

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
        return "{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                '}';
    }

    public void addLog(SellingLog log) {
        allLogs.add(log);
    }

    public void receiveProductMoney(Product product) {
        balance += product.getAuctionedPrice();
    }
}
