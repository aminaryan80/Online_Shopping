package Models.Shop;

import Models.Account.Seller;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private static ArrayList<Product> allProducts = new ArrayList<Product>();
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
    private List<Comment> allComments;
    //TODO different sellers for one product

    public static Product getProductById(String id) {

    }

    public static void deleteProduct(Product product) {

    }

    private enum ProductStatus {
        UNDER_REVIEW_FOR_CONSTRUCTION, UNDER_REVIEW_FOR_EDITING, CONFIRMED;
    }
}
