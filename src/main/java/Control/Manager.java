package Control;

import Control.Products.ProductsManager;
import Models.Account.Account;
import Models.Account.Principal;
import Models.Shop.Cart;
import Models.Shop.Category.Category;
import Models.Shop.Off.Discount;
import Models.Shop.Product.Product;
//import View.Menu;
import View.Menu;
import ViewController.Controller;
import ViewController.SortController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
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

    public Manager(Account account, Addresses previousAddress, Manager previousManager) {
        Manager.account = account;
        if (!Category.hasCategoryWithName("mainCategory")) {
            mainCategory = new Category("mainCategory", null, new HashMap<>(), new ArrayList<>());
        } else {
            mainCategory = Category.getCategoryByName("mainCategory");
        }
        this.previousAddress = previousAddress;
        this.previousManager = previousManager;
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

    /*public Menu getMenu() {
        return menu;
    }*/

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
    public boolean isEnteredSortFieldValid(String field) {
        return false;
    }

    public String currentSort() {
        return "";
    }

    public String showAvailableSorts() {
        return "";
    }

    public ArrayList<String> getSortFields() {
        return null;
    }

    public ArrayList<Object> sort(String sort, boolean isAscending) {
        return null;
    }

    public ArrayList<Object> disableSort() {
        return null;
    }

    public void openSort(Controller controller, Manager manager) {
        Controller myController = loadFxml(Manager.Addresses.SORT, true, manager);
        ((SortController) myController).init(controller);
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
            controller.setManager(manager);
            Scene scene = new Scene(root);
            workingStage.setTitle("AP Project");
            workingStage.setScene(scene);
            workingStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return controller;
    }

    public void openProductsMenu() {
        new ProductsManager(account, Addresses.MAIN_MENU, this, false);
    }

    public void openOffsMenu() {
        new ProductsManager(account, Addresses.MAIN_MENU, this, true);
    }

    public void editPassword() {
        new EditPasswordManager(account);
    }

    public TreeItem<String> getCategoriesInTable() {
        return getCategoriesInTable(mainCategory);
    }

    private TreeItem<String> getCategoriesInTable(Category category) {
        TreeItem<String> categories = new TreeItem<>(category.getName());
        for (Category subCategory : category.getSubCategories()) {
            categories.getChildren().add(getCategoriesInTable(subCategory));
        }
        categories.setExpanded(true);
        return categories;
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
        Controller controller = loadBack(previousAddress, previousManager);
        previousManager.update(controller);
    }

    public void update(Controller controller) {

    }

    public enum Addresses {

        ADD_PRODUCT_POP_UP("view/userPanel/Seller/FeaturesPopUp.fxml"),

        SORT("view/sorting.fxml"),

        MAIN_MENU("view/main_menu.fxml"),

        PRODUCTS_MENU("view/products/products_menu.fxml"),

        VIEW_CATEGORIES("view/products/view_categories.fxml"),

        REGISTER("view/userPanel/register_menu.fxml"),

        EDIT_PRODUCTS_MENU("view/userPanel/Seller/EditProducts.fxml"),

        USER_PANEL("view/userPanel/user_panel.fxml"),

        PRINCIPAL_MENU("view/principal/principal_menu.fxml"),

        SELLER_MENU("view/userPanel/Seller/seller_menu.fxml"),

        CUSTOMER_MENU("view/userPanel/dashboard/customer_dashboard_menu.fxml"),

        EDIT_OFFS("view/userPanel/Seller/EditOff.fxml"),

        EDIT_PASSWORD("view/edit_password.fxml"),

        MANAGE_USERS("view/principal/manage_users_menu.fxml"),

        MANAGE_PRODUCTS("view/principal/manageProducts/manage_products_menu.fxml"),

        MANAGE_REQUESTS("view/principal/manage_requests_menu.fxml"),

        MANAGE_CATEGORIES("view/principal/manageCategories/manage_categories_menu.fxml"),

        ADD_CATEGORY("view/principal/manageCategories/add_category.fxml"),

        EDIT_CATEGORY("view/principal/manageCategories/editCategory/edit_category_menu.fxml"),

        EDIT_NAME_CATEGORY("view/principal/manageCategories/editCategory/edit_name_category_menu.fxml"),

        EDIT_FEATURES_CATEGORY("view/principal/manageCategories/editCategory/edit_features_category_menu.fxml"),

        VIEW_DISCOUNT_CODES("view/principal/viewDiscountCodes/view_discount_codes_menu.fxml"),

        CREATE_DISCOUNT_CODE("view/principal/create_new_discount.fxml"),

        EDIT_DISCOUNTS("view/principal/viewDiscountCodes/edit_discount_menu.fxml"),

        VIEW_CART("view/userPanel/Customer/ViewCart.fxml"),

        VIEW_ORDERS("view/userPanel/Customer/ViewOrders.fxml"),

        COMMENT("view/userPanel/Customer/product/Comment.fxml"),

        ADD_COMMENT("view/userPanel/Customer/product/addCommentPage.fxml"),

        PRODUCT_PAGE("view/userPanel/Customer/product/productPage.fxml");

        private String address;

        Addresses(String address) {
            this.address = address;
        }

        public String getAddress() {
            return address;
        }
    }
}