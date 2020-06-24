package ViewController.products;

import Models.Shop.Product.Product;
import ViewController.Controller;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ProductItemController extends Controller {

    public Label idLabel;
    public ImageView picture;
    public Label nameLabel;
    public Label priceLabel;
    public Label statusLabel;
    public Label rateLabel;

    public void setInfos(Product product) {
        idLabel.setText(product.getId());
        // TODO Product picture
        nameLabel.setText(product.getName());
        priceLabel.setText(product.getPrice() + "$");
        if (product.getStatus() == Product.ProductStatus.CONFIRMED)
            statusLabel.setText("Available");
        else statusLabel.setText("Unavailable");
        rateLabel.setText(product.getRate() + "");
    }
}
