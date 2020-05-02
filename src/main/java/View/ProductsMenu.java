package View;

import Control.AuctionPageManager;
import Control.Manager;
import Control.ProductsManager;
import Models.Shop.Category;
import Models.Shop.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class ProductsMenu extends Menu {

    public ProductsMenu(Manager manager) {
        super(manager);
    }

    public void productsMenu() {

    }

    public void viewCategories() {

    }

    private void filtering() {
        Matcher matcher;
        while (true) {
            String command = scanner.nextLine();
            if ((matcher = getMatcher(command, "show available filters")).find()) {
                showAvailableFilters();
            } else if ((matcher = getMatcher(command, "filter (\\w+)")).find()) {
                applyFilter(matcher.group(1));
            } else if ((matcher = getMatcher(command, "current filters")).find()) {
                currentFilters();
            } else if ((matcher = getMatcher(command, "disable filter (\\w+)")).find()) {
                disableFilter(matcher.group(1));
            } else if ((matcher = getMatcher(command, "help")).find()) {
                System.out.println("show available filters\n" +
                        "filter (an available filter)\n" +
                        "current filters\n" +
                        "disable filter\n" +
                        "back");
            } else if ((matcher = getMatcher(command, "back")).find()) {
                return;
            } else {
                ErrorProcessor.invalidInput();
            }
        }
    }

    private void showAvailableFilters() {
        System.out.println(((ProductsManager) manager).showAvailableFilters());
    }

    private void applyFilter(String filterType) {
        if (!((ProductsManager) manager).isEnteredFilterFieldValid(filterType)) {
            ErrorProcessor.invalidEnteredInEditField();
            return;
        }
        System.out.println("enter value of the filter");
        String filterValue = scanner.nextLine();
        ArrayList<String> productsNames = ((ProductsManager) manager).applyFilter(filterType, filterValue);
        for (String productsName : productsNames) {
            System.out.println(productsName);
        }
    }

    private void currentFilters() {
        List<String> filtersNames = ((ProductsManager) manager).currentFilters();
        for (String filtersName : filtersNames) {
            System.out.println(filtersName);
        }
    }

    private void disableFilter(String filter) {
        if (!((ProductsManager) manager).isEnteredFilterFieldValid(filter)) {
            ErrorProcessor.invalidEnteredInEditField();
            return;
        }
        List<String> productsNames = ((ProductsManager) manager).disableFilter(filter);
        for (String productsName : productsNames) {
            System.out.println(productsName);
        }
    }

    private void sorting() {
        Matcher matcher;
        while (true) {
            String command = scanner.nextLine();
            if ((matcher = getMatcher(command, "show available sorts")).find()) {
                showAvailableSorts();
            } else if ((matcher = getMatcher(command, "sort (\\w+)")).find()) {
                sort(matcher.group(1));
            } else if ((matcher = getMatcher(command, "current sort")).find()) {
                currentSort();
            } else if ((matcher = getMatcher(command, "disable sort")).find()) {
                disableSort();
            } else if ((matcher = getMatcher(command, "help")).find()) {
                System.out.println("show available sorts\n" +
                        "sort (an available sort)\n" +
                        "current sort\n" +
                        "disable sort\n" +
                        "help\n" +
                        "back");
            } else if ((matcher = getMatcher(command, "back")).find()) {
                return;
            } else {
                ErrorProcessor.invalidInput();
            }
        }
    }

    private void showAvailableSorts() {
        System.out.println(((ProductsManager) manager).showAvailableSorts());
    }

    private void sort(String sort) {
        if (!((ProductsManager) manager).isEnteredSortFieldValid(sort)) {
            ErrorProcessor.invalidInput();
            return;
        }
        System.out.println("do you want the sort to be ascending?(type true or false)");
        String sortValue = scanner.nextLine();
        ArrayList<String> productsNames = ((ProductsManager) manager).sort(sort, Boolean.parseBoolean(sortValue));
        for (String productsName : productsNames) {
            System.out.println(productsName);
        }
    }

    private void currentSort() {
        System.out.println(((ProductsManager) manager).currentSort());
    }

    private void disableSort() {
        ArrayList<String> productsNames = ((ProductsManager) manager).disableSort();
        for (String productsName : productsNames) {
            System.out.println(productsName);
        }
    }

    private void showProducts() {

    }

    private void showProduct(String id) {

    }

}
