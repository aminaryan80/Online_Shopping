package Control;

import Models.Account.Account;
import Models.Account.Principal;
import Models.Shop.Cart;
import Models.Shop.Category.Category;
import Models.Shop.Off.Discount;
import Models.Shop.Product.Product;
import View.Menu;
import ViewController.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public abstract class Manager {
    protected static Account account;
    protected static Cart cart = new Cart();
    protected static Category mainCategory;
    protected static Stage stage;
    protected static Stage popup = new Stage();
    protected Addresses previousAddress;
    protected Manager previousManager;
    protected Menu menu;
    protected boolean isPrincipalExists = Principal.isPrincipalExists();

    public Manager(Account account) {
        Manager.account = account;
        if (!Category.hasCategoryWithName("mainCategory")) {
            mainCategory = new Category("mainCategory", null, new HashMap<>(), new ArrayList<>());
        } else {
            mainCategory = Category.getCategoryByName("mainCategory");
        }
    }

    public Manager(Account account, Addresses address, Manager manager) {
        Manager.account = account;
        if (!Category.hasCategoryWithName("mainCategory")) {
            mainCategory = new Category("mainCategory", null, new HashMap<>(), new ArrayList<>());
        } else {
            mainCategory = Category.getCategoryByName("mainCategory");
        }
        this.previousAddress = address;
        this.previousManager = manager;
    }

    public static Cart getCart() {
        return cart;
    }

    public static void setStage(Stage stage) {
        Manager.stage = stage;
    }

    public Account getAccount() {
        return account;
    }

    public static void setAccount(Account account) {
        Manager.account = account;
    }

    public Category getMainCategory() {
        return mainCategory;
    }

    public Menu getMenu() {
        return menu;
    }

    public void logout() {
        account = null;
//        cart = new Cart();
    }

    public boolean userExistsWithUsername(String username) {
        return Account.hasAccountWithUsername(username);
    }

    protected boolean checkAccountType(String type) {
        return type.matches("^(customer|seller|principal)$");
    }

    public boolean checkEmail(String email) {
        return email.matches("^\\S+@\\S+\\.\\S+$");
    }

    public boolean checkPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^\\d{11}$");
    }

    public boolean checkNumber(String number) {
        return number.matches("^\\d+(\\.\\d+)?$");
    }

    public boolean checkDate(String date) {
        //return date.matches("^\\d{2}-\\d{2}-\\d{4}$");
        return date.matches("^(\\d{4}-\\d{2}-\\d{2})$");
    }

    protected Date parseDate(String stringDate) {
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = format.parse(stringDate);
        } catch (Exception ignored) {

        }
        return date;
    }

    public boolean checkPercent(String date) {
        return date.matches("^(100|(\\d{1,2}))$");
    }

    public String showCategories() {
        StringBuilder result = new StringBuilder();
        buildCategoryList(mainCategory, result, 1);
        return result.toString();
    }

    private void buildCategoryList(Category currentCategory, StringBuilder categoryField, int categoryLevel) {
        if (currentCategory != mainCategory) {
            categoryField.append("\n");
        }
        for (int i = 0; i < categoryLevel; i++) {
            categoryField.append("-");
        }
        categoryField.append(currentCategory.getName());
        for (Category category : currentCategory.getSubCategories()) {
            buildCategoryList(category, categoryField, categoryLevel + 1);
        }
    }

    public boolean isPrincipalExists() {
        return isPrincipalExists;
    }

    public void viewPersonalInfo() {
        System.out.println(account.toString());
        new ViewPersonalInfoManager(account);
    }

    public boolean isDiscountCodeValid(String DiscountCodeId) {
        return Discount.getDiscountById(DiscountCodeId) != null;
    }

    public boolean doesProductExist(String productId) {
        return Product.getProductById(productId) != null;
    }

    public Discount getDiscountById(String discountId) {
        return Discount.getDiscountById(discountId);
    }

    public Product getProduct(String productId) {
        return Product.getProductById(productId);
    }

    public Controller loadBack(Addresses address, Manager manager) {
        return loadFxml(address, false, manager);
    }

    public Controller loadFxml(Addresses address) {
        return loadFxml(address, false, this);
    }

    public Controller loadFxml(Addresses address, boolean isPopup) {
        return loadFxml(address, isPopup, this);
    }

    public Controller loadFxml(Addresses address, boolean isPopup, Manager manager) {
        Stage workingStage;
        if (isPopup)
            workingStage = popup;
        else
            workingStage = stage;
        Controller controller = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(address.getAddress()));
            Parent root = loader.load();
            controller = loader.getController();
            Scene scene = new Scene(root);
            controller.setManager(manager);
            workingStage.setTitle("AP Project");
            workingStage.setScene(scene);
            workingStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return controller;
    }

    public void error(String message) {
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setAlertType(Alert.AlertType.ERROR);
        a.setContentText(message);
        a.show();
    }

    public void success(String message) {
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setAlertType(Alert.AlertType.CONFIRMATION);
        a.setContentText(message);
        a.show();
    }

    public void back() {
        loadBack(previousAddress, previousManager);
    }

    public enum Addresses {
        MAIN_MENU("view/main_menu.fxml"),

        USER_PANEL("view/userPanel/user_panel.fxml"),

        PRINCIPAL_MENU("view/principal/principal_menu.fxml"),

        EDIT_PASSWORD("view/edit_password.fxml"),

        MANAGE_USERS("view/principal/manage_users_menu.fxml"),

        MANAGE_PRODUCTS("view/principal/manageProducts/manage_products_menu.fxml"),

        MANAGE_REQUESTS("view/principal/manage_requests_menu.fxml"),

        MANAGE_CATEGORIES("view/principal/manageCategories/manage_categories_menu.fxml"),

        ADD_CATEGORY("view/principal/manageCategories/add_category.fxml"),

        EDIT_CATEGORY("view/principal/manageCategories/editCategory/edit_category_menu.fxml"),

        EDIT_NAME_CATEGORY("view/principal/manageCategories/editCategory/edit_name_category_menu.fxml"),

        EDIT_FEATURES_CATEGORY("view/principal/manageCategories/editCategory/edit_features_category_menu.fxml"),

        VIEW_DISCOUNT_CODES("view/principal/viewDiscountCodes/view_discount_codes_menu.fxml");
        private String address;

        Addresses(String address) {
            this.address = address;
        }

        public String getAddress() {
            return address;
        }
    }
}