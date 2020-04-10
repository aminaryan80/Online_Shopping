package Models.Shop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    //private List<Product> products;
    private Map<Product,Integer> products = new HashMap<Product, Integer>();

    public Map<Product, Integer> getProducts() {
        return products;
    }
}
