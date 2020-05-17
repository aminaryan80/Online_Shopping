package Control.Seller;

import Control.Manager;
import Models.Account.Account;
import Models.Account.Seller;
import Models.Shop.Category.Category;
import Models.Shop.Category.Feature;
import Models.Shop.Log.SellingLog;
import Models.Shop.Product.Product;
import Models.Shop.Request.AddProductRequest;
import View.Seller.SellerMenu;

import java.util.ArrayList;

public class SellerManager extends Manager {

    public SellerManager(Account account) {
        super(account);
        new SellerMenu(this);
    }

    // edit [field]
    public boolean isEnteredAccountFieldValid(String field) {
        return false;
    }

    public void editAccountAttribute(String field, String newAttribute) {

    }

    public boolean isEnteredFieldValid(String type) {
        return false;
    }

    // view company information
    public String viewCompanyInformation() {
        return ((Seller) account).getCompanyName();
    }

    // view sales history
    public ArrayList<String> viewSalesHistory() {
        ArrayList<SellingLog> allLogs = ((Seller) account).getAllLogs();
        ArrayList<String> salesHistory = new ArrayList<String>();
        for (SellingLog Log : allLogs) {
            salesHistory.add(Log.toString());
        }
        return salesHistory;
    }

    // manage products
    public ArrayList<String> viewProducts() {
        return Product.viewProductsInShort((Seller) account);
    }

    // add product
    public void addProduct(String name,
                           Category category, double price, boolean iaAvailable,
                           String description, ArrayList<Feature> features) {
        String id = generateNewId();
        String companyName = ((Seller) account).getCompanyName();
        Product product = new Product(id, name, companyName, price, (Seller) account, iaAvailable, category, description, features);
        Product.addProduct(product);
        product.setStatus(Product.ProductStatus.UNDER_REVIEW_FOR_CONSTRUCTION);
        new AddProductRequest(id, (Seller) account, product);
    }

    // remove product [productId]
    public boolean isItSellersProduct(String id) {
        return Product.getProductById(id).getSeller().equals(account);
    }

    public void deleteProductById(String id) {
        Product.deleteProduct(Product.getProductById(id));
    }

    // view offs
    public ArrayList<String> viewOffs() {
        return ((Seller) account).viewOffsInShort();
    }

    // view balance
    public double viewSellerBalance() {
        return account.getBalance();
    }
}
