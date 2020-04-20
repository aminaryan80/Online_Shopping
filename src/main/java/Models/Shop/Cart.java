package Models.Shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Product,Integer> products = new HashMap<Product, Integer>();

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public ArrayList<String> showProductsInShort() {
        // #id name count
        return null;
    }

    public boolean hasProductInCartWithId(String id) {
        return true;
    }

    public void addProduct(Product product) {

    }

    public void deleteProduct(Product product) {

    }

    public double getTotalPrice() {
        return 1;
    }
}
