package Control;

import Models.Account.Account;
import Models.Shop.Category;
import Models.Shop.Filter;
import Models.Shop.Sort;
import View.Menu;
import View.ProductsMenu;

import java.util.ArrayList;
import java.util.List;

public class ProductsManager extends Manager {

    public ProductsManager(Account account, Category currentCategory) {
        super(account);
        this.menu = new ProductsMenu(this,currentCategory);
        this.currentCategory = currentCategory;
    }

    private Category currentCategory;
    private List<Filter> filters;
    private Sort currentSort;

    // view categories
    public ArrayList<String> viewCategories() {
return null;
    }

    // filtering
    public String showAvailableFilters() {
        return null;
    }

    public boolean isEnteredFilterFieldValid(String field) {
        return true;
    }

    public ArrayList<String> applyFilter(String filterType, String filterValue) {
        return null;
    }

    public List<String> currentFilters() {
        return null;
    }

    public List<String> disableFilter(Filter filter) {
        return null;
    }

    private Filter getFilterByField(String field) {
        return null;
    }

    // sorting
    public String showAvailableSorts() {
        return null;
    }

    public void sort(String sort) {

    }

    public void currentSort() {

    }

    public void disableSort() {

    }

    // show products
    public List<String> showProducts() {
        return null;
    }

    // show product [productId]
    public void showProductById(String id) {

    }
}
