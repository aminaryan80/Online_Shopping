package Control;

import Models.Account.Account;
import Models.Shop.Category.Filter;
import Models.Shop.Off.Auction;
import View.AuctionPage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AuctionPageManager extends Manager {

    private List<Filter> filters = new ArrayList<>();
    private List<Auction> auctions = Auction.getAllAuctions();

    public AuctionPageManager(Account account) {
        super(account);
        this.menu = new AuctionPage(this);
    }

    public String showAvailableFilters() {
        return "status\n" +
                "beginningDate\n" +
                "endingDate\n" +
                "discountAmount";
    }

    public List<String> currentFilters() {
        ArrayList<String> filtersNames = new ArrayList<>();
        for (Filter filter : filters) {
            filtersNames.add(filter.toString());
        }
        return filtersNames;
    }

    private Filter getFilterByField(String field) {
        for (Filter filter : filters) {
            if (filter.getField().equals(field)) {
                return filter;
            }
        }
        return null;
    }

    public List<String> disableFilter(String filterField) {
        Filter filter = getFilterByField(filterField);
        filters.remove(filter);
        auctions = Auction.getAllAuctions();
        return setFilters();
    }

    public ArrayList<String> applyFilter(String filterType, String filterValue) {
        filters.add(new Filter(filterType, filterValue));
        auctions = Auction.getAllAuctions();
        return setFilters();
    }

    public boolean isEnteredFilterFieldValid(String field) {
        return field.equals("status") || field.equals("beginningDate") || field.equals("endingDate") ||
                field.equals("discountAmount");
    }

    private ArrayList<String> setFilters() {
        for (Filter filter : filters) {
            String filed = filter.getField();
            if (filed.equals("status")) {
                auctions = setStatusFilter(filter);
            } else if (filed.equals("beginningDate")) {
                auctions = setBeginningDateFilter(filter);
            } else if (filed.equals("endingDate")) {
                auctions = setEndingDateFilter(filter);
            } else {
                auctions = setDiscountAmountFilter(filter);
            }
        }
        return productsInShort();
    }

    private ArrayList<String> productsInShort() {
        ArrayList<String> products = new ArrayList<>();
        for (Auction auction : auctions) {
            products.addAll(auction.getAuctionProducts());
        }
        return products;
    }

    private ArrayList<Auction> setStatusFilter(Filter filter) {
        return auctions.stream().filter(auction -> {
            return auction.getStatus().equals(Auction.parseAuctionStatus(filter.getValue()));
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Auction> setBeginningDateFilter(Filter filter) {
        return auctions.stream().filter(auction -> {
            return auction.getBeginningDate().equals(new Date(filter.getValue()));
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Auction> setEndingDateFilter(Filter filter) {
        return auctions.stream().filter(auction -> {
            return auction.getEndingDate().equals(new Date(filter.getValue()));
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Auction> setDiscountAmountFilter(Filter filter) {
        return auctions.stream().filter(auction -> {
            return auction.getDiscountAmount() == Double.parseDouble(filter.getValue());
        }).collect(Collectors.toCollection(ArrayList::new));
    }
}
