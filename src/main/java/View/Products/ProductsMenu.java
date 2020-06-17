package View.Products;

import Control.Manager;
import Control.Products.ProductsManager;
import Models.Shop.Product.Product;
import View.ErrorProcessor;
import View.Menu;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class ProductsMenu extends Menu {

    public ProductsMenu(Manager manager, ArrayList<String> products) {
        super(manager);
        for (String product : products) {
            System.out.println(product);
        }
        productsMenu();
    }

    public void productsMenu() {
        while (true) {
            Matcher matcher;
            String input = scanner.nextLine();
            if (getMatcher(input, "^user panel$").find()) {
                openUserPanel(true);
            } else if (getMatcher(input, "^view categories$").find()) {
                showCategories();
            } else if (getMatcher(input, "^filtering$").find()) {
                filtering();
            } else if (getMatcher(input, "^sorting$").find()) {
                sorting();
            } else if (getMatcher(input, "^show products$").find()) {
                showProducts();
            } else if ((matcher = getMatcher(input, "^show product (\\S+)$")).find()) {
                showProduct(matcher.group(1));
            } else if (getMatcher(input, "^help$").find()) {
                help();
            } else if (getMatcher(input, "^back$").find()) {
                return;
            } else {
                ErrorProcessor.invalidInput();
            }
        }
    }

    public void showCategories() {
        System.out.println(manager.showCategories());
    }

    private void filtering() {
        Matcher matcher;
        while (true) {
            String command = scanner.nextLine();
            if (command.equals("show available filters")) {
                showAvailableFilters();
            } else if ((matcher = getMatcher(command, "filter (\\S+)")).matches()) {
                applyFilter(matcher.group(1));
            } else if (command.equals("current filters")) {
                currentFilters();
            } else if (command.equals("show available lengthfilters")) {
                showAvailableLengthFilter();
            } else if ((matcher = getMatcher(command, "lengthfilter (\\S+)")).matches()) {
                lengthFilter(matcher.group(1));
            } else if ((matcher = getMatcher(command, "disable filter (\\S+)")).matches()) {
                    disableFilter(matcher.group(1));
            } else if (command.equals("help")) {
                    filteringHelp();
            } else if (command.equals("back")) {
                    return;
            } else {
                    ErrorProcessor.invalidInput();
            }
        }
    }

    private void filteringHelp() {
        System.out.println("show available filters\n" +
                "filter [an available filter]\n" +
                "show available lengthFilters\n" +
                "lengthFilter [an available filter]\n" +
                "current filters\n" +
                "disable filter [a selected filter]\n" +
                "help\n" +
                "back");
    }

    private void lengthFilter(String field) {
        if (((ProductsManager) manager).isEnteredLengthFilterFieldValid(field)) {
            System.out.println("enter the minimum value of the filter");
            String minFilterValue = scanner.nextLine();
            System.out.println("enter the maximum value of the filter");
            String maxFilterValue = scanner.nextLine();
            ArrayList<String> filteredProducts = ((ProductsManager) manager).applyFilter(field, minFilterValue, maxFilterValue);
            for (String filteredProduct : filteredProducts) {
                System.out.println(filteredProduct);
            }
        } else {
            ErrorProcessor.invalidInput();
        }
    }

    private void showAvailableLengthFilter() {
        System.out.println(((ProductsManager) manager).showAvailableLengthFilter());
    }

    private void showAvailableFilters() {
        System.out.println(((ProductsManager) manager).showAvailableFilters());
    }

    private void applyFilter(String filterType) {
        if (((ProductsManager) manager).isEnteredFilterFieldValid(filterType)) {
            System.out.println("enter the value of the filter");
            String filterValue = scanner.nextLine();
            ArrayList<String> filteredProducts = ((ProductsManager) manager).applyFilter(filterType, filterValue);
            for (String filteredProduct : filteredProducts) {
                System.out.println(filteredProduct);
            }
        } else {
            ErrorProcessor.invalidInput();
        }
    }

    private void currentFilters() {
        List<String> currentFilters = ((ProductsManager) manager).currentFilters();
        for (String currentFilter : currentFilters) {
            System.out.println(currentFilter);
        }
    }

    private void disableFilter(String filter) {
        if (((ProductsManager) manager).isItSelectedFilter(filter)) {
            List<String> products = ((ProductsManager) manager).disableFilter(filter);
            for (String product : products) {
                System.out.println(product);
            }
        } else {
            ErrorProcessor.invalidInput();
        }
    }

    private void sorting() {
        while (true) {
            Matcher matcher;
            String command = scanner.nextLine();
            if (command.equals("show available sorts")) {
                showAvailableSorts();
            } else if ((matcher = getMatcher(command, "sort (\\S+)")).find()) {
                sort(matcher.group(1));
            } else if (command.equals("current sort")) {
                currentSort();
            } else if (command.equals("disable sort")) {
                disableSort();
            } else if (command.equals("help")) {
                sortingHelp();
            } else if (command.equals("back")) {
                return;
            }
        }
    }

    private void sortingHelp() {
        System.out.println("show available sorts\n" +
                "sort [an available sort]\n" +
                "current sort\n" +
                "disable sort\n" +
                "help\n" +
                "back");
    }

    private void showAvailableSorts() {
        System.out.println(((ProductsManager) manager).showAvailableSorts());
    }

    private void sort(String sort) {
        if (((ProductsManager) manager).isEnteredSortFieldValid(sort)) {
            System.out.println("do you want it to be ascending (answer with true or false)");
            String isAscending = scanner.nextLine();
            if (sort.equals("features")) {
                System.out.println("enter the feature you want to sort by");
                String temp = scanner.nextLine();
                if (((ProductsManager) manager).hasFeatureWithName(temp)) {
                    sort = temp;
                } else {
                    ErrorProcessor.invalidInput();
                }
            }
            ArrayList<Product> sortedProducts = ((ProductsManager) manager).sort(sort, Boolean.parseBoolean(isAscending));
            for (Product sortedProduct : sortedProducts) {
                System.out.println(sortedProduct.viewProductInShort());
            }
        } else {
            ErrorProcessor.invalidInput();
        }
    }

    private void currentSort() {
        System.out.println(((ProductsManager) manager).currentSort());
    }

    private void disableSort() {
        ArrayList<String> products = ((ProductsManager) manager).disableSort();
        for (String product : products) {
            System.out.println(product);
        }
    }

    private void showProducts() {
        List<String> products = ((ProductsManager) manager).showProducts();
        for (String product : products) {
            System.out.println(product);
        }
    }

    private void showProduct(String id) {
        if (((ProductsManager) manager).hasProductWithId(id)) {
            ((ProductsManager) manager).showProductById(id);
        } else {
            ErrorProcessor.invalidInput();
        }
    }

    private void help() {
        System.out.println("view categories\n" +
                "filtering\n" +
                "sorting\n" +
                "show products\n" +
                "show product [productId]\n" +
                "user panel\n" +
                "help\n" +
                "back");
    }
}
