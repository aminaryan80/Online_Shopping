package Control;

import Models.Account.Account;
import Models.Account.Customer;
import View.Menu;
import View.PrincipalMenu;

import java.util.ArrayList;
import java.util.Date;

public class PrincipalManager extends MainManager {

    public PrincipalManager(Account account, Menu menu) {
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

    // manage users
    public String viewAccountByUsername(String username) {
return null;
    }

    public void deleteUsername(String username) {

    }

    public void createPrincipalAccount() {

    }

    // manage all products
    public void manageProducts() {

    }

    public void deleteProduct(String id) {

    }

    public boolean hasProductWithId(String id) {
        return true;
    }

    // create discount code
    public void createDiscountCode(String id, String beginningDate, String endingDate, int discountPercent,
                                   double maximumDiscount, int discountUseCount, ArrayList<String> allowedCustumersUsernames) {

    }

    private Date parseStringToDate(String string) {
        return null;
    }

    private ArrayList<Customer> getCustomersListByUsernames(ArrayList<String> allowedCustumersUsernames) {
return null;
    }

    // view discount codes
    public ArrayList<String> viewDiscountCodes() {
        return null;
    }

    public String viewSingleDiscountCode(String id) {
        return null;
    }

    public boolean isEnteredDiscountFieldValid(String field) {
        return true;
    }

    public void editDiscountAttribute(String id, String field, String newAttribute) {

    }

    public void deleteDiscountCode(String id) {

    }

    //manage requests
    public void manageRequests() {

    }

    public void showRequestDetails(String id) {

    }

    private boolean hasRequestById(String id) {
        return true;
    }

    public void acceptRequest(String id) {

    }

    public void declineRequest(String id) {

    }

    // manage categories
    public void showCategories() {

    }

    public void editCategory(String category, String type, String newValue) {

    }

    public boolean hasCategoryWithName(String name) {
        return true;
    }

    public void addCategory(String name, ArrayList<String> features, ArrayList<String> productIds) {

    }

    public void removeCategory(String name) {

    }

}
