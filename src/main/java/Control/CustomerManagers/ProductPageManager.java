package Control.CustomerManagers;

import Control.Manager;
import Control.UtilTestObject;
import Models.Account.Account;
import Models.Account.Customer;
import Models.Shop.Product.Comment;
import Models.Shop.Product.Product;
import View.CustomerMenus.product.ProductPage;
import ViewController.products.ProductPageController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductPageManager extends Manager {
    private Customer customer = (Customer) account; //TODO should this field be static?
    private Product product;

    public ProductPageManager(Account account, Product product) {
        super(account);
        this.product = product;
        if (!account.getUsername().equals(UtilTestObject.CUSTOMER))
            this.menu = new ProductPage(this);
    }

    public ProductPageManager(Account account, Product product, Addresses address, Manager manager) {
        super(account, address, manager);
        this.product = product;
        ProductPageController productPageController = (ProductPageController) loadFxml(Addresses.PRODUCT_PAGE);
        productPageController.init();
    }

    public void setCustomer(Customer customer) {
        ProductPageManager.setAccount(customer);
        this.customer = customer;
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
        return product.getAttributes() + "\nCompared to\n" + otherProduct.getAttributes();
    }

    // comments
    public List<String> comments() {
        List<String> comments = new ArrayList<>();
        comments.add("Rate:" + product.getRate() + "\n");
        comments.addAll(product.getComments());
        return comments;
    }

    public HashMap<String,String> commentsFXML(){
        HashMap<String,String> commentsFXML = new HashMap<>();
        for (Comment comment : product.getAllComments()) {
            commentsFXML.put(comment.getAccount().getUsername(),comment.getText());
        }
    return commentsFXML;
    }

    public void addComment(String title, String content) {
        boolean hasPurchased = false;
        for (Customer buyer : product.getAllBuyers()) {
            if (customer.getUsername().equals(buyer.getUsername())) {
                hasPurchased = true;
            }
        }
        Comment comment = new Comment(customer, product, title + ":\n" + "\t" + content, null, hasPurchased);
        product.addComment(comment);
    }

    public Product getProduct() {
        return product;
    }

    public void addComment() {
        loadFxml(Addresses.ADD_COMMENT,true);
    }
}
