package Client.ViewController;

import Models.Account.Account;
import Models.Account.Customer;
import Models.Account.Principal;
import Models.Account.Seller;
import Models.Gson;
import Models.Shop.Category.Category;
import Models.Shop.Off.Discount;
import Models.Shop.Product.Product;
import Models.Shop.Request.*;
import Server.Control.Manager;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Controller {
    protected Manager manager;
    protected static String accountUsername;
    private static Socket socket;
    private static DataInputStream input;
    private static DataOutputStream output;
    protected static String accountType;
    protected Addresses backAddress;
    protected static Stage stage;
    protected static Stage popup = new Stage();

    public static void closeClient() {
        logout();
    }

    public void init() {

    }

    public void initTable(ArrayList<Object> tableObjects) {

    }

    public static void setStage(Stage stage) {
        Controller.stage = stage;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public void setBack(Addresses backAddress) {
        this.backAddress = backAddress;
    }

    /*protected String sendRequest(String request) {
        return RequestProcessor.processRequest(request);
    }*/

    public DataOutputStream getOutput() {
        return output;
    }

    protected static String sendRequest(String request) {
        String response = null;
        try {
            socket = new Socket("localhost", 8080);
            System.out.println("Connected");
            // takes input from terminal
            input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            output = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            // Sends request and Receives response
            output.writeUTF(request);
            output.flush();
            do {
                response = input.readUTF();
            } while (response == null);
            // close the connection
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public void close() {
        try {
            input.close();
            output.close();
            socket.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void openUserPanel() {
        if (accountUsername == null) {
            loadFxml(Addresses.USER_PANEL);
        }
        if (accountUsername != null) {
            if (accountType.equals("P")) {
                loadFxml(Addresses.PRINCIPAL_MENU);
            } else if (accountType.equals("C")) {
                loadFxml(Addresses.CUSTOMER_MENU);
            } else if (accountType.equals("S")) {
                loadFxml(Addresses.SELLER_MENU);
            }
        }
    }

    public void openOffsMenu() {
        MainController.isOffsMenu = true;
        loadFxml(Addresses.PRODUCTS_MENU);
    }

    protected ArrayList<Account> getAllAccounts() {
        ArrayList<Principal> principals = new ArrayList<>(Gson.INSTANCE.get().fromJson(
                sendRequest("GET_ALL_PRINCIPALS"), new TypeToken<ArrayList<Principal>>() {
                }.getType()));
        ArrayList<Customer> customers = new ArrayList<>(Gson.INSTANCE.get().fromJson(
                sendRequest("GET_ALL_CUSTOMERS"), new TypeToken<ArrayList<Customer>>() {
                }.getType()));
        ArrayList<Seller> sellers = new ArrayList<>(Gson.INSTANCE.get().fromJson(
                sendRequest("GET_ALL_SELLERS"), new TypeToken<ArrayList<Seller>>() {
                }.getType()));
        ArrayList<Account> accounts = new ArrayList<>();
        accounts.addAll(principals);
        accounts.addAll(customers);
        accounts.addAll(sellers);
        return accounts;
    }

    protected ArrayList<Discount> getAllDiscounts() {
        return new ArrayList<>(Gson.INSTANCE.get().fromJson(
                sendRequest("GET_ALL_DISCOUNTS"), new TypeToken<ArrayList<Discount>>() {
                }.getType()));
    }

    protected ArrayList<Product> getAllProducts() {
        return new ArrayList<>(Gson.INSTANCE.get().fromJson(
                sendRequest("GET_ALL_PRODUCTS"), new TypeToken<ArrayList<Product>>() {
                }.getType()));
    }

    protected ArrayList<Request> getAllRequests() {
        String[] response = sendRequest("GET_ALL_REQUESTS").split("&&&");
        ArrayList<Request> requests = new ArrayList<>();
        requests.addAll(Gson.INSTANCE.get().fromJson(response[0], new TypeToken<ArrayList<AddOffRequest>>() {
        }.getType()));
        requests.addAll(Gson.INSTANCE.get().fromJson(response[1], new TypeToken<ArrayList<AddProductRequest>>() {
        }.getType()));
        requests.addAll(Gson.INSTANCE.get().fromJson(response[2], new TypeToken<ArrayList<AddSellerRequest>>() {
        }.getType()));
        requests.addAll(Gson.INSTANCE.get().fromJson(response[3], new TypeToken<ArrayList<DeleteProductRequest>>() {
        }.getType()));
        requests.addAll(Gson.INSTANCE.get().fromJson(response[4], new TypeToken<ArrayList<EditOffRequest>>() {
        }.getType()));
        requests.addAll(Gson.INSTANCE.get().fromJson(response[5], new TypeToken<ArrayList<EditProductRequest>>() {
        }.getType()));
        return requests;
    }

    public TreeItem<String> getCategoriesInTable() {
        ArrayList<Category> categories = new ArrayList<>(Gson.INSTANCE.get().fromJson(sendRequest("GET_ALL_CATEGORIES"),
                new TypeToken<ArrayList<Category>>() {
                }.getType()));
        return getCategoriesInTable(categories, getCategoryByName(categories, "mainCategory"));
    }

    private TreeItem<String> getCategoriesInTable(ArrayList<Category> allCategories, Category category) {
        TreeItem<String> categories = new TreeItem<>(category.getName());
        ArrayList<Category> subCategories = new ArrayList<>();
        for (String subCategoriesName : category.getSubCategoriesNames()) {
            subCategories.add(getCategoryByName(allCategories, subCategoriesName));
        }
        for (Category subCategory : subCategories) {
            categories.getChildren().add(getCategoriesInTable(allCategories, subCategory));
        }
        categories.setExpanded(true);
        return categories;
    }

    private Category getCategoryByName(ArrayList<Category> allCategories, String name) {
        for (Category category : allCategories) {
            if (category.getName().equals(name)) {
                return category;
            }
        }
        return null;
    }

    public static void logout() {
        System.out.println("LOGOUT");
        sendRequest("LOGOUT " + accountUsername);
        accountType = null;
        accountUsername = null;
    }

    public void logout(ActionEvent actionEvent) {
        logout();
        loadFxml(Addresses.MAIN_MENU);
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

    public void loadFxml(Addresses address) {
        loadFxml(address, false);
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

    public Controller loadFxml(Addresses address, boolean isPopup) {
        Stage workingStage;
        FXMLLoader loader = null;
        if (isPopup)
            workingStage = popup;
        else
            workingStage = stage;
        try {
//           FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(address.getAddress()));
            loader = getLoader(address);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            workingStage.setTitle("AP Project");
            workingStage.setScene(scene);
            workingStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return loader.getController();
        }

        public FXMLLoader getLoader (Addresses address){
            return new FXMLLoader(getClass().getClassLoader().getResource(address.getAddress()));
        }

        public void openSort (Controller controller){
            Controller myController = loadFxml(Addresses.SORT, true);
            ((SortController) myController).init(controller);
        }
    public enum Addresses {
        FILTER("view/products/filtering.fxml"),

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

        PURCHASE_PAGE("view/userPanel/Customer/purchase.fxml"),

        COMPARE("view/userPanel/Customer/compare.fxml"),

        SHOW_ORDER_PRODUCTS("view/userPanel/Customer/showOrderProducts.fxml"),

        PRODUCT_PAGE("view/userPanel/Customer/product/productPage.fxml"),

        AUCTION_DETAILS("view/products/AuctionDetails.fxml"),

        PRODUCT_ITEM("view/products/Product.fxml");

        private String address;

        Addresses(String address) {
            this.address = address;
        }

        public String getAddress() {
            return address;
        }
    }
}
