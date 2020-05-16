package Control.Products;

import Control.Manager;
import Models.Account.Account;
import Models.Shop.Category.Category;
import Models.Shop.Category.Feature;
import Models.Shop.Category.Filter;
import Models.Shop.Category.Sort;
import Models.Shop.Product.Product;
import View.Products.ProductsMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ProductsManager extends Manager {

    public ProductsManager(Account account) {
        super(account);
        this.currentCategory = mainCategory;
        products = mainCategory.getAllProducts();
        this.menu = new ProductsMenu(this);
    }

    private Category currentCategory;
    private List<Filter> filters = new ArrayList<>();
    private Sort currentSort = null;
    private List<Product> products;

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
        return field.equals("category") && !currentCategory.getSubCategories().equals(null);
    }

    public ArrayList<String> applyFilter(String filterType, String filterValue) {
        filters.add(new Filter(filterType, filterValue));
        products = mainCategory.getAllProducts();
        setFilters();
        applySort();
        return productsInShort();
    }

    private void setFilters() {
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
    }

    private ArrayList<String> productsInShort() {
        ArrayList<String> productsInShort= new ArrayList<>();
        for (Product product : products) {
            productsInShort.add(product.viewProductInShort());
        }
        return productsInShort;
    }

    private ArrayList<Product> setFeaturesFilter(Filter filter) {
        return products.stream().filter(product -> {
            Feature feature = product.getFeatureByName(filter.getField());
            return feature.getValue().equals(filter.getValue());
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Product> setCategoryFilter(Filter filter) {
        return products.stream().filter(product -> {
            return product.getCategory().equals(Category.getCategoryByName(filter.getValue()));
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Product> setIsAvailableFilter(Filter filter) {
        return products.stream().filter(product -> {
            return product.isAvailable() == Boolean.parseBoolean(filter.getValue());
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Product> setSellerFilter(Filter filter) {
        return products.stream().filter(product -> {
            return product.getSeller().equals(Account.getAccountByUsername(filter.getValue()));
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Product> setPriceFilter(Filter filter) {
        return products.stream().filter(product -> {
            return product.getPrice() == Double.parseDouble(filter.getValue());
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Product>setCompanyNameFilter(Filter filter) {
        return products.stream().filter(product -> {
            return product.getCompanyName().equals(filter.getValue());
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Product>setNameFilter(Filter filter) {
        return products.stream().filter(product -> {
            return product.getName().equals(filter.getValue());
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Product> setStatusFilter(Filter filter) {
        return products.stream().filter(product -> {
            return product.getStatus().equals(Product.parseProductStatus(filter.getValue()));
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
        setFilters();
        applySort();
        return productsInShort();
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
        return "price\n" +
                "name\n" +
                "rating";
    }

    public ArrayList<String> sort(String sort, boolean isAscending) {
        products = mainCategory.getAllProducts();
        setFilters();
        currentSort = new Sort(sort, isAscending);
        applySort();
        return productsInShort();
    }

    private void applySort() {
        String field = currentSort.getField();
        if (field.equals("price")) {
            sortByPrice();
        } else if (field.equals("name")) {
            sortByName();
        } else {
            sortByRating();
        }
        if (!currentSort.isAscending()) {
            Collections.reverse(products);
        }
    }

    private void sortByPrice() {
        Product[] productsForSort = products.toArray(new Product[0]);
        for (int i = 0; i < productsForSort.length; i++) {
            for (int j = i + 1; j < productsForSort.length; j++) {
                if (productsForSort[i].getPrice() > productsForSort[j].getPrice()) {
                    Product temp = productsForSort[i];
                    productsForSort[i] = productsForSort[j];
                    productsForSort[j] = temp;
                }
            }
        }
        products = Arrays.asList(productsForSort);
    }

    private void sortByName() {
        Product[] productsForSort = products.toArray(new Product[0]);
        for (int i = 0; i < productsForSort.length; i++) {
            for (int j = i + 1; j < productsForSort.length; j++) {
                if (productsForSort[i].getName().compareTo(productsForSort[j].getName()) > 0) {
                    Product temp = productsForSort[i];
                    productsForSort[i] = productsForSort[j];
                    productsForSort[j] = temp;
                }
            }
        }
        products = Arrays.asList(productsForSort);
    }

    private void sortByRating() {
        Product[] productsForSort = products.toArray(new Product[0]);
        for (int i = 0; i < productsForSort.length; i++) {
            for (int j = i + 1; j < productsForSort.length; j++) {
                if (productsForSort[i].getRate() > productsForSort[j].getRate()) {
                    Product temp = productsForSort[i];
                    productsForSort[i] = productsForSort[j];
                    productsForSort[j] = temp;
                }
            }
        }
        products = Arrays.asList(productsForSort);
    }

    public boolean isEnteredSortFieldValid(String field) {
        return field.equals("price") || field.equals("name") || field.equals("rating");
    }

    public String currentSort() {
        return currentSort.toString();
    }

    public ArrayList<String> disableSort() {
        currentSort = null;
        products = mainCategory.getAllProducts();
        setFilters();
        applySort();
        return productsInShort();
    }

    // show products
    public List<String> showProducts() {
        return null;
    }

    // show product [productId]
    public void showProductById(String id) {

    }
}
