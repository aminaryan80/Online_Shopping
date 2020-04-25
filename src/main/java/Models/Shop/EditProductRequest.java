package Models.Shop;

import Control.Manager;
import Control.Seller.EditProductsManager;
import Models.Account.Seller;

public class EditProductRequest extends Request {
    private String productId;
    private String field;
    private String newValue;

    public EditProductRequest(String id, Seller seller, Manager manager, String productId, String field, String newValue) {
        super(id, seller, manager);
        this.type = RequestType.EDIT_PRODUCT;
        this.productId = productId;
        this.field = field;
        this.newValue = newValue;
    }

    public void accept() {
        ((EditProductsManager) manager).editProduct(id, field, newValue);
    }

    @Override
    public String toString() {
        return "EditProductRequest{" +
                "productId='" + productId + '\'' +
                ", field='" + field + '\'' +
                ", newValue='" + newValue + '\'' +
                ", id='" + id + '\'' +
                ", seller=" + seller +
                ", type=" + type +
                '}';
    }
}
