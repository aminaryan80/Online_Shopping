package View;

import Control.AuctionPageManager;
import Control.Manager;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class AuctionPage extends Menu {

    public AuctionPage(Manager manager) {
        super(manager);
    }


    public void auctionPage() {

    }

    private void showOffs() {

    }

    private void showProductById(String id) {

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
        System.out.println(((AuctionPageManager) manager).showAvailableFilters());
    }

    private void applyFilter(String filterType) {
        if (!((AuctionPageManager) manager).isEnteredFilterFieldValid(filterType)) {
            ErrorProcessor.invalidEnteredInEditField();
            return;
        }
        System.out.println("enter value of the filter");
        String filterValue = scanner.nextLine();
        ArrayList<String> auctionsNames = ((AuctionPageManager) manager).applyFilter(filterType, filterValue);
        for (String auctionName : auctionsNames) {
            System.out.println(auctionName);
        }
    }

    private void currentFilters() {
        List<String> filtersNames = ((AuctionPageManager) manager).currentFilters();
        for (String filtersName : filtersNames) {
            System.out.println(filtersName);
        }
    }

    private void disableFilter(String filter) {
        if (!((AuctionPageManager) manager).isEnteredFilterFieldValid(filter)) {
            ErrorProcessor.invalidEnteredInEditField();
            return;
        }
        List<String> auctionsNames = ((AuctionPageManager) manager).disableFilter(filter);
        for (String auctionName : auctionsNames) {
            System.out.println(auctionName);
        }
    }
}
