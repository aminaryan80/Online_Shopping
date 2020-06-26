package Control.CustomerManagers;

import Control.Manager;
import Control.UtilTestObject;
import Models.Account.Account;
import Models.Account.Customer;
import Models.Shop.Category.Sort;
import Models.Shop.Log.BuyingLog;
import Models.Shop.Product.Product;
import View.CustomerMenus.customer.ViewOrdersMenu;
import ViewController.Controller;

import java.util.*;

public class ViewOrdersManager extends Manager {

    private Sort currentSort;
    private List<BuyingLog> logs;
    private Customer customer = (Customer) account;

    public ViewOrdersManager(Account account) {
        super(account);
        logs = ((Customer) account).getAllLogs();
        if (!account.getUsername().equals(UtilTestObject.CUSTOMER))
            this.menu = new ViewOrdersMenu(this);
    }

    public ViewOrdersManager(Account account, Addresses address, Manager manager) {
        super(account, address, manager);
        logs = ((Customer) account).getAllLogs();
        Controller controller = loadFxml(Addresses.VIEW_ORDERS);
        update(controller);
    }

    @Override
    public void update(Controller controller) {
        controller.init();
    }

    public boolean canShowOrderWithId(String logId) {
        return customer.getLogById(logId) != null;
    }

    public boolean doesLogExist(String logId) {
        return customer.getLogById(logId) != null;
    }

    public String showOrderById(String logId) {
        return customer.getLogById(logId).toString();
    }

    public void rateProduct(String productId, int score) throws ViewCartManager.ProductDoNotExistAtAllException { //TODO RECHECK
        Product product = Product.getProductById(productId);
        if (product != null) product.addRate(customer, score);
        else throw new ViewCartManager.ProductDoNotExistAtAllException("Product does not exist");
    }

    private ArrayList<String> logsInShort() {
        ArrayList<String> logsInShort = new ArrayList<>();
        for (BuyingLog log : logs) {
            logsInShort.add(log.viewLogInShort());
        }
        return logsInShort;
    }

    public String showAvailableSorts() {
        return "money\n" +
                "date";
    }

    public ArrayList<Object> sort(String sort, boolean isAscending) {
        logs = ((Customer) account).getAllLogs();
        currentSort = new Sort(sort, isAscending);
        applySort();
        ArrayList<Object> objects = new ArrayList<>();
        objects.addAll(logs);
        return objects;
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

    public ArrayList<Object> disableSort() {
        currentSort = null;
        logs = ((Customer) account).getAllLogs();
        ArrayList<Object> objects = new ArrayList<>();
        objects.addAll(logs);
        return objects;
    }

    public BuyingLog getLogById(String id) {
        return customer.getLogById(id);
    }

    public ArrayList<BuyingLog> getLogs() {
        return customer.getAllLogs();
    }

    BuyingLog logToShowProducts;

    public void showProductsByLogId(String logId) {
        logToShowProducts = customer.getLogById(logId);
        Controller controller = loadFxml(Addresses.SHOW_ORDER_PRODUCTS,true);
        controller.init();
    }

    public HashMap<Product, Integer> getOrderProductsToShow() {
        HashMap<Product,Integer> orderProductsToShow= new HashMap<>();
        for (String productId : logToShowProducts.getProductIdToNumberMap().keySet()) {
            orderProductsToShow.put(Product.getProductById(productId),logToShowProducts.getProductIdToNumberMap().get(productId));
        }
        return orderProductsToShow;
    }
}
