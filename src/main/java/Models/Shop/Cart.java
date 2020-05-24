package Models.Shop;

import Models.Account.Customer;
import Models.Shop.Off.Discount;
import Models.Shop.Product.Product;

import java.time.LocalDate;
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

    public static void addCartToCustomerCart(Customer account, Cart cart) {
        account.getCart().products.putAll(cart.products);
    }

    public boolean hasProductInCartWithId(String id) {
        return true;
    }

    public Product getProductInCartById(String id) {
        for (Product product : getProducts()) {
            if (product.getId().equals(id)) return product;
        }
        return null;
    }

    public String getProductNumberInCartById(String id) {
        for (Product product : getProducts()) {
            if (product.getId().equals(id)) return products.get(product).toString();
        }
        return null;
    }

    public ArrayList<String> showProductsInShort() {
        ArrayList<String> productsNamesAndIds = new ArrayList<>();
        for (Product product : getProducts()) {
            productsNamesAndIds.add("Name: " + product.getName() +
                    "\nId: " + product.getId() +
                    "\nNumber: " + products.get(product));
        }
        return productsNamesAndIds;
    }

    public void addProduct(Product product) {
        if (products.containsKey(product)) {
            products.replace(product, products.get(product) + 1);
        } else products.put(product, 1);
    }

    public void removeProduct(Product product) {
        int numberOfThisProduct = products.get(product) - 1;
        products.replace(product,numberOfThisProduct);
        if(numberOfThisProduct == 0){
            products.remove(product);
        }
    }

    public double getTotalPrice(Discount discount) {
        double totalPriceOfProducts = getTotalAmountWithoutDiscount();
        return totalPriceOfProducts - amountOfDiscount(totalPriceOfProducts, discount);
    }

    public double getTotalAmountWithoutDiscount() {
        double totalAmountWithoutDiscount = 0; // is auctioned
        for (Product product : products.keySet()) {
            totalAmountWithoutDiscount += product.getAuctionedPrice() * products.get(product);
        }
        return totalAmountWithoutDiscount;
    }

    public double amountOfDiscount(double priceOfProducts, Discount discount) { //price of products after auction calculation
        if (discount == null || !discount.isActive(LocalDate.now())) return 0;
        double discountPercent = (double) discount.getDiscountPercent() / 100;
        double sumWithDiscountPercent = discountPercent * priceOfProducts;
        double sumWithMaximumDiscount = discount.getMaximumDiscount();
        if (sumWithDiscountPercent > sumWithMaximumDiscount) {
            return discount.getMaximumDiscount();
        } else return discountPercent * priceOfProducts;
    }
}
