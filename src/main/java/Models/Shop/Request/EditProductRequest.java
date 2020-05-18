package Models.Shop.Request;

import Models.Account.Seller;
import Models.Shop.Product.Product;

import java.io.IOException;

public class EditProductRequest extends Request {
    private Product product;
    private String productId;

    public EditProductRequest(String id, Seller seller, Product product) {
        super(id, seller);
        this.type = RequestType.EDIT_PRODUCT;
        this.product = product;
        this.productId = product.getId();
    }

    public void accept() throws IOException {
        product.setStatus(Product.ProductStatus.CONFIRMED);
        deleteRequest(this);
    }

    @Override
    public void decline() throws IOException {
        deleteRequest(this);
    }

    @Override
    protected void loadReference() {
        product = Product.getProductById(productId);
    }

    @Override
    public String toString() {
        return "EditProductRequest : " +
                "\nproduct=" + product +
                "\nid='" + id + '\'' +
                "\nseller=" + seller +
                "\ntype=" + type;
    }
}
