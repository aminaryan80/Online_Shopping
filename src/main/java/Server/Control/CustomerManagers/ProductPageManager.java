package Server.Control.CustomerManagers;

import Server.Control.Manager;
import Client.ViewController.Controller;
import Client.ViewController.products.ProductPageController;
import Models.Account.Account;
import Models.Account.Customer;
import Models.Shop.Product.Comment;
import Models.Shop.Product.Product;

import java.util.HashMap;

public class ProductPageManager extends Manager {
    private Customer customer = (Customer) account; //TODO should this field be static?
    private Product product;
    private ProductPageController productPageController;

    public ProductPageManager(Account account, String productId) {
        super(account);
        this.product = Product.getProductById(productId);
    }

    public HashMap<String, String> commentsFXML() {
        HashMap<String, String> commentsFXML = new HashMap<>();
        for (Comment comment : product.getAllComments()) {
            commentsFXML.put(comment.getAccount().getUsername(), comment.getText());
        }
        return commentsFXML;
    }

    public void addComment(String title, String content) {
        boolean hasPurchased = false;
        for (Customer buyer : product.getAllBuyers()) {
            if (customer.getUsername().equals(buyer.getUsername())) {
                hasPurchased = true;
                break;
            }
        }
        Comment comment = new Comment(customer, product, title + ":\n" + "\t" + content, null, hasPurchased);
        product.addComment(comment);
        //productPageController.initializeComments();
    }

    public String rateProduct(String productId, int score) { //TODO RECHECK
        if (customer.hasBoughtProduct(productId)) {
            Product product = Product.getProductById(productId);
            if (product != null) {
                product.addRate(customer, score);
                return "0";
            }
        }
        return "1";
    }

    public Product getProduct() {
        return product;
    }

    public int addToCart() {
        if (customer == null) //TODO check
            cart.addProduct(product);
        else customer.getCart().addProduct(product);
        return 0;
    }

    public int hasProductInCart() {
        for (Product productInCart : customer.getCart().getProducts()) {
            if (productInCart.getId().equals(product.getId())) {
                return 0;
            }
        }
        return 1;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        ProductPageManager.setAccount(customer);
        this.customer = customer;
    }
}