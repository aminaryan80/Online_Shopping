package Models.Shop.Request;

import Models.Account.Seller;
import Models.Shop.Product.Product;

public class AddProductRequest extends Request {
    private Product product;
    private String productId;

    public AddProductRequest(Seller seller, Product product) {
        super(seller);
        this.type = RequestType.ADD_PRODUCT;
        this.product = product;
        this.productId = product.getId();
    }

    @Override
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
        return "AddProductRequest : " +
                "\nproduct = " + product +
                "\nid = '" + id + '\'' +
                "\nseller = " + seller +
                "\ntype = " + type;
    }
}
