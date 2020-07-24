package Server.Control;

import Client.ViewController.Controller;
import Models.Account.Account;
import Models.Account.Principal;
import Models.Shop.Cart;
import Models.Shop.Category.Category;
import Models.Shop.Category.Filter;
import Models.Shop.Off.Discount;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Manager {
    protected static Account account;
    protected static Cart cart = new Cart();
    protected static Category mainCategory;
    protected boolean isPrincipalExists = Principal.isPrincipalExists();

    public Manager(Account account) {
        Manager.account = account;
        if (!Category.hasCategoryWithName("mainCategory")) {
            mainCategory = new Category("mainCategory", null, new HashMap<>(), new ArrayList<>());
        } else {
            mainCategory = Category.getCategoryByName("mainCategory");
        }
    }

    public static Cart getCart() {
        return cart;
    }

    public Account getAccount() {
        return account;
    }

    public static void setAccount(Account account) {
        Manager.account = account;
    }

    public boolean userExistsWithUsername(String username) {
        return Account.hasAccountWithUsername(username);
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
        return date.matches("^(\\d{4}-\\d{2}-\\d{2})$");
    }

    public static ArrayList<String> getSortFields() {
        return null;
    }

    public static ArrayList<Object> sort(String sort, boolean isAscending) {
        return null;
    }

    public ArrayList<Object> disableSort() {
        return null;
    }

    public void openSort(Controller controller, Manager manager) {
        //Controller myController = loadFxml(Addresses.SORT, true, manager);
        //((SortController) myController).init(controller);
    }

    public boolean checkPercent(String date) {
        return date.matches("^(100|(\\d{1,2}))$");
    }

    public boolean isPrincipalExists() {
        return isPrincipalExists;
    }

    public boolean isDiscountCodeValid(String DiscountCodeId) {
        return Discount.getDiscountById(DiscountCodeId) != null;
    }

    public Discount getDiscountById(String discountId) {
        return Discount.getDiscountById(discountId);
    }

    public void editPassword() {
        new EditPasswordManager(account);
    }

    public ArrayList<Object> disableFilter(String filterType) {
        return null;
    }

    public ArrayList<Object> applyFilter(String filterType, String value) {
        return null;
    }

    public List<Filter> currentFilters() {
        return null;
    }

    public ArrayList<String> getFilterTypes() {
        return null;
    }

    public static String sendRequest(String request) {
        Socket socket;
        DataInputStream input;
        DataOutputStream output;
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
}