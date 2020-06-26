package ViewController.products;

import Control.Products.ProductsManager;
import Models.Shop.Off.Auction;
import Models.Shop.Product.Product;
import ViewController.Controller;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ProductItemController extends Controller {

    public Button auctionButton;
    private Product product;
    private Auction auction;
    public Label idLabel;
    public ImageView picture;
    public Label nameLabel;
    public Label priceLabel;
    public Label statusLabel;
    public Label rateLabel;

    public void setInfos(Auction auction, Product product, boolean isOffMenu) {
        if(!isOffMenu) {
            auctionButton.setDisable(false);
            auctionButton.setVisible(false);
        }
        this.product = product;
        this.auction = auction;
        idLabel.setText(product.getId());
        // TODO Product picture
        nameLabel.setText(product.getName());
        priceLabel.setText(product.getAuctionedPrice() + "$");
        if (product.getStatus() == Product.ProductStatus.CONFIRMED)
            statusLabel.setText("Available");
        else statusLabel.setText("Unavailable");
        rateLabel.setText(product.getRate() + "");
    }

    public void openProduct(MouseEvent mouseEvent) {
        ((ProductsManager) manager).openProductPage(product.getId());
    }

    public void auctionDetails(ActionEvent actionEvent) {
        ((ProductsManager) manager).openAuctionDetails(auction, product);
    }
}
