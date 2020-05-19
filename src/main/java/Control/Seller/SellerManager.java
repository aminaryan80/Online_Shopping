package Control.Seller;

import Control.Manager;
import Models.Account.Account;
import Models.Account.Seller;
import Models.Shop.Category.Category;
import Models.Shop.Category.Feature;
import Models.Shop.Category.Sort;
import Models.Shop.Log.SellingLog;
import Models.Shop.Product.Product;
import Models.Shop.Request.AddProductRequest;
import View.Seller.SellerMenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class SellerManager extends Manager {

    private Sort currentSort;
    private ArrayList<SellingLog> logs;

    public SellerManager(Account account) {
        super(account);
        logs = ((Seller) account).getAllLogs();
        new SellerMenu(this);
    }

    // view company information
    public String viewCompanyInformation() {
        return ((Seller) account).getCompanyName();
    }

    // view sales history
    public ArrayList<String> viewSalesHistory() {
        ArrayList<SellingLog> allLogs = ((Seller) account).getAllLogs();
        ArrayList<String> salesHistory = new ArrayList<>();
        for (SellingLog Log : allLogs) {
            salesHistory.add(Log.toString());
        }
        return salesHistory;
    }

    // manage products
    public ArrayList<String> viewProducts() {
        return Product.viewProductsInShort((Seller) account);
    }

    // add product
    public void addProduct(String name,
                           Category category, double price, boolean iaAvailable,
                           String description, ArrayList<Feature> features) {
        String companyName = ((Seller) account).getCompanyName();
        Product product = new Product(name, companyName, price, (Seller) account, iaAvailable, category, description, features);
        product.setStatus(Product.ProductStatus.UNDER_REVIEW_FOR_CONSTRUCTION);
        new AddProductRequest((Seller) account, product);
    }

    // remove product [productId]
    public boolean isItSellersProduct(String id) {
        return Product.getProductById(id).getSeller().equals(account);
    }

    public void deleteProductById(String id) throws IOException {
        Product.deleteProduct(Product.getProductById(id));
    }

    // view offs
    public ArrayList<String> viewOffs() {
        return ((Seller) account).viewOffsInShort();
    }

    // view balance
    public double viewSellerBalance() {
        return account.getBalance();
    }

    private ArrayList<String> logsInShort() {
        ArrayList<String> logsInShort= new ArrayList<>();
        for (SellingLog log : logs) {
            logsInShort.add(log.viewLogInShort());
        }
        return logsInShort;
    }

    public String showAvailableSorts() {
        return "money\n" +
                "date";
    }

    public ArrayList<String> sort(String sort, boolean isAscending) {
        logs = ((Seller) account).getAllLogs();
        currentSort = new Sort(sort, isAscending);
        applySort();
        return logsInShort();
    }

    private void applySort() {
        String field = currentSort.getField();
        if (field.equals("money")) {
            sortByMoney();
        } else {
            sortByDate();
        }
        if (!currentSort.isAscending()) {
            Collections.reverse(logs);
        }
    }

    private void sortByMoney() {
        SellingLog[] logsForSort = logs.toArray(new SellingLog[0]);
        for (int i = 0; i < logsForSort.length; i++) {
            for (int j = i + 1; j < logsForSort.length; j++) {
                if (logsForSort[i].getMoney() > logsForSort[j].getMoney()) {
                    SellingLog temp = logsForSort[i];
                    logsForSort[i] = logsForSort[j];
                    logsForSort[j] = temp;
                }
            }
        }
        logs = (ArrayList<SellingLog>) Arrays.asList(logsForSort);
    }

    private void sortByDate() {
        SellingLog[] logsForSort = logs.toArray(new SellingLog[0]);
        for (int i = 0; i < logsForSort.length; i++) {
            for (int j = i + 1; j < logsForSort.length; j++) {
                if (logsForSort[i].getDate().isBefore(logsForSort[j].getDate())) {
                    SellingLog temp = logsForSort[i];
                    logsForSort[i] = logsForSort[j];
                    logsForSort[j] = temp;
                }
            }
        }
        logs = (ArrayList<SellingLog>) Arrays.asList(logsForSort);
    }

    public boolean isEnteredSortFieldValid(String field) {
        return field.equals("money") || field.equals("date");
    }

    public String currentSort() {
        return currentSort.toString();
    }

    public ArrayList<String> disableSort() {
        currentSort = null;
        logs = ((Seller) account).getAllLogs();
        return logsInShort();
    }
}
