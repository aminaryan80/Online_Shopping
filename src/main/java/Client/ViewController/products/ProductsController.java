package Client.ViewController.products;

import Client.ViewController.Controller;
import Models.Account.Account;
import Models.Shop.Category.*;
import Server.Control.Manager;
import Server.Control.Products.ProductsManager;
import Client.ViewController.MainController;
import Models.Gson;
import Models.Shop.Off.Auction;
import Models.Shop.Product.Product;
import com.google.gson.reflect.TypeToken;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class ProductsController extends MainController implements Initializable {

    public GridPane productsGridPane;
    public Label categoryNameLabel;
    private Category currentCategory;
    private List<Filter> filters = new ArrayList<>();
    private List<LengthFilter> lengthFilters = new ArrayList<>();
    private Sort currentSort = null;
    private List<Product> products;
    private List<Product> myProducts;
    private boolean isOffMenu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            if(currentCategory == null) {
                currentCategory = Gson.INSTANCE.get().fromJson(sendRequest("GET_CATEGORY mainCategory"), Category.class);
            }
            ObservableList<Node> children = productsGridPane.getChildren();
            productsGridPane.getChildren().removeAll(children);
            categoryNameLabel.setText(currentCategory.getName());
            int i = 0;
            productsGridPane.setPrefHeight(0);
            products = new ArrayList<>();
            products.addAll(Gson.INSTANCE.get().fromJson(sendRequest("LOAD_PRODUCTS " + isOffsMenu),
                    new TypeToken<ArrayList<Product>>() {
                    }.getType()));
            myProducts = new ArrayList<>();
            myProducts.addAll(products);
            for (Product product : products) {
                FXMLLoader loader = getLoader(Addresses.PRODUCT_ITEM);
                Parent root = loader.load();
                ProductItemController controller = loader.getController();
                Auction auction = Gson.INSTANCE.get().fromJson(sendRequest("GET_PRODUCT_AUCTION " + product.getId()), Auction.class);
                controller.setInfos(auction, product, product.hasAuction());
                productsGridPane.add(root, i % 3, i / 3);
                if (i % 3 == 0) {
                    productsGridPane.setPrefHeight(productsGridPane.getPrefHeight() + 380);
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initTable(ArrayList<Object> tableObjects) {
        ArrayList<Product> tableProducts = new ArrayList<>();
        for (Object tableProduct : tableObjects) {
            tableProducts.add((Product) tableProduct);
        }
        products = tableProducts;
        ObservableList<Node> children = productsGridPane.getChildren();
        productsGridPane.getChildren().removeAll(children);
        init();
    }

    public void init() {
        try {
            categoryNameLabel.setText(currentCategory.getName());
            int i = 0;
            productsGridPane.setPrefHeight(0);
            for (Product product : products) {
                //FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/products/Product.fxml"));
                FXMLLoader loader = getLoader(Addresses.PRODUCT_ITEM);
                Parent root = loader.load();
                ProductItemController controller = loader.getController();
                controller.setInfos(product.getAuction(), product, product.hasAuction());
                controller.setManager(manager);
                productsGridPane.add(root, i % 3, i / 3);
                if (i % 3 == 0) {
                    productsGridPane.setPrefHeight(productsGridPane.getPrefHeight() + 380);
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void viewCategories(ActionEvent actionEvent) {
        loadFxml(Addresses.VIEW_CATEGORIES, true);
    }

    public void filter(ActionEvent actionEvent) {
        ((FilteringController) loadFxml(Addresses.FILTER, true)).init(this);
    }

    public void sort(ActionEvent actionEvent) {
        openSort(this);
    }

    public void back() {
        loadFxml(Addresses.MAIN_MENU);
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
            currentCategory = Gson.INSTANCE.get().fromJson(sendRequest("GET_CATEGORY " + filterValue), Category.class);
        }
        products = new ArrayList<>();
        products.addAll(myProducts);
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
        return products.stream().filter(product -> product.getCategory().equals(Gson.INSTANCE.get().fromJson(sendRequest("GET_CATEGORY " + filter.getValue()), Category.class))).collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Product> setIsAvailableFilter(Filter filter) {
        return products.stream().filter(product -> product.isAvailable() == Boolean.parseBoolean(filter.getValue())).collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Product> setSellerFilter(Filter filter) {
        return products.stream().filter(product -> product.getSeller().equals(Gson.INSTANCE.get().fromJson(sendRequest("GET_ACCOUNT " + filter.getValue()), Account.class))).collect(Collectors.toCollection(ArrayList::new));
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

    public ArrayList<Object> disableFilter(String filterField) {
        Object filter = getFilterByField(filterField);
        if (filter instanceof Filter) {
            filters.remove(filter);
        } else {
            lengthFilters.remove(filter);
        }
        products = new ArrayList<>();
        products.addAll(myProducts);
        if (filterField.equals("category")) {
            currentCategory = Gson.INSTANCE.get().fromJson(sendRequest("GET_CATEGORY mainCategory"), Category.class);
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

    public ArrayList<Object> sort(String sort, boolean isAscending) {
        products = new ArrayList<>();
        products.addAll(myProducts);
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

    public List<Filter> currentFilters() {
        return filters;
    }
}
