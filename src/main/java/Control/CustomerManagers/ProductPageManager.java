package Control.CustomerManagers;

import Control.Manager;
import Models.Account.Account;
import Models.Account.Customer;
import Models.Shop.Product.Comment;
import Models.Shop.Product.Product;
import ViewController.Controller;
import ViewController.products.ProductPageController;

import java.util.HashMap;

public class ProductPageManager extends Manager {
    private Customer customer = (Customer) account; //TODO should this field be static?
    private Product product;
    private ProductPageController productPageController;

    public ProductPageManager(Account account, Product product, Addresses address, Manager manager) {
        super(account, address, manager);
        this.product = product;
        Controller controller = loadFxml(Addresses.PRODUCT_PAGE);
        update(controller);
    }

    public void update(Controller c) {
        productPageController = (ProductPageController) c;
        productPageController.init();
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
        productPageController.initializeComments();
    }

    public boolean rateProduct(String productId, int score) throws ViewCartManager.ProductDoNotExistAtAllException { //TODO RECHECK
        if (customer.hasBoughtProduct(productId)) {

            Product product = Product.getProductById(productId);
            if (product != null) product.addRate(customer, score);
            else throw new ViewCartManager.ProductDoNotExistAtAllException("Product does not exist");

            return true;
        } else return false;
    }

    public Product getProduct() {
        return product;
    }

    public void addToCart() {
        if (customer == null) //TODO check
            cart.addProduct(product);
        else customer.getCart().addProduct(product);
    }

    public boolean hasProductInCart() {
        for (Product productInCart : customer.getCart().getProducts()) {
            if (productInCart.getId().equals(product.getId())) {
                return true;
            }
        }
        return false;
    }

    public void addComment() {
        loadFxml(Addresses.ADD_COMMENT, true);
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        ProductPageManager.setAccount(customer);
        this.customer = customer;
    }

    public void compare() {
        Controller controller = loadFxml(Addresses.COMPARE, true);
        controller.init();
    }
}