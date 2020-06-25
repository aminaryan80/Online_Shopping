package ViewController.products;

import Control.Products.ProductsManager;
import Models.Shop.Product.Product;
import ViewController.Controller;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ProductItemController extends Controller {

    private Product product;
    public Label idLabel;
    public ImageView picture;
    public Label nameLabel;
    public Label priceLabel;
    public Label statusLabel;
    public Label rateLabel;

    public void setInfos(Product product) {
        this.product = product;
        idLabel.setText(product.getId());
        // TODO Product picture
        nameLabel.setText(product.getName());
        priceLabel.setText(product.getPrice() + "$");
        if (product.getStatus() == Product.ProductStatus.CONFIRMED)
            statusLabel.setText("Available");
        else statusLabel.setText("Unavailable");
        rateLabel.setText(product.getRate() + "");
    }

    public void openProduct(MouseEvent mouseEvent) {
        ((ProductsManager) manager).openProductPage(product.getId());
    }
}
