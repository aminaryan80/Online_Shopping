package Server.Control.Seller;

import Server.Control.Manager;
import Client.ViewController.Controller;
import Models.Account.Account;
import Models.Account.Seller;
import Models.Shop.Category.Sort;
import Models.Shop.Log.SellingLog;
import Models.Shop.Product.Product;
import Models.Shop.Request.DeleteProductRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SellerManager extends Manager {

    private static Sort currentSort;
    private static List<SellingLog> logs;

    public SellerManager(Account account) {
        super(account);
    }

    public void deleteProductById(String sellerUsername, String id) {
        Seller seller = (Seller) Account.getAccountByUsername(sellerUsername);
        new DeleteProductRequest(seller, Product.getProductById(id));
    }

    public String showAvailableSorts() {
        return "money\n" +
                "date";
    }

    public static ArrayList<Object> sort(String sort, boolean isAscending) {
        logs = ((Seller) account).getAllLogs();
        currentSort = new Sort(sort, isAscending);
        applySort();
        return new ArrayList<>(logs);
    }

    private static void applySort() {
        if (currentSort == null) {
            return;
        }
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

    private static void sortByMoney() {
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

    private static void sortByDate() {
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
        logs = Arrays.asList(logsForSort);
    }

    public boolean isEnteredSortFieldValid(String field) {
        return field.equals("money") || field.equals("date");
    }

    public String currentSort() {
        return currentSort.toString();
    }

    public ArrayList<Object> disableSort() {
        currentSort = null;
        logs = ((Seller) account).getAllLogs();
        return new ArrayList<>(logs);
    }
}
