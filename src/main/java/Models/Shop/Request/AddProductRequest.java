package Models.Shop.Request;

import Models.Account.Seller;
import Models.Shop.Product.Product;

public class AddProductRequest extends Request {
    private Product product;
    private String productId;

    public AddProductRequest(String id, Seller seller, Product product) {
        super(id, seller);
        this.type = RequestType.ADD_PRODUCT;
        this.product = product;
        this.productId = product.getId();
    }

    @Override
    public void accept() {
        product.setStatus(Product.ProductStatus.CONFIRMED);
    }

    @Override
    protected void loadReference() {
        product = Product.getProductById(productId);
    }

    @Override
    public String toString() {
        return "AddProductRequest{" +
                "product=" + product +
                ", id='" + id + '\'' +
                ", seller=" + seller +
                ", type=" + type +
                '}';
    }
}
