package Control.Seller;

import Control.MainManager;
import Models.Account.Account;
import Models.Account.Seller;
import Models.Shop.Category;
import Models.Shop.Features;
import Models.Shop.Product;
import Models.Shop.SellingLog;
import java.util.ArrayList;

public class SellerManager extends MainManager {

    public SellerManager(Account account) {
        super(account);
    }

    // edit [field]
    public boolean isEnteredAccountFieldValid(String field) {

    }

    public void editAccountAttribute(String field, String newAttribute) {

    }

    public boolean isEnteredFieldValid(String type) {

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
    public void addProduct(String id, String name, String companyName,
                           Category category, double price, boolean iaAvailable,
                           String description, ArrayList<Features> features) {
        Product.addProduct(new Product(id, name, companyName, price, (Seller) account, iaAvailable, category, description, features));
    }

    // remove product [productId]
    public boolean isItSellersProduct(String id) {
        return Product.getProductById(id).getSeller().equals((Seller) account)
    }

    public void deleteProductById(String id) {
        Product.deleteProduct(Product.getProductById(id));
    }

    // show categories
    public ArrayList<String> showCategories() {
        return Category.getAllCategoriesNames();
    }

    // view offs
    public ArrayList<String> viewOffs() {
        return ((Seller) account).viewOffsInShort();
    }

    // view balance
    public double viewSellerBalance() {
        return ((Seller) account).getBalance();
    }

}
