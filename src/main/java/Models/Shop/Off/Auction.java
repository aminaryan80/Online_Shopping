package Models.Shop.Off;

import Control.Identity;
import Models.Address;
import Models.Gson;
import Models.Shop.Product.Product;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Auction {
    private static List<Auction> allAuctions = new ArrayList<>();
    private String id;
    private List<String> productsIds;
    private AuctionStatus status;
    private LocalDate beginningDate;
    private LocalDate endingDate;
    private double discountAmount;

    public Auction(List<String> productsIds, LocalDate beginningDate, LocalDate endingDate, double discountAmount) {
        this.id = Identity.getId();
        this.productsIds = productsIds;
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
            for (Product product : auction.getProducts())
                productsInShort.add("#" + product.getId() + " : \n" +
                        "price = " + product.getPrice() +
                        "\nAuctioned price = " + product.getAuctionedPrice() +
                        "\nAuction's ending date = " + auction.endingDate +
                        "\n--------------------------------------------"); // TODO localDate shit
        }
        return productsInShort;
    }

    public ArrayList<String> getAuctionProducts() {
        ArrayList<String> productsInShort = new ArrayList<>();
        for (Product product : getProducts())
            productsInShort.add("#" + product.getId() + " : \n" +
                    "price = " + product.getPrice() +
                    "\nAuctioned price = " + product.getAuctionedPrice() +
                    "\nAuction's ending date = " + endingDate +
                    "\n--------------------------------------------"); // TODO localDate shit
        return productsInShort;
    }

    public static void addAuction(Auction auction) {
        allAuctions.add(auction);
    }

    public void setBeginningDate(LocalDate beginningDate) {
        this.beginningDate = beginningDate;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public void setEndingDate(LocalDate endingDate) {
        this.endingDate = endingDate;
    }

//    public void setProducts(List<Product> products) {
//        this.products = products;
//    }

    public void setStatus(AuctionStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Auction{" +
                "id='" + id + '\'' +
                ", products=" + getProducts() +
                ", status=" + status +
                ", beginningDate=" + beginningDate +
                ", endingDate=" + endingDate +
                ", discountAmount=" + discountAmount +
                '}';
    }

    public static AuctionStatus parseAuctionStatus(String status) {
        switch (status) {
            case "UNDER_REVIEW_FOR_CONSTRUCTION":
                return AuctionStatus.UNDER_REVIEW_FOR_CONSTRUCTION;
            case "UNDER_REVIEW_FOR_EDITING":
                return AuctionStatus.UNDER_REVIEW_FOR_EDITING;
            case "CONFIRMED":
                return AuctionStatus.CONFIRMED;
            default:
                return null;
        }
    }

    public AuctionStatus getStatus() {
        return status;
    }

    public LocalDate getBeginningDate() {
        return beginningDate;
    }

    public LocalDate getEndingDate() {
        return endingDate;
    }

    public boolean isActive(LocalDate now) {
        return now.compareTo(beginningDate) > 0 && now.compareTo(endingDate) < 0;
    }

    public enum AuctionStatus {
        UNDER_REVIEW_FOR_CONSTRUCTION, UNDER_REVIEW_FOR_EDITING, CONFIRMED
    }

    public static void open() throws Exception {
        File folder = new File(Address.AUCTIONS.get());
        if (!folder.exists()) folder.mkdirs();
        else {
            for (File file : folder.listFiles()) {
                allAuctions.add(open(file));
            }
        }
    }

    public static Auction open(File file) throws Exception {
        StringBuilder json = new StringBuilder();
        Scanner reader = new Scanner(file);
        while (reader.hasNext()) json.append(reader.next());
        reader.close();
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

//    public static void loadReferences() {
//        for (Auction auction : allAuctions) {
//            auction.loadReference();
//        }
//    }

//    private void loadReference() {
//        products = new ArrayList<>();
//        for (String productsId : productsIds) {
//            products.add(Product.getProductById(productsId));
//        }
//    }

    public ArrayList<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<>();
        for (String productsId : productsIds) {
            products.add(Product.getProductById(productsId));
        }
        return products;
    }
}

