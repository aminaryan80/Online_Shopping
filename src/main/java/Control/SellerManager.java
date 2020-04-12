package Control;

import Models.Account.Account;
import Models.Account.Seller;
import Models.Shop.Product;

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

    }

    // view sales history
    public ArrayList<String> viewSalesHistory() {

    }

    // manage products
    public ArrayList<String> viewProducts() {

    }

    public String viewProductDetails(String id) {

    }

    public ArrayList<String> viewProductBuyers(String id) {

    }

    public boolean isEnteredProductFieldValid(String field) {

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

    }

    // view offs
    public void viewOffs() {

    }

    public String viewOffById(String id) {

    }

    public void editOffAttribute(String id, String field, String newValue) {

    }

    public void addOff(String id, String beginningDate, String endingDate,
                       double discountAmount, ArrayList<String> productsNames) {

    }

    private ArrayList<Product> getProductsListByNames(ArrayList<String> productsNames) {

    }

    // view balance
    public double viewSellerBalance() {

    }

}
