package Models.Shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Product, Integer> products = new HashMap<Product, Integer>();

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
        if (products.containsKey(product)) {
            products.replace(product, products.get(product) + 1);
        } else products.put(product, 1);
    }

    public boolean deleteProduct(Product product) {
        if (products.containsKey(product)) {
            if (products.get(product) > 0)
                products.replace(product, products.get(product) - 1);
            return true;
        } else return false;
    }

    public double getTotalPrice() {
        double sum = 0;
        for (Product product : products.keySet()) {
            sum += product.getPrice();
        }
        return sum;
    }
}
