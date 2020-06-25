package Control.Seller;

import Control.Manager;
import Models.Account.Account;
import Models.Account.Seller;
import Models.Shop.Category.Sort;
import Models.Shop.Log.SellingLog;
import Models.Shop.Product.Product;
import View.Seller.EditProductsMenu;
import ViewController.Controller;
import ViewController.userPanel.Seller.EditProductsController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class EditProductsManager extends Manager {
    private Sort currentSort;
    private ArrayList<Product> products;

    public EditProductsManager(Account account) {
        super(account);
        products = Product.getAllProducts();
        new EditProductsMenu(this);
    }

    public EditProductsManager(Account account, Addresses address, Manager manager) {
        super(account, address, manager);
        Controller controller = loadFxml(Addresses.EDIT_PRODUCTS_MENU);
        update(controller);
    }

    public void update(Controller c) {
        EditProductsController controller = (EditProductsController) c;
        controller.init((Seller) account);
    }

    public String viewProductDetails(String id) {
        return Product.getProductById(id).toString();
    }

    public ArrayList<String> viewProductBuyers(String id) {
        ArrayList<SellingLog> allLogs = ((Seller) account).getAllLogs();
        ArrayList<String> allBuyers = new ArrayList<String>();
        for (SellingLog log : allLogs) {
            allBuyers.add(log.getName());
        }
        return allBuyers;
    }

    public String showAvailableSorts() {
        return "price\n" +
                "name\n" +
                "rating";
    }

    public ArrayList<Product> sort(String sort, boolean isAscending) {
        products = mainCategory.getAllProducts();
        currentSort = new Sort(sort, isAscending);
        applySort();
        return products;
    }

    private ArrayList<String> productsInShort() {
        ArrayList<String> productsInShort= new ArrayList<>();
        for (Product product : products) {
            productsInShort.add(product.viewProductInShort());
        }
        return productsInShort;
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

    public boolean hasProductWithId(String id) {
        return Product.hasProductWithId(id, account.getUsername());
    }

    public Product editProduct(String id, String field, String newValue) {
        Product product = Product.getProductById(id);
        if (field.equals("name")) {
            product.setName(newValue);
        } else if (field.equals("description")) {
            product.setDescription(newValue);
        } else if (field.equals("isAvailable")) {
            product.setAvailable(Boolean.parseBoolean(newValue));
        } else if (field.equals("price")) {
            product.setPrice(Double.parseDouble(newValue));
        } else {
            product.getFeatureByName(field).setValue(newValue);
        }
        product.setStatus(Product.ProductStatus.UNDER_REVIEW_FOR_EDITING);
        return product;
    }

    public boolean isFeatureFieldValid(String field, String id) {
        return Product.getProductById(id).getCategory().getFeaturesNames().contains(field);
    }

    public boolean isEnteredProductEditFieldValid(String field) {
        return Product.isEnteredProductFieldValid(field);
    }
}
