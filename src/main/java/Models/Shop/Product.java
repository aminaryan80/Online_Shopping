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


    public Product(String id, ProductStatus status, String name, String companyName, double price, Seller seller,
                   boolean isAvailable, Category category, String description, double rate, Map<String, String> features) {
        this.id = id;
        this.status = status;
        this.name = name;
        this.companyName = companyName;
        this.price = price;
        this.seller = seller;
        this.isAvailable = isAvailable;
        this.category = category;
        this.description = description;
        this.rate = rate;
        this.features = features;
        allProducts.add(this);
        this.status = ProductStatus.UNDER_REVIEW_FOR_CONSTRUCTION;
    }

    public static ArrayList<Product> getProductsByName(String name) {
        ArrayList<Product> products = new ArrayList<Product>();
        for (Product product : allProducts) {
            if (product.getName().equals(name)) {
                products.add(product);
            }
        }
        return products;
    }

    public String getName() {
        return name;
    }

    public static boolean hasProductWithId(String id) {

    }

    public static Product getProductById(String id) {

    }

    public static void deleteProduct(Product product) {

    }

    public static ArrayList<String> viewProductInShort() {
        return null;
    private String viewProductInShort() {
        //ToDo
    }

    public static ArrayList<String> viewProductsInShort(Seller seller) {
        ArrayList<String> allProductsInShort = new ArrayList<String>();
        for (Product product : allProducts) {
            if (product.getSeller().equals(seller)) {
                allProductsInShort.add(product.viewProductInShort());
            }
        }
        return allProductsInShort;
    }

    public String getId() {
        return id;
    }

    public List<String> getComments() {

    }

    public Seller getSeller() {
        return seller;
    }

    public void addRate(int score) {
        rates.add((double) score);
    }

    public double getRate() {
        return rates.stream().reduce((a, b) -> (a + b) / 2).orElse(0.0);
    }

    private enum ProductStatus {
        UNDER_REVIEW_FOR_CONSTRUCTION, UNDER_REVIEW_FOR_EDITING, CONFIRMED
    }

    public double getPrice() {
        return price;
    }

    public void setStatus(String status) {
        if (status.equals("UNDER_REVIEW_FOR_CONSTRUCTION")) {
            this.status = ProductStatus.UNDER_REVIEW_FOR_CONSTRUCTION;
        } else if (status.equals(ProductStatus.UNDER_REVIEW_FOR_EDITING)) {
            this.status = ProductStatus.UNDER_REVIEW_FOR_EDITING;
        } else {
            this.status = ProductStatus.CONFIRMED;
        }
    }

    public void setFeature(String feature)

    public void setName(String name) {
        this.name = name;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
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
