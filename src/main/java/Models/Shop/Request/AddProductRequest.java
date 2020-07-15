package Models.Shop.Request;

import Models.Account.Seller;
import Models.Shop.Product.Product;

import java.io.IOException;

public class AddProductRequest extends Request {
    private Product product;

    public AddProductRequest(Seller seller, Product product) {
        super(seller);
        this.type = RequestType.ADD_PRODUCT;
        this.product = product;
    }

    @Override
    public void accept() throws IOException {
        product.setStatus(Product.ProductStatus.CONFIRMED);
        deleteRequest(this, "add product requests");
        Product.addProduct(product);
    }

    @Override
    public void decline() throws IOException {
        deleteRequest(this, "add product requests");
    }

    @Override
    public String toString() {
        return "AddProductRequest : " +
//                "\nproduct = " + getProduct() +
                "\nproduct = " + product +
                "\nid = '" + id + '\'' +
                "\nseller = " + seller +
                "\ntype = " + type;
    }
}
