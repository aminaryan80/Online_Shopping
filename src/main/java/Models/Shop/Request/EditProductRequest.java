package Models.Shop.Request;

import Models.Account.Seller;
import Models.Shop.Product.Product;

public class EditProductRequest extends Request {
    private Product product;
    private String productId;

    public EditProductRequest(String id, Seller seller, Product product) {
        super(id, seller);
        this.type = RequestType.EDIT_PRODUCT;
        this.product = product;
        this.productId = product.getId();
    }

    public void accept() {
        product.setStatus(Product.ProductStatus.CONFIRMED);
    }

    @Override
    protected void loadReference() {
        product = Product.getProductById(productId);
    }

    @Override
    public String toString() {
        return "EditProductRequest{" +
                "product=" + product +
                ", id='" + id + '\'' +
                ", seller=" + seller +
                ", type=" + type +
                '}';
    }
}
