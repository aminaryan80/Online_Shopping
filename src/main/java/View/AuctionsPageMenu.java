package View;

import Control.AuctionsPageManager;
import Control.CustomerManagers.ProductPageManager;
import Control.Manager;
import Models.Shop.Off.Auction;
import Models.Shop.Product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class AuctionsPageMenu extends Menu {

    public AuctionsPageMenu(Manager manager) {
        super(manager);
        auctionPage();
    }


    public void auctionPage() {
        Matcher matcher;
        showAuctionedProducts();
        while (true) {
            String input = scanner.nextLine();
            if (getMatcher(input, "^user panel$").find()) {
                openUserPanel(true);
            } else if ((matcher = getMatcher(input, "^show product (\\S+)$")).find()) {
                showProductById(matcher.group(1));
            } else if (getMatcher(input, "^filtering$").find()) {
                filtering();
            } else if (getMatcher(input, "^back$").find()) {
                return;
            } else if (getMatcher(input, "help").find()) {
                help();
            } else {
                ErrorProcessor.invalidInput();
            }
        }
    }

    private void showAuctionedProducts() {
        for (String string : Auction.getAuctionedProducts()) {
            System.out.println(string);
        }
    }

    private void showProductById(String id) {
        if (Product.hasProductWithId(id))
            new ProductPageManager(manager.getAccount(), Product.getProductById(id));
        else ErrorProcessor.invalidProductId();
    }

    private void help() {
        System.out.println("show product [productId]\n" +
                "filtering\n" +
                "sorting\n" +
                "user panel\n" +
                "help\n" +
                "back");
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
            } else ErrorProcessor.invalidInput();
        }
    }

    private void showAvailableFilters() {
        System.out.println(((AuctionsPageManager) manager).showAvailableFilters());
    }

    private void applyFilter(String filterType) {
        if (!((AuctionsPageManager) manager).isEnteredFilterFieldValid(filterType)) {
            ErrorProcessor.invalidEnteredInEditField();
            return;
        }
        System.out.println("enter value of the filter");
        String filterValue = scanner.nextLine();
        ArrayList<String> auctionsNames = ((AuctionsPageManager) manager).applyFilter(filterType, filterValue);
        for (String auctionName : auctionsNames) {
            System.out.println(auctionName);
        }
    }

    private void currentFilters() {
        List<String> filtersNames = ((AuctionsPageManager) manager).currentFilters();
        for (String filtersName : filtersNames) {
            System.out.println(filtersName);
        }
    }

    private void disableFilter(String filter) {
        if (!((AuctionsPageManager) manager).isEnteredFilterFieldValid(filter)) {
            ErrorProcessor.invalidEnteredInEditField();
            return;
        }
        List<String> auctionsNames = ((AuctionsPageManager) manager).disableFilter(filter);
        for (String auctionName : auctionsNames) {
            System.out.println(auctionName);
        }
    }
}
