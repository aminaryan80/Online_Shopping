package Models.Shop;

import Models.Account.Customer;
import Models.Account.Seller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Product {
    private static ArrayList<Product> allProducts = new ArrayList<>();
    private String id;
    private ProductStatus status;
    private String name;
    private String companyName;
    private double price;
    private Seller seller;
    private boolean isAvailable;
    private Category category;
    private String description;
    private double rate;
    private ArrayList<Customer> allBuyers;
    private List<Comment> allComments;
    private ArrayList<Features> features;
    //TODO different sellers for one product


    public Product(String id, String name, String companyName, double price, Seller seller,
                   boolean isAvailable, Category category, String description, ArrayList<Features> features) {
        this.id = id;
        this.name = name;
        this.companyName = companyName;
        this.price = price;
        this.seller = seller;
        this.isAvailable = isAvailable;
        this.category = category;
        this.description = description;
        this.features = features;
    }

    public static boolean hasProductWithId(String id) {

    }

    public static Product getProductById(String id) {

    }

    public static void deleteProduct(Product product) {

    }

    public static ArrayList<String> viewProductInShort() {

    }

    public String getId() {
        return id;
    }

    public List<String> getComments() {

    }

    public Seller getSeller() {
        return seller;
    }

    private enum ProductStatus {
        UNDER_REVIEW_FOR_CONSTRUCTION, UNDER_REVIEW_FOR_EDITING, CONFIRMED
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", status=" + status +
                ", name='" + name + '\'' +
                ", companyName='" + companyName + '\'' +
                ", price=" + price +
                ", seller=" + seller +
                ", isAvailable=" + isAvailable +
                ", category=" + category +
                ", description='" + description + '\'' +
                ", rate=" + rate +
                ", allComments=" + allComments +
                '}';
    }
}
