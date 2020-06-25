package View;

import Control.AuctionsPageManager;
import Control.Manager;
import Models.Shop.Product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class AuctionsPageMenu extends Menu {

    public AuctionsPageMenu(Manager manager, ArrayList<String> products) {
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
        if (((AuctionsPageManager) manager).isEnteredLengthFilterFieldValid(field)) {
            System.out.println("enter the minimum value of the filter");
            String minFilterValue = scanner.nextLine();
            System.out.println("enter the maximum value of the filter");
            String maxFilterValue = scanner.nextLine();
            ArrayList<String> filteredProducts = ((AuctionsPageManager) manager).applyFilter(field, minFilterValue, maxFilterValue);
            for (String filteredProduct : filteredProducts) {
                System.out.println(filteredProduct);
            }
        } else {
            ErrorProcessor.invalidInput();
        }
    }

    private void showAvailableLengthFilter() {
        System.out.println(((AuctionsPageManager) manager).showAvailableLengthFilter());
    }

    private void showAvailableFilters() {
        System.out.println(((AuctionsPageManager) manager).showAvailableFilters());
    }

    private void applyFilter(String filterType) {
        if (((AuctionsPageManager) manager).isEnteredFilterFieldValid(filterType)) {
            System.out.println("enter the value of the filter");
            String filterValue = scanner.nextLine();
            ArrayList<String> filteredProducts = ((AuctionsPageManager) manager).applyFilter(filterType, filterValue);
            for (String filteredProduct : filteredProducts) {
                System.out.println(filteredProduct);
            }
        } else {
            ErrorProcessor.invalidInput();
        }
    }

    private void currentFilters() {
        List<String> currentFilters = ((AuctionsPageManager) manager).currentFilters();
        for (String currentFilter : currentFilters) {
            System.out.println(currentFilter);
        }
    }

    private void disableFilter(String filter) {
        if (((AuctionsPageManager) manager).isItSelectedFilter(filter)) {
            List<String> products = ((AuctionsPageManager) manager).disableFilter(filter);
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
        System.out.println(((AuctionsPageManager) manager).showAvailableSorts());
    }

    private void sort(String sort) {
        if (((AuctionsPageManager) manager).isEnteredSortFieldValid(sort)) {
            System.out.println("do you want it to be ascending (answer with true or false)");
            String isAscending = scanner.nextLine();
            if (sort.equals("features")) {
                System.out.println("enter the feature you want to sort by");
                String temp = scanner.nextLine();
                if (((AuctionsPageManager) manager).hasFeatureWithName(temp)) {
                    sort = temp;
                } else {
                    ErrorProcessor.invalidInput();
                }
            }
            ArrayList<Object> sortedProducts = ((AuctionsPageManager) manager).sort(sort, Boolean.parseBoolean(isAscending));
            for (Object sortedProduct : sortedProducts) {
                System.out.println(((Product)sortedProduct).viewProductInShort());
            }
        } else {
            ErrorProcessor.invalidInput();
        }
    }

    private void currentSort() {
        System.out.println(((AuctionsPageManager) manager).currentSort());
    }

    private void disableSort() {
        ArrayList<Object> products = ((AuctionsPageManager) manager).disableSort();
        for (Object product : products) {
            System.out.println(product);
        }
    }

    private void showProducts() {
        List<String> products = ((AuctionsPageManager) manager).showProducts();
        for (String product : products) {
            System.out.println(product);
        }
    }

    private void showProduct(String id) {
        if (((AuctionsPageManager) manager).hasProductWithId(id)) {
            ((AuctionsPageManager) manager).showProductById(id);
        } else {
            ErrorProcessor.invalidInput();
        }
    }

    private void help() {
        System.out.println("filtering\n" +
                "sorting\n" +
                "show products\n" +
                "show product [productId]\n" +
                "user panel\n" +
                "help\n" +
                "back");
    }
}
