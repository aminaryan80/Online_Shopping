package Control.Principal;

import Control.Manager;
import Models.Account.Account;
import Models.Account.Customer;
import Models.Shop.Category.Sort;
import Models.Shop.Product.Product;
import View.Principal.ManageAllProductsMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import java.io.IOException;

public class ManageAllProductsManager extends Manager {

    private Sort currentSort;
    private ArrayList<Product> products;

    public ManageAllProductsManager(Account account) {
        super(account);
        products = Product.getAllProducts();
        new ManageAllProductsMenu(this);
    }

    public void removeProductById(String id) {
        try {
            Product.deleteProduct(Product.getProductById(id));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Customer.deleteProductFromCarts(Product.getProductById(id));
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

    private ArrayList<String> productsInShort() {
        ArrayList<String> productsInShort= new ArrayList<>();
        for (Product product : products) {
            productsInShort.add(product.viewProductInShort());
        }
        return productsInShort;
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
        products = (ArrayList<Product>) Arrays.asList(productsForSort);
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
        products = (ArrayList<Product>) Arrays.asList(productsForSort);
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
        products = (ArrayList<Product>) Arrays.asList(productsForSort);
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
}
