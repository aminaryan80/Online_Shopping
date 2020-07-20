package Client.Control;

import Models.Account.Account;
import Models.Shop.Category.*;
import Models.Shop.Product.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AuctionsPageManager extends Manager {

    private Category currentCategory;
    private List<Filter> filters = new ArrayList<>();
    private List<LengthFilter> lengthFilters = new ArrayList<>();
    private Sort currentSort = null;
    private List<Product> products;

    public AuctionsPageManager(Account account) {
        super(account);
        this.currentCategory = mainCategory;
        products = Product.getAllAuctionedProducts();
    }

    public ArrayList<Object> applyFilter(String filterType, String filterValue) {
        filters.add(new Filter(filterType, filterValue));
        if (filterType.equals("category")) {
            currentCategory = Category.getCategoryByName(filterValue);
        }
        products = Product.getAllAuctionedProducts();
        setFilters();
        applySort();
        ArrayList<Object> objects = new ArrayList<>();
        objects.addAll(products);
        return objects;
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

    public ArrayList<Object> disableFilter(String filterField) {
        Object filter = getFilterByField(filterField);
        if (filter instanceof Filter) {
            filters.remove(filter);
        } else {
            lengthFilters.remove(filter);
        }
        products = Product.getAllAuctionedProducts();
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

    public ArrayList<String> getSortFields() {
        ArrayList<String> fields = new ArrayList<>();
        fields.add("price");
        fields.add("name");
        fields.add("rating");
        fields.addAll(currentCategory.getFeaturesNames());
        return fields;
    }

    public ArrayList<Object> sort(String sort, boolean isAscending) {
        products = Product.getAllAuctionedProducts();
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
        products = Product.getAllAuctionedProducts();
        setFilters();
        return new ArrayList<>(products);
    }
}
