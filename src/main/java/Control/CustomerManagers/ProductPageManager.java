package Control.CustomerManagers;

import Models.Account.Account;
import Models.Shop.Comment;
import Models.Shop.Product;
import View.CustomerMenus.product.ProductPage;

import java.util.ArrayList;
import java.util.List;

public class ProductPageManager extends CustomerManager {

    protected Product product;

    public ProductPageManager(Account account,Product product) {
        super(account);
        this.menu = new ProductPage(this);
        this.product = product;
    }

    public String digest() {
        return product.digest();
    }
    // attributes
    public String attributes() {
        return product.getAttributes();
    }

    // compare [productId]

    public String compare(String id) {
        Product otherProduct = Product.getProductById(id);
        return product.getAttributes()+"\nCompared to\n"+otherProduct.getAttributes();
    }
    // comments

    public List<String> comments() {
        List<String> comments = new ArrayList<>();
        comments.add("Rate:" + product.getRate());
        comments.addAll(product.getComments());
        return comments;
    }

    public void addComment(String title, String content) {
        Comment comment = new Comment(account,product,content,null,false);
        product.addComment(comment);
    }

    public Product getProduct() {
        return product;
    }
}
