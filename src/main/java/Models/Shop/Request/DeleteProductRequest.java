package Models.Shop.Request;

import Models.Account.Seller;
import Models.Shop.Product.Product;

import java.io.IOException;

public class DeleteProductRequest extends Request {
    private String productId;

    public DeleteProductRequest(Seller seller, Product product) {
        super(seller);
        this.type = RequestType.DELETE_PRODUCT;
        this.productId = product.getId();
    }

    public void accept() throws IOException {
        Product.deleteProduct(getProduct());
        deleteRequest(this, "delete product requests");
    }

    public Product getProduct() {
        return Product.getProductById(productId);
    }

    @Override
    public void decline() throws IOException {
        deleteRequest(this, "delete product requests");
    }

    @Override
    public String toString() {
        return "DeleteProductRequest : " +
                "\nproduct=" + getProduct() +
                "\nid='" + id + '\'' +
                "\nseller=" + seller +
                "\ntype=" + type;
    }
}
