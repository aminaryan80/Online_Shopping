package Models.Shop;

import Control.Manager;
import Control.Seller.EditProductsManager;
import Models.Account.Seller;

public class EditProductRequest extends Request {
    private Product product;

    public EditProductRequest(String id, Seller seller, Manager manager, Product product) {
        super(id, seller, manager);
        this.type = RequestType.EDIT_PRODUCT;
        this.product = product;
    }

    public void accept() {
        product.setStatus(Product.ProductStatus.CONFIRMED);
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
