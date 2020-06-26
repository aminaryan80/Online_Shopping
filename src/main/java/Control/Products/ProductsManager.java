package Control.Products;

import Control.CustomerManagers.ProductPageManager;
import Control.Manager;
import Models.Account.Account;
import Models.Shop.Category.*;
import Models.Shop.Off.Auction;
import Models.Shop.Product.Product;
import View.Products.ProductsMenu;
import ViewController.Controller;
import ViewController.products.AuctionDetailsController;
import ViewController.products.FilteringController;
import ViewController.products.ProductsController;
import ViewController.products.ViewCategoriesController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ProductsManager extends Manager {

    private Category currentCategory;
    private List<Filter> filters = new ArrayList<>();
    private List<LengthFilter> lengthFilters = new ArrayList<>();
    private Sort currentSort = null;
    private List<Product> products;
    private boolean isOffMenu;

    public ProductsManager(Account account) {
        super(account);
        this.currentCategory = mainCategory;
        products = Product.getAllProducts();
        this.menu = new ProductsMenu(this, productsInShort());
    }

    public ProductsManager(Account account, Addresses address, Manager manager, boolean isOffMenu) {
        super(account, address, manager);
        this.currentCategory = mainCategory;
        this.products = Product.getAllProducts();
        this.isOffMenu = isOffMenu;
        Controller controller = loadFxml(Addresses.PRODUCTS_MENU);
        update(controller);
    }

    public void update(Controller c) {
        ProductsController controller = (ProductsController) c;
        if (isOffMenu) {
            ArrayList<Product> offProducts = new ArrayList<>();
            for (Auction auction : Auction.getAllAuctions()) {
                offProducts.addAll(auction.getProducts());
            }
            controller.setProducts(offProducts);
        } else controller.setProducts(products);
        controller.setCategory(mainCategory);
        controller.init();
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

    public ArrayList<String> getFilterTypes() {
        ArrayList<String> filterTypes = new ArrayList<>();
        filterTypes.add("status");
        filterTypes.add("name");
        filterTypes.add("companyName");
        filterTypes.add("price");
        filterTypes.add("seller");
        filterTypes.add("isAvailable");
        filterTypes.add("category");
        filterTypes.addAll(currentCategory.getFeaturesNames());
        return filterTypes;
    }

    public boolean hasFeatureWithName(String name) {
        if (currentCategory == getMainCategory()) {
            return false;
        }
        return currentCategory.getFeaturesNames().contains(name);
    }

    public boolean isEnteredFilterFieldValid(String field) {
        for (Filter filter : filters) {
            if (filter.getField().equals(field)) {
                return false;
            }
        }
        if (field.equals("status") || field.equals("name") || field.equals("companyName") ||
                field.equals("price") || field.equals("seller") || field.equals("isAvailable")) {
            return true;
        }
        if (!currentCategory.equals(mainCategory) && currentCategory.getFeaturesNames().contains(field))
            return true;
        return field.equals("category") && currentCategory.getSubCategories() != null;
    }

    public ArrayList<Object> applyFilter(String filterType, String filterValue) {
        filters.add(new Filter(filterType, filterValue));
        if (filterType.equals("category")) {
            currentCategory = Category.getCategoryByName(filterValue);
        }
        products = Product.getAllProducts();
        setFilters();
        applySort();
        ArrayList<Object> objects = new ArrayList<>();
        objects.addAll(products);
        return objects;
    }

    public ArrayList<String> applyFilter(String filterType, String minValue, String maxValue) {
        lengthFilters.add(new LengthFilter(filterType, minValue, maxValue));
        products = Product.getAllProducts();
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
        for (LengthFilter lengthFilter : lengthFilters) {
            if (lengthFilter.getField().equals("price")) {
                setPriceLengthFilter(lengthFilter);
            }
        }
    }

    private ArrayList<Product> setPriceLengthFilter(LengthFilter filter) {
        return products.stream().filter(product -> {
            return product.getPrice() <= Double.parseDouble(filter.getMaxValue()) || product.getPrice() >= Double.parseDouble(filter.getMinValue());
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<String> productsInShort() {
        ArrayList<String> productsInShort = new ArrayList<>();
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

    private ArrayList<Product> setCompanyNameFilter(Filter filter) {
        return products.stream().filter(product -> {
            return product.getCompanyName().equals(filter.getValue());
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Product> setNameFilter(Filter filter) {
        return products.stream().filter(product -> {
            return product.getName().equals(filter.getValue());
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Product> setStatusFilter(Filter filter) {
        return products.stream().filter(product -> {
            return product.getStatus().equals(Product.parseProductStatus(filter.getValue()));
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Filter> currentFilters() {
        return filters;
    }

    public boolean isItSelectedFilter(String filterName) {
        for (Filter filter : filters) {
            if (filter.getField().equals(filterName)) {
                return true;
            }
        }
        for (LengthFilter lengthFilter : lengthFilters) {
            if (lengthFilter.getField().equals(filterName)) {
                return true;
            }
        }
        return false;
    }

    public boolean isEnteredLengthFilterFieldValid(String field) {
        return field.equals("price");
    }

    public String showAvailableLengthFilter() {
        return "price";
    }

    public ArrayList<Object> disableFilter(String filterField) {
        Object filter = getFilterByField(filterField);
        if (filter instanceof Filter) {
            filters.remove(filter);
        } else {
            lengthFilters.remove(filter);
        }
        products = Product.getAllProducts();
        if (filterField.equals("category")) {
            currentCategory = mainCategory;
        }
        setFilters();
        applySort();
        ArrayList<Object> objects = new ArrayList<>();
        objects.addAll(products);
        return objects;
    }

    private Object getFilterByField(String field) {
        for (Filter filter : filters) {
            if (filter.getField().equals(field)) {
                return filter;
            }
        }
        for (LengthFilter lengthFilter : lengthFilters) {
            if (lengthFilter.getField().equals(field)) {
                return lengthFilter;
            }
        }
        return null;
    }

    // sorting
    public String showAvailableSorts() {
        return "price\n" +
                "name\n" +
                "rating\n" +
                "features";
    }

    public ArrayList<Object> sort(String sort, boolean isAscending) {
        products = Product.getAllProducts();
        setFilters();
        currentSort = new Sort(sort, isAscending);
        applySort();
        ArrayList<Object> objects = new ArrayList<>();
        objects.addAll(products);
        return objects;
    }

    private void applySort() {
        if (currentSort == null) {
            return;
        }
        String field = currentSort.getField();
        if (field.equals("price")) {
            sortByPrice();
        } else if (field.equals("name")) {
            sortByName();
        } else if (field.equals("rating")) {
            sortByRating();
        } else {
            sortByFeature();
        }
        if (!currentSort.isAscending()) {
            Collections.reverse(products);
        }
    }

    public void openFilter(Controller controller) {
        Controller myController = loadFxml(Addresses.FILTER, true, this);
        ((FilteringController) myController).init(controller);
    }

    public ArrayList<String> getSortFields() {
        ArrayList<String> fields = new ArrayList<>();
        fields.add("price");
        fields.add("name");
        fields.add("rating");
        fields.addAll(currentCategory.getFeaturesNames());
        return fields;
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

    private void sortByFeature() {
        Product[] productsForSort = products.toArray(new Product[0]);
        for (int i = 0; i < productsForSort.length; i++) {
            for (int j = i + 1; j < productsForSort.length; j++) {
                if (productsForSort[i].getFeatureByName(currentSort.getField()).getValue().compareTo(productsForSort[j].getFeatureByName(currentSort.getField()).getValue()) > 0) {
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
        return field.equals("price") || field.equals("name") || field.equals("rating") || field.equals("features");
    }

    public String currentSort() {
        return currentSort.toString();
    }

    public ArrayList<Object> disableSort() {
        currentSort = null;
        products = Product.getAllProducts();
        setFilters();
        ArrayList<Object> objects = new ArrayList<>();
        objects.addAll(products);
        return objects;
    }

    public boolean hasProductWithId(String id) {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    // show products
    public List<String> showProducts() {
        products = currentCategory.getAllProducts();
        setFilters();
        applySort();
        return productsInShort();
    }

    // show product [productId]
    public void openProductPage(String id) {
        new ProductPageManager(account, Product.getProductById(id), Addresses.PRODUCTS_MENU, this);
    }

    public void viewCategories() {
        ViewCategoriesController controller = (ViewCategoriesController) loadFxml(Addresses.VIEW_CATEGORIES, true);
        controller.init();
    }

    public void openAuctionDetails(Auction auction, Product product) {
        AuctionDetailsController controller = (AuctionDetailsController) loadFxml(Addresses.AUCTION_DETAILS, true);
        controller.setInfos(auction, product);
    }
}
