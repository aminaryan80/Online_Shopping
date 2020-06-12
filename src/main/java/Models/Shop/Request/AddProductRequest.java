package Models.Shop.Request;

import Models.Account.Seller;
import Models.Shop.Product.Product;

import java.io.IOException;

public class AddProductRequest extends Request {
//    private Product product;
    private String productId;

    public AddProductRequest(Seller seller, Product product) {
        super(seller);
        this.type = RequestType.ADD_PRODUCT;
//        this.product = product;
        this.productId = product.getId();
    }

    @Override
    public void accept() throws IOException {
        getProduct().setStatus(Product.ProductStatus.CONFIRMED);
        deleteRequest(this, "add product requests");
        Product.addProduct(getProduct());
    }

    public Product getProduct() {
        return Product.getProductById(productId);
    }

    @Override
    public void decline() throws IOException {
        deleteRequest(this, "add product requests");
    }

//    @Override
//    protected void loadReference() {
//        product = Product.getProductById(productId);
//    }

    @Override
    public String toString() {
        return "AddProductRequest : " +
                "\nproduct = " + getProduct() +
                "\nid = '" + id + '\'' +
                "\nseller = " + seller +
                "\ntype = " + type;
    }
}
