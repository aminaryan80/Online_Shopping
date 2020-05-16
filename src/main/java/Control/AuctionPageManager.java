package Control;

import Models.Account.Account;
import Models.Shop.Off.Auction;
import Models.Shop.Category.Filter;
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

    // offs
    public List<String> showOffs() {
        return null;
    }

    // show product [productId]
    public void showProduct(String id) {

    }

    public String showAvailableFilters() {
        return "status\n" +
                "beginningDate\n" +
                "endingDate\n" +
                "discountAmount";
    }

    public List<String> currentFilters() {
        ArrayList<String> filtersNames= new ArrayList<String>();
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
        if (field.equals("status") || field.equals("beginningDate") || field.equals("endingDate") ||
                field.equals("discountAmount")){
            return true;
        }
        return false;
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
        return auctionsInShort();
    }

    private ArrayList<String> auctionsInShort() {
        ArrayList<String> auctionsNames = new ArrayList<>();
        for (Auction auction : auctions) {
            auctionsNames.add(auction.viewInShort());
        }
        return auctionsNames;
    }

    private ArrayList<Auction> setStatusFilter(Filter filter) {
        return auctions.stream().filter(auction -> {
            if (auction.getStatus().equals(Auction.parseAuctionStatus(filter.getValue()))) {
                return true;
            }
            return false;
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Auction> setBeginningDateFilter(Filter filter) {
        return auctions.stream().filter(auction -> {
            if (auction.getBeginningDate().equals(new Date(filter.getValue()))) {
                return true;
            }
            return false;
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Auction> setEndingDateFilter(Filter filter) {
        return auctions.stream().filter(auction -> {
            if (auction.getEndingDate().equals(new Date(filter.getValue()))) {
                return true;
            }
            return false;
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Auction> setDiscountAmountFilter(Filter filter) {
        return auctions.stream().filter(auction -> {
            if (auction.getDiscountAmount() == Double.parseDouble(filter.getValue())) {
                return true;
            }
            return false;
        }).collect(Collectors.toCollection(ArrayList::new));
    }
}
