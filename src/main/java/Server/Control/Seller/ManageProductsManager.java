package Server.Control.Seller;

import Server.Control.Manager;
import Client.ViewController.Controller;
import Models.Account.Account;
import Models.Account.Seller;
import Models.Shop.Category.Category;
import Models.Shop.Category.Feature;
import Models.Shop.Category.Sort;
import Models.Shop.Product.Product;
import Models.Shop.Request.AddProductRequest;
import Models.Shop.Request.EditProductRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ManageProductsManager extends Manager {
    private static Sort currentSort;
    private static List<Product> products;

    public ManageProductsManager(Account account) {
        super(account);
    }

    public String showAvailableSorts() {
        return "price\n" +
                "name\n" +
                "rating";
    }

    public static ArrayList<String> getSortFields() {
        ArrayList<String> fields = new ArrayList<>();
        fields.add("price");
        fields.add("name");
        fields.add("rating");
        return fields;
    }

    public void addProduct(String name, String category, double price, boolean isAvailable,
                           String description, ArrayList<Feature> features, String fileName) {
        String companyName = ((Seller) account).getCompanyName();
        Product product = new Product(name, companyName, price, (Seller) account, isAvailable, category, description, features, fileName);
        new AddProductRequest((Seller) account, product);
    }

    public static ArrayList<Object> sort(String sort, boolean isAscending) {
        products = mainCategory.getAllProducts();
        currentSort = new Sort(sort, isAscending);
        applySort();
        return new ArrayList<>(products);
    }

    private static void applySort() {
        if (currentSort == null) {
            return;
        }
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

    private static void sortByPrice() {
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

    private static void sortByName() {
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

    private static void sortByRating() {
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

    public ArrayList<Object> disableSort() {
        currentSort = null;
        products = mainCategory.getAllProducts();
        return new ArrayList<>(products);
    }

    public void editProduct(String sellerUsername, String id, String field, String newValue) {
        Seller seller = (Seller) Account.getAccountByUsername(sellerUsername);
        Product product = Product.getProductById(id);
        if (field.equals("NAME")) {
            product.setName(newValue);
        } else if (field.equals("DESCRIPTION")) {
            product.setDescription(newValue);
        } else if (field.equals("IS_AVAILABLE")) {
            product.setAvailable(Boolean.parseBoolean(newValue));
        } else if (field.equals("PRICE")) {
            product.setPrice(Double.parseDouble(newValue));
        } else {
            product.getFeatureByName(field).setValue(newValue);
        }
        product.setStatus(Product.ProductStatus.UNDER_REVIEW_FOR_EDITING);
        new EditProductRequest(seller, product);
    }
}
