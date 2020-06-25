package Control.CustomerManagers;

import Control.Manager;
import Control.UtilTestObject;
import Models.Account.Account;
import Models.Account.Customer;
import Models.Shop.Category.Sort;
import Models.Shop.Off.Discount;
import Models.Shop.Product.Product;
import View.CustomerMenus.customer.ViewCartMenu;
import ViewController.Controller;
import ViewController.customer.cart.ViewCartController;

import java.util.*;

public class ViewCartManager extends Manager {

    private Sort currentSort;
    private List<Product> products;
    private Customer customer = (Customer) account;

    public ViewCartManager(Account account) {
        super(account);
        products = Product.getAllProducts();
        if (!account.getUsername().equals(UtilTestObject.CUSTOMER))
            this.menu = new ViewCartMenu(this);
    }

    public ViewCartManager(Account account, Addresses address, Manager manager) {
        super(account, address, manager);
        products = Product.getAllProducts();
        Controller controller = loadFxml(Addresses.VIEW_CART);
        update(controller);
    }

    public void update(Controller c) {
        ViewCartController controller = (ViewCartController) c;
        controller.init();
    }

    public String showProducts() {
        List<String> productsInfos = customer.getCart().showProductsInShort();
        StringBuilder allProductsInfo = new StringBuilder();
        for (String productsInfo : productsInfos) {
            allProductsInfo.append("\n" + productsInfo);
        }
        return allProductsInfo.toString();
    }

    public void productQuantity(String id, boolean isIncrease) throws ProductDoNotExistInCartException {
        Product product;
        if ((product = customer.getCart().getProductInCartById(id)) != null) {
            if (isIncrease)
                customer.getCart().addProduct(product);
            else {
                customer.getCart().removeProduct(product);
            }
        } else throw new ProductDoNotExistInCartException("Product does not exist in cart");
    }

    public double getTotalPrice(Discount discount) {
        return customer.getCart().getTotalPrice(discount);
    }

    public boolean doesProductExistInCart(Product product) {
        return customer.getCart().getProducts().contains(product);
    }

    public HashMap<Product,Integer> getProductsInCart() {
        return customer.getCart().getProductsMap();
    }

    public void clearCart() {
        customer.getCart().empty();
    }

    public void purchase() {
        new PurchaseManager(account,Addresses.VIEW_CART,this);
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

    public ArrayList<Object> sort(String sort, boolean isAscending) {
        products = mainCategory.getAllProducts();
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

    public ArrayList<Object> disableSort() {
        currentSort = null;
        products = mainCategory.getAllProducts();
        ArrayList<Object> objects = new ArrayList<>();
        objects.addAll(products);
        return objects;
    }

    private ArrayList<String> productsInShort() {
        ArrayList<String> productsInShort = new ArrayList<>();
        for (Product product : products) {
            productsInShort.add(product.viewProductInShort());
        }
        return productsInShort;
    }

    public void showProduct(String id){
        new ProductPageManager(account,Product.getProductById(id),Addresses.VIEW_CART,this);
    }

    public boolean isCartEmpty() {
        return customer.getCart().getProducts().size() == 0;
    }
}
