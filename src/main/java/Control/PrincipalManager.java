package Control;

import Models.Account.Account;
import Models.Account.Customer;

import java.util.ArrayList;
import java.util.Date;

public class PrincipalManager extends MainManager {

    public PrincipalManager(Account account) {
        super(account);
    }

    // edit [field]
    public boolean isEnteredAccountFieldValid(String field) {

    }

    public void editAccountAttribute(String field, String newAttribute) {

    }

    public boolean isEnteredFieldValid(String type) {

    }

    // manage users
    public String viewAccountByUsername(String username) {

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

    }

    // create discount code
    public void createDiscountCode(String id, String beginningDate, String endingDate, int discountPercent,
                                   double maximumDiscount, int discountUseCount, ArrayList<String> allowedCustumersUsernames) {

    }

    private Date parseStringToDate(String string) {

    }

    private ArrayList<Customer> getCustomersListByUsernames(ArrayList<String> allowedCustumersUsernames) {

    }

    // view discount codes
    public ArrayList<String> viewDiscountCodes() {

    }

    public String viewSingleDiscountCode(String id) {

    }

    public boolean isEnteredDiscountFieldValid(String field) {

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

    }

    public void addCategory(String name, ArrayList<String> features, ArrayList<String> productIds) {

    }

    public void removeCategory(String name) {

    }

}
