package Control;

import Models.Account.Account;
import Models.Account.Seller;
import Models.Shop.*;
import View.Menu;
import View.ProductsMenu;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductsManager extends Manager {

    public ProductsManager(Account account) {
        super(account);
        this.menu = new ProductsMenu(this);
        this.currentCategory = mainCategory;
        products = mainCategory.getAllProducts();
    }

    private Category currentCategory;
    private List<Filter> filters = new ArrayList<>();
    private Sort currentSort;
    private ArrayList<Product> products;

    // view categories
    public ArrayList<String> viewCategories() {
return null;
    }

    // filtering
    public String showAvailableFilters() {
        return "status\n" +
                "name\n" +
                "companyName\n" +
                "price\n" +
                "seller\n" +
                "isAvailable\n" +
                "category\n" +
                currentCategory.getFeaturesNames().toString();
    }

    public boolean isEnteredFilterFieldValid(String field) {
        if (field.equals("status") || field.equals("name") || field.equals("companyName") ||
        field.equals("price") || field.equals("seller") || field.equals("isAvailable") ||
        currentCategory.getFeaturesNames().contains(field)) {
            return true;
        }
        if (field.equals("category") && !currentCategory.getSubCategories().equals(null)) {
            return true;
        }
        return false;
    }

    public ArrayList<String> applyFilter(String filterType, String filterValue) {
        filters.add(new Filter(filterType, filterValue));
        products = mainCategory.getAllProducts();
        return setFilters();
    }

    private ArrayList<String> setFilters() {
        for (Filter filter : filters) {
            String field = filter.getField();
            if (field.equals("status")) {
                products = setStatusFilter(filter);
            } else if (field.equals("name")) {
                products = setNameFilter(filter);
            } else if (field.equals("companyName")) {
                products = setCompanyNameFilter(filter);
            } else if (field.equals("price")) {
                products = setPriceFilter(filter);
            } else if (field.equals("seller")) {
                products = setSellerFilter(filter);
            } else if (field.equals("isAvailable")) {
                products = setIsAvailableFilter(filter);
            } else if (field.equals("category")) {
                products = setCategoryFilter(filter);
            } else {
                products = setFeaturesFilter(filter);
            }
        }
        return productsInShort();
    }

    private ArrayList<String> productsInShort() {
        ArrayList<String> productsInShort= new ArrayList<String>();
        for (Product product : products) {
            productsInShort.add(product.viewProductInShort());
        }
        return productsInShort;
    }

    private ArrayList<Product> setFeaturesFilter(Filter filter) {
        return products.stream().filter(product -> {
            Feature feature = product.getFeatureByName(filter.getField());
            if (feature.getValue().equals(filter.getValue())) {
                return true;
            }
            return false;
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Product> setCategoryFilter(Filter filter) {
        return products.stream().filter(product -> {
            if (product.getCategory().equals(Category.getCategoryByName(filter.getValue()))) {
                return true;
            }
            return false;
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Product> setIsAvailableFilter(Filter filter) {
        return products.stream().filter(product -> {
            if (product.isAvailable() == Boolean.parseBoolean(filter.getValue())) {
                return true;
            }
            return false;
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Product> setSellerFilter(Filter filter) {
        return products.stream().filter(product -> {
            if (product.getSeller().equals((Seller) Account.getAccountByUsername(filter.getValue()))) {
                return true;
            }
            return false;
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Product> setPriceFilter(Filter filter) {
        return products.stream().filter(product -> {
            if (product.getPrice() == Double.parseDouble(filter.getValue())) {
                return true;
            }
            return false;
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Product>setCompanyNameFilter(Filter filter) {
        return products.stream().filter(product -> {
            if (product.getCompanyName().equals(filter.getValue())) {
                return true;
            }
            return false;
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Product>setNameFilter(Filter filter) {
        return products.stream().filter(product -> {
            if (product.getName().equals(filter.getValue())) {
                return true;
            }
            return false;
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Product> setStatusFilter(Filter filter) {
        return products.stream().filter(product -> {
            if (product.getStatus().equals(Product.parseProductStatus(filter.getValue()))) {
                return true;
            }
            return false;
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    public List<String> currentFilters() {
        ArrayList<String> filtersNames= new ArrayList<String>();
        for (Filter filter : filters) {
            filtersNames.add(filter.toString());
        }
        return filtersNames;
    }

    public List<String> disableFilter(String filterField) {
        Filter filter = getFilterByField(filterField);
        filters.remove(filter);
        products = mainCategory.getAllProducts();
        return setFilters();
    }

    private Filter getFilterByField(String field) {
        for (Filter filter : filters) {
            if (filter.getField().equals(field)) {
                return filter;
            }
        }
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
