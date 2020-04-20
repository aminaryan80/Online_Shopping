package Control;

import Models.Account.Account;
import Models.Shop.Product;

import java.util.List;

public class ProductPageManager extends Manager {

    public ProductPageManager(Account account, Product product) {
        super(account);
        this.product = product;
    }

    private Product product;

    // digest
    public String digest() {
        return null;
    }

    public void addToCart() {

    }

    public void selectSeller(String sellerUsername) {

    }

    // attributes
    public String attributes() {
        return null;
    }

    // compare [productId]
    public List<String> compare(String id) {
        return null;
    }

    // comments
    public List<String> comments() {
        return null;
    }

    public void addComment(String title, String content) {

    }
}
