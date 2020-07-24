package Models.Account;

import Models.Shop.Log.SellingLog;
import Models.Shop.Off.Auction;
import Models.Shop.Product.Product;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Seller extends Account {
    private List<String> auctionsId = new ArrayList<>();
    private String companyName;
    private ArrayList<SellingLog> allLogs = new ArrayList<>();
    private Wallet wallet;

    public Seller(String username, String firstName, String lastName, String email, String phoneNumber, String password, double balance, String companyName) {
        super(username, firstName, lastName, email, phoneNumber, password, balance);
        this.companyName = companyName;
        wallet = new Wallet(balance, username, password, myBankId);
    }

    public Wallet getWallet() {
        return wallet;
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
    protected void createBankAccount(Account account) {
        try {
            Socket socket = new Socket("127.0.0.1", BANK_PORT);
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            dataOutputStream.writeUTF("create_account" + " " + firstName + " " + lastName + " " + username + " " + password + " " + password);
            dataOutputStream.flush();
            String bankId = dataInputStream.readUTF();
            this.setMyBankId(bankId);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void receiveProductMoney(Product product, String payingMethod) {
        if (payingMethod.equals("credit")) {
            wallet.addAmount((1 - Wallet.getWage()) * product.getAuctionedPrice());
        } else {
            wallet.chargeWallet((1 - Wallet.getWage()) * product.getAuctionedPrice(), Principal.getTheBankId());
        }
    }
}
