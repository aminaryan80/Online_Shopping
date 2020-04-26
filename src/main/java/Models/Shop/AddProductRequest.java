package Models.Shop;

import Control.Manager;
import Control.Seller.SellerManager;
import Models.Account.Seller;

import java.util.ArrayList;

public class AddProductRequest extends Request {
    private Product product;

    public AddProductRequest(String id, Seller seller, Manager manager, Product product) {
        super(id, seller, manager);
        this.type = RequestType.ADD_PRODUCT;
        this.product = product;
    }

    @Override
    public void accept() {
        product.setStatus(Product.ProductStatus.CONFIRMED);
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
