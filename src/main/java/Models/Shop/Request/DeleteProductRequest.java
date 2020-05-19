package Models.Shop.Request;

import Models.Account.Seller;
import Models.Shop.Product.Product;

import java.io.IOException;

public class DeleteProductRequest extends Request {
    private Product product;
    private String productId;

    public DeleteProductRequest(Seller seller, Product product) {
        super(seller);
        this.type = RequestType.DELETE_PRODUCT;
        this.product = product;
        this.productId = product.getId();
    }

    public void accept() throws IOException {
        Product.deleteProduct(product);
        deleteRequest(this, "delete product requests");
    }

    @Override
    public void decline() throws IOException {
        deleteRequest(this, "delete product requests");
    }

    @Override
    protected void loadReference() {
        product = Product.getProductById(productId);
    }

    @Override
    public String toString() {
        return "DeleteProductRequest : " +
                "\nproduct=" + product +
                "\nid='" + id + '\'' +
                "\nseller=" + seller +
                "\ntype=" + type;
    }
}
