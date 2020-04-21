package Control;

import Models.Account.Account;
import Models.Account.Seller;
import Models.Shop.Product;
import View.Menu;

import java.util.ArrayList;

public class SellerManager extends MainManager {

    public SellerManager(Account account, Menu menu) {
        super(account, menu);
    }

    // edit [field]
    public boolean isEnteredAccountFieldValid(String field) {
        return true;
    }

    public void editAccountAttribute(String field, String newAttribute) {

    }

    public boolean isEnteredFieldValid(String type) {
        return true;
    }

    // view company information
    public String viewCompanyInformation() {
        return null;
    }

    // view sales history
    public ArrayList<String> viewSalesHistory() {
        return null;
    }

    // manage products
    public ArrayList<String> viewProducts() {
        return null;
    }

    public String viewProductDetails(String id) {
        return null;
    }

    public ArrayList<String> viewProductBuyers(String id) {

        return null;
    }

    public boolean isEnteredProductFieldValid(String field) {
        return true;
    }

    public void editProduct(String id) {

    }

    // add product
    public void addProduct(String id, String name, String companyName, Seller seller,
                           String category, double price, boolean iaAvailable, String description) {

    }

    // remove product [productId]
    public void deleteProductById(String id) {

    }

    // show categories
    public ArrayList<String> showCategories() {
        return null;
    }

    // view offs
    public void viewOffs() {

    }

    public String viewOffById(String id) {
        return null;
    }

    public void editOffAttribute(String id, String field, String newValue) {

    }

    public void addOff(String id, String beginningDate, String endingDate,
                       double discountAmount, ArrayList<String> productsNames) {

    }

    private ArrayList<Product> getProductsListByNames(ArrayList<String> productsNames) {
        return null;
    }

    // view balance
    public double viewSellerBalance() {
        return 1;
    }

}
