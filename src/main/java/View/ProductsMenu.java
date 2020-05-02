package View;

import Control.Manager;
import Control.ProductsManager;
import Models.Shop.Category;
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
        List<String> productsNames = ((ProductsManager) manager).disableFilter(filter);
        for (String productsName : productsNames) {
            System.out.println(productsName);
        }
    }

    private void showAvailableSorts() {

    }

    private void sort(String sort) {

    }

    private void currentSort() {

    }

    private void disableSort() {

    }

    private void showProducts() {

    }

    private void showProduct(String id) {

    }

}
