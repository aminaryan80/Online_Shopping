package Models.Shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Product, Integer> products;

    public Cart() {
        products = new HashMap<>();
    }

    public ArrayList<Product> getProducts() {
        return new ArrayList<>(products.keySet());
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
        double sum = getTotalAmountWithoutDiscount();
        return sum - amountOfDiscount(sum,discount);
    }

    public double getTotalAmountWithoutDiscount() {
        double sum = 0;
        for (Product product : products.keySet()) {
            sum += product.getPrice() * products.get(product);
        }
        return sum;
    }

    public double amountOfDiscount(double sum,Discount discount) {
        if(discount == null) return 0;
        double sumWithDiscountPercent = discount.getDiscountPercent()*sum/100;
        double sumWithMaximumDiscount = discount.getMaximumDiscount();
        if(sumWithDiscountPercent > sumWithMaximumDiscount){
            return discount.getMaximumDiscount();
        } else return discount.getDiscountPercent()/100 * sum;
    }
}
