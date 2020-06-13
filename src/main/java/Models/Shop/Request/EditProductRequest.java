package Models.Shop.Request;

import Models.Account.Seller;
import Models.Shop.Product.Product;

import java.io.IOException;

public class EditProductRequest extends Request {
    //private Product product;
    private String productId;

    public EditProductRequest(Seller seller, Product product) {
        super(seller);
        this.type = RequestType.EDIT_PRODUCT;
        //this.product = product;
        this.productId = product.getId();
    }

    public void accept() throws IOException {
        getProduct().setStatus(Product.ProductStatus.CONFIRMED);
        deleteRequest(this, "edit product requests");
    }

    public Product getProduct() {
        return Product.getProductById(productId);
    }

    @Override
    public void decline() throws IOException {
        deleteRequest(this, "edit product requests");
    }
//
//    @Override
//    protected void loadReference() {
//        product = Product.getProductById(productId);
//    }

    @Override
    public String toString() {
        return "EditProductRequest : " +
                "\nproduct=" + getProduct() +
                "\nid='" + id + '\'' +
                "\nseller=" + seller +
                "\ntype=" + type;
    }
}
