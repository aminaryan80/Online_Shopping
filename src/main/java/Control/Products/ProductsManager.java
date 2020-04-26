package Control;

import Models.Account.Account;
import Models.Shop.Category;
import Models.Shop.Filter;
import Models.Shop.Sort;

import java.util.ArrayList;
import java.util.List;

public class ProductsManager extends Manager {

    public ProductsManager(Account account, Category currentCategory) {
        super(account);
        this.currentCategory = currentCategory;
    }

    private Category currentCategory;
    private List<Filter> filters;
    private Sort currentSort;

    // view categories
    public ArrayList<String> viewCategories() {

    }

    // filtering
    public String showAvailableFilters() {

    }

    public boolean isEnteredFilterFieldValid(String field) {

    }

    public ArrayList<String> applyFilter(String filterType, String filterValue) {

    }

    public List<String> currentFilters() {

    }

    public List<String> disableFilter(Filter filter) {

    }

    private Filter getFilterByField(String field) {

    }

    // sorting
    public String showAvailableSorts() {

    }

    public void sort(String sort) {

    }

    public void currentSort() {

    }

    public void disableSort() {

    }

    // show products
    public List<String> showProducts() {

    }

    // show product [productId]
    public void showProductById(String id) {

    }
}
