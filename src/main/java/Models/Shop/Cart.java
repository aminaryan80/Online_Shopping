package Models.Shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    private Map<Product,Integer> products = new HashMap<Product, Integer>();

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public ArrayList<String> showProductsInshort() {
        // #id name count
    }

    public boolean hasProductInCartWithId(String id) {

    }

    public void deleteProduct(Product product) {

    }

    public double getTotalPrice() {

    }
}
