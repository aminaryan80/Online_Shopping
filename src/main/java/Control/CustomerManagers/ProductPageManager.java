package Control.CustomerManagers;

import Models.Account.Account;
import Models.Shop.Product.Comment;
import Models.Shop.Product.Product;
import View.CustomerMenus.product.ProductPage;

import java.util.ArrayList;
import java.util.List;

public class ProductPageManager extends CustomerManager {

    protected Product product;

    public ProductPageManager(Account account,Product product) {
        super(account);
        this.product = product;
        this.menu = new ProductPage(this);
    }

    public String digest() {
        return product.digest();
    }
    // attributes
    public String attributes() {
        return product.getAttributes();
    }

    // compare [productId]

    public String compare(Product otherProduct) {
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
