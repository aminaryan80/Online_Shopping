package Models.Shop;

import Control.Manager;
import Control.Seller.SellerManager;
import Models.Account.Seller;

import java.util.ArrayList;

public class AddProductRequest extends Request {
    private String productId;
    private String productName;
    private String companyName;
    private Category category;
    private double price;
    private boolean isAvailable;
    private String description;
    private ArrayList<Features> allFeatures;

    public AddProductRequest(String id, Seller seller, Manager manager, String productId,
                             String productName, String companyName, Category category, double price,
                             boolean isAvailable, String description, ArrayList<Features> allFeatures) {
        super(id, seller, manager);
        this.type = RequestType.ADD_PRODUCT;
        this.productId = productId;
        this.productName = productName;
        this.companyName = companyName;
        this.category = category;
        this.price = price;
        this.isAvailable = isAvailable;
        this.description = description;
        this.allFeatures = allFeatures;
    }

    @Override
    public void accept() {
        ((SellerManager) manager).addProduct(productId, productName, companyName, category, price, isAvailable, description, allFeatures);
    }

    @Override
    public String toString() {
        return "AddProductRequest{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", companyName='" + companyName + '\'' +
                ", category=" + category +
                ", price=" + price +
                ", isAvailable=" + isAvailable +
                ", description='" + description + '\'' +
                ", allFeatures=" + allFeatures +
                ", id='" + id + '\'' +
                ", seller=" + seller +
                ", type=" + type +
                '}';
    }
}
