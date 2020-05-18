package Control.CustomerManagers;

import Control.Manager;
import Models.Account.Account;
import Models.Account.Customer;
import Models.Shop.Category.Sort;
import Models.Shop.Off.Discount;
import Models.Shop.Product.Product;
import View.CustomerMenus.customer.ViewCartMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ViewCartManager extends Manager {

    private Sort currentSort;
    private List<Product> products;
    private Customer customer = (Customer) account;

    public ViewCartManager(Account account) {
        super(account);
        products = Product.getAllProducts();
        this.menu = new ViewCartMenu(this);
    }

    public String showProducts() {
        List<String> productsInfos = customer.getCart().showProductsInShort();
        StringBuilder allProductsInfo = new StringBuilder();
        for (String productsInfo : productsInfos) {
            allProductsInfo.append(productsInfo);
        }
        return allProductsInfo.toString();
    }

    public void ProductQuantity(String id, boolean isIncrease) throws ProductDoNotExistAtAllException,ProductDoNotExistInCartException {
        Product product;
        if ((product = Product.getProductById(id)) != null) {
            if (isIncrease)
                customer.getCart().addProduct(product);
            else {
                if (!customer.getCart().deleteProduct(product))
                    throw new ProductDoNotExistInCartException("Product does not exist in cart");
            }
        } else throw new ProductDoNotExistAtAllException("Product does not exist at all");
    }

    public double getTotalPrice(Discount discount) {
        return customer.getCart().getTotalPrice(discount);
    }

    public static class ProductDoNotExistAtAllException extends Exception {
        public ProductDoNotExistAtAllException(String message) {
            super(message);
        }
    }

    public static class ProductDoNotExistInCartException extends Exception {
        public ProductDoNotExistInCartException(String message) {
            super(message);
        }
    }

    public String showAvailableSorts() {
        return "price\n" +
                "name\n" +
                "rating";
    }

    public ArrayList<String> sort(String sort, boolean isAscending) {
        products = mainCategory.getAllProducts();
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
        return productsInShort();
    }

    private ArrayList<String> productsInShort() {
        ArrayList<String> productsInShort= new ArrayList<>();
        for (Product product : products) {
            productsInShort.add(product.viewProductInShort());
        }
        return productsInShort;
    }
}
