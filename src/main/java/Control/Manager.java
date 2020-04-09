package Control;

import Models.Account.Account;
import Models.Account.Customer;
import Models.Account.Seller;

import java.util.ArrayList;
import java.util.Date;

public class Manager {
    private Account account;

    public boolean userExistsWithUsername(String username) {

    }

    public void changeFirstName(String newFirstName) {

    }

    public void changeLastName(String newLastName) {

    }

    public void login(String username,String password){
//        if(){
//            if(){
//
//            }else View.ErrorProcessor.wrongPassword();
//        }else
    }

    public boolean checkEmail(String email){
        return true;
    }

    public boolean checkPhoneNumber(String phoneNumber){
        return true;
    }

    public void createAccount(String type, String username, String password, String email, String firstName,
                              String lastName, String phoneNumber, double balance) {

    }

    public String viewPersonalInfo(){
        return account.toString();
    }

    public boolean isEnteredAccountFieldValid(String field) {

    }

    public void editAccountAttribute(String field, String newAttribute) {

    }

    public boolean isEnteredRoleValid(String type) {

    }

    public void deleteUsername(String username) {

    }

    public boolean hasProductWithId(String id) {

    }

    public void deleteProduct(String id) {

    }

    public void createDiscountCode(String id, String beginningDate, String endingDate, int discountPercent,
                                   double maximumDiscount, int discountUseCount, ArrayList<String> allowedCustumersUsernames) {

    }

    private Date parseStringToDate(String string) {

    }

    private ArrayList<Customer> getCustomersListByUsernames(ArrayList<String> allowedCustumersUsernames) {

    }

    public void viewDiscountCodes() {

    }

    public String viewSingleDiscountCode(String id) {

    }

    public boolean isDiscountCodeValid(String id) {

    }

    public boolean isEnteredDiscountFieldValid(String field) {

    }

    public void editDiscountAttribute(String id, String field, String newAttribute) {

    }

    public void deleteDiscountCode(String id) {

    }

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

    public ArrayList<String> viewSalesHistory() {

    }

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

    public void addProduct(String id, String name, String companyName, Seller seller, String category, double price, boolean iaAvailable, String description) {

    }
}
