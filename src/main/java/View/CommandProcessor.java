package View;

import Control.Manager;

import java.util.Scanner;
import java.util.regex.Matcher;

public class CommandProcessor {
    private Manager manager;
    private String command;
    private Scanner scanner = new Scanner(System.in);

    public void mainMenu() {

    }

    public void productsMenu() {

    }

    public void accountMenu(String type) {

    }

    public void principalMenu() {
        String password = "Jesus";
        String newPassword = "Christ";
        manager.changePassword(password, newPassword);
    }

    public void sellerMenu() {

    }

    public void customerMenu() {

    }

    public void productPage() {

    }

    public void auctionMenu() {

    }

    private void userMenu() {
//        register(type,username);
//        login(username);
    }

    private void register(String type, String username) {
        manager.createAccount(firstname, lastname, phoneNumber, email, balance, type, username, company);
    }

    private void login(String username) {
        String password;
        manager.login(username, password);
    }

    private void editAttribute(String field) {
        if (manager.isEnteredAccountFieldValid()) {
            manager.editAccountAttribute(newAttribute)
        }
    }

    private void manageUsers() {

    }

    private void viewManager(String username) {

    }

    private void changeType(String username, String type) {

    }

    private void deleteUser(String username) {

    }

    private void createManagerProfile() {
        register("manager", username);
    }

    private void manageAllProducts() {

    }

    private void removeProduct(String id) {

    }

    private void createDiscountCode() {

    }

    private void viewDiscountCodes() {
        // prints all discounts

    }

    private void viewSingleDiscountCode(String id) {

    }

    private void editDiscountCode(String id) {
        // if code valid
        // edit [field]
        // if field valid

    }

    private void removeDiscountCode(String id) {

    }

    private Matcher getMatcher(String input, String regex) {
    }
}
