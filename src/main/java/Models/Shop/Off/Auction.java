package Models.Shop.Off;

import Models.Address;
import Models.Gson;
import Models.Shop.Product.Product;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Auction {
    private static List<Auction> allAuctions = new ArrayList<>();
    private String id;
    private List<Product> products;
    private List<String> productsIds;
    private AuctionStatus status;
    private Date beginningDate;
    private Date endingDate;
    private double discountAmount;

    public Auction(String id, List<Product> products, Date beginningDate, Date endingDate, double discountAmount) {
        this.id = id;
        this.products = products;
        this.productsIds = new ArrayList<>();
        for (Product product : products) {
            productsIds.add(product.getId());
        }
        this.status = AuctionStatus.UNDER_REVIEW_FOR_CONSTRUCTION;
        this.beginningDate = beginningDate;
        this.endingDate = endingDate;
        this.discountAmount = discountAmount;
        allAuctions.add(this);
    }

    public static Auction getAuctionById(String id) {
        for (Auction auction : allAuctions) {
            if (auction.id.equals(id)) {
                return auction;
            }
        }
        return null;
    }

    public static List<Auction> getAllAuctions() {
        return allAuctions;
    }

    public String getId() {
        return id;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public static ArrayList<String> getAuctionedProducts() {
        ArrayList<String> productsInShort = new ArrayList<>();
        for (Auction auction : allAuctions) {
            for (Product product : auction.products)
                productsInShort.add("#" + product.getId() + " : \n" +
                        "price = " + product.getPrice() +
                        "\nAuctioned price = " + product.getAuctionedPrice() +
                        "\nAuction's ending date = " + auction.endingDate +
                        "\n--------------------------------------------"); // TODO localDate shit
        }
        return productsInShort;
    }

    public void setBeginningDate(Date beginningDate) {
        this.beginningDate = beginningDate;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public void setEndingDate(Date endingDate) {
        this.endingDate = endingDate;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setStatus(AuctionStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Auction{" +
                "id='" + id + '\'' +
                ", products=" + products +
                ", status=" + status +
                ", beginningDate=" + beginningDate +
                ", endingDate=" + endingDate +
                ", discountAmount=" + discountAmount +
                '}';
    }

    public String viewInShort() {
        return null;
    }

    public static AuctionStatus parseAuctionStatus(String status) {
        if (status.equals("UNDER_REVIEW_FOR_CONSTRUCTION")) {
            return AuctionStatus.UNDER_REVIEW_FOR_CONSTRUCTION;
        } else if (status.equals("UNDER_REVIEW_FOR_EDITING")) {
            return AuctionStatus.UNDER_REVIEW_FOR_EDITING;
        } else if (status.equals("CONFIRMED")) {
            return AuctionStatus.CONFIRMED;
        } else {
            return null;
        }
    }

    public AuctionStatus getStatus() {
        return status;
    }

    public Date getBeginningDate() {
        return beginningDate;
    }

    public Date getEndingDate() {
        return endingDate;
    }

    public enum AuctionStatus {
        UNDER_REVIEW_FOR_CONSTRUCTION, UNDER_REVIEW_FOR_EDITING, CONFIRMED
    }

    public static void open() throws Exception{
        File folder = new File(Address.AUCTIONS.get());
        if (!folder.exists()) folder.mkdirs();
        else {
            for (File file : folder.listFiles()) {
                allAuctions.add(open(file));
            }
        }
    }

    public static Auction open(File file) throws Exception{
        StringBuilder json = new StringBuilder();
            Scanner reader = new Scanner(file);
            while (reader.hasNext()) json.append(reader.next());
        return Gson.INSTANCE.get().fromJson(json.toString(), Auction.class);
    }

    public static void save() throws Exception {
        for (Auction auction : allAuctions) {
            save(auction);
        }
    }

    public static void save(Auction auction) throws Exception {
        String jsonAccount = Gson.INSTANCE.get().toJson(auction);
        FileWriter file = new FileWriter(Address.AUCTIONS.get() + "\\" + auction.getId() + ".json");
        file.write(jsonAccount);
        file.close();
    }

    public static void loadReferences() {
        for (Auction auction : allAuctions) {
            auction.loadReference();
        }
    }

    private void loadReference() {
        products = new ArrayList<>();
        for (String productsId : productsIds) {
            products.add(Product.getProductById(productsId));
        }
    }
}

