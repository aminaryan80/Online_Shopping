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

    public double getTotalPrice(Discount discount) {
        double sum = 0;
        for (Product product : products.keySet()) {
            sum += product.getPrice();
        }
        return affectDiscount(sum,discount);
    }

    private double affectDiscount(double sum,Discount discount) {
        if(discount == null) return sum;
        double sumWithDiscountPercent = discount.getDiscountPercent()*sum/100;
        double sumWithMaximumDiscount = discount.getMaximumDiscount();
        if(sumWithDiscountPercent > sumWithMaximumDiscount){
            return sum - discount.getMaximumDiscount();
        } else return discount.getDiscountPercent()*sum/100;
    }
}
