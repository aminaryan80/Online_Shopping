package Models.Shop.Request;

import Models.Account.Seller;
import Models.Shop.Product.Product;

public class EditProductRequest extends Request {
    private Product product;
    private String productId;

    public EditProductRequest(Seller seller, Product product) {
        super(seller);
        this.type = RequestType.EDIT_PRODUCT;
        this.product = product;
        this.productId = product.getId();
    }

    public void accept() {
        product.setStatus(Product.ProductStatus.CONFIRMED);
        deleteRequest(this);
    }

    @Override
    public void decline() {
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
