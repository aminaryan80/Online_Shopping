package Control.CustomerManagers;

import Control.Manager;
import Control.UtilTestObject;
import Models.Account.Account;
import Models.Account.Customer;

import Models.Shop.Category.Sort;
import Models.Shop.Log.BuyingLog;
import Models.Shop.Product.Product;
import View.CustomerMenus.customer.ViewOrdersMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ViewOrdersManager extends Manager {

    private Sort currentSort;
    private List<BuyingLog> logs;
    private Customer customer = (Customer) account;
    public ViewOrdersManager(Account account) {
        super(account);
        logs = ((Customer) account).getAllLogs();
        if(!account.getUsername().equals(UtilTestObject.CUSTOMER))
        this.menu = new ViewOrdersMenu(this);
    }

    public boolean canShowOrderWithId(String logId) {
        return customer.getLogById(logId) != null;
    }

    public boolean doesLogExist(String logId){
        return customer.getLogById(logId)!=null;
    }

    public String showOrderById(String logId) {
        return customer.getLogById(logId).toString();
    }

    public void rateProduct(String productId, int score) throws ViewCartManager.ProductDoNotExistAtAllException { //TODO RECHECK
        Product product = Product.getProductById(productId);
        if(product != null) product.addRate(customer,score);
        else throw new ViewCartManager.ProductDoNotExistAtAllException("Product does not exist");
    }

    private ArrayList<String> logsInShort() {
        ArrayList<String> logsInShort= new ArrayList<>();
        for (BuyingLog log : logs) {
            logsInShort.add(log.viewLogInShort());
        }
        return logsInShort;
    }

    public String showAvailableSorts() {
        return "money\n" +
                "date";
    }

    public List<BuyingLog> sort(String sort, boolean isAscending) {
        logs = ((Customer) account).getAllLogs();
        currentSort = new Sort(sort, isAscending);
        applySort();
        return logs;
    }

    private void applySort() {
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

    private void sortByMoney() {
        BuyingLog[] logsForSort = logs.toArray(new BuyingLog[0]);
        for (int i = 0; i < logsForSort.length; i++) {
            for (int j = i + 1; j < logsForSort.length; j++) {
                if (logsForSort[i].getMoney() > logsForSort[j].getMoney()) {
                    BuyingLog temp = logsForSort[i];
                    logsForSort[i] = logsForSort[j];
                    logsForSort[j] = temp;
                }
            }
        }
        logs = Arrays.asList(logsForSort);
    }

    private void sortByDate() {
        BuyingLog[] logsForSort = logs.toArray(new BuyingLog[0]);
        for (int i = 0; i < logsForSort.length; i++) {
            for (int j = i + 1; j < logsForSort.length; j++) {
                if (logsForSort[i].getDate().isBefore(logsForSort[j].getDate())) {
                    BuyingLog temp = logsForSort[i];
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

    public ArrayList<String> disableSort() {
        currentSort = null;
        logs = ((Customer) account).getAllLogs();
        return logsInShort();
    }
}
