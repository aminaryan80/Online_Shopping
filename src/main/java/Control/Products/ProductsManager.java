package Control.Products;

import Control.AuctionsPageManager;
import Control.CustomerManagers.ProductPageManager;
import Control.Manager;
import Models.Account.Account;
import Models.Shop.Category.*;
import Models.Shop.Off.Auction;
import Models.Shop.Product.Product;
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

    public ArrayList<Object> applyFilter(String filterType, String filterValue) {
        filters.add(new Filter(filterType, filterValue));
        if (filterType.equals("category")) {
            currentCategory = Category.getCategoryByName(filterValue);
        }
        products = Product.getAllProducts();
        setFilters();
        applySort();
        return new ArrayList<>(products);
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

    private void setPriceLengthFilter(LengthFilter filter) {
        products.stream().filter(product -> product.getPrice() <= Double.parseDouble(filter.getMaxValue()) || product.getPrice() >= Double.parseDouble(filter.getMinValue())).collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Product> setFeaturesFilter(Filter filter) {
        return products.stream().filter(product -> {
            Feature feature = product.getFeatureByName(filter.getField());
            return feature.getValue().equals(filter.getValue());
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Product> setCategoryFilter(Filter filter) {
        return products.stream().filter(product -> product.getCategory().equals(Category.getCategoryByName(filter.getValue()))).collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Product> setIsAvailableFilter(Filter filter) {
        return products.stream().filter(product -> product.isAvailable() == Boolean.parseBoolean(filter.getValue())).collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Product> setSellerFilter(Filter filter) {
        return products.stream().filter(product -> product.getSeller().equals(Account.getAccountByUsername(filter.getValue()))).collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Product> setPriceFilter(Filter filter) {
        return products.stream().filter(product -> product.getPrice() == Double.parseDouble(filter.getValue())).collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Product> setCompanyNameFilter(Filter filter) {
        return products.stream().filter(product -> product.getCompanyName().equals(filter.getValue())).collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Product> setNameFilter(Filter filter) {
        return products.stream().filter(product -> product.getName().equals(filter.getValue())).collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Product> setStatusFilter(Filter filter) {
        return products.stream().filter(product -> product.getStatus().equals(Product.parseProductStatus(filter.getValue()))).collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Filter> currentFilters() {
        return filters;
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
        return new ArrayList<>(products);
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
        return new ArrayList<>(products);
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
        Controller myController;
        if (isOffMenu) {
            myController = loadFxml(Addresses.FILTER, true, new AuctionsPageManager(null));
        } else {
            myController = loadFxml(Addresses.FILTER, true, this);
        }
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

    public ArrayList<Object> disableSort() {
        currentSort = null;
        products = Product.getAllProducts();
        setFilters();
        return new ArrayList<>(products);
    }

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
