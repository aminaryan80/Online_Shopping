package View;

import java.util.regex.Matcher;

public class PrincipalMenu extends MainMenu {
    public void principalMenu() {
        String password = "Jesus";
        String newPassword = "Christ";
        manager.changePassword(password, newPassword);
    }

    private void manageUsers() {

    }

    private void viewManager(String username) {

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

    private void manageRequests() {

    }

    private void showRequestDetails(String id) {

    }

    private void acceptRequest(String id) {

    }

    private void declineRequest(String id) {

    }

    private void manageCategories() {

    }

    private void editCategory(String category) {

    }

    private void addCategory(String name) {

    }

    private void removeCategory(String name) {

    }
}
