package Client.ViewController.products;

import Server.Control.Manager;
import Client.ViewController.Controller;
import Models.Shop.Off.Auction;
import Models.Shop.Product.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class ProductItemController extends Controller {

    private Product product;
    private Auction auction;
    public Button auctionButton;
    public Label idLabel;
    public ImageView picture;
    public Label nameLabel;
    public Label priceLabel;
    public Label statusLabel;
    public Label rateLabel;

    public void setInfos(Auction auction, Product product, boolean isOffMenu) {
        if (!isOffMenu) {
            auctionButton.setDisable(false);
            auctionButton.setVisible(false);
        }
        this.product = product;
        this.auction = auction;
        idLabel.setText(product.getId());
        // TODO Product picture
        nameLabel.setText(product.getName());
        priceLabel.setText(sendRequest("GET_PRODUCT_PRICE " + product.getId()) + "$");
        if (product.getStatus() == Product.ProductStatus.CONFIRMED)
            statusLabel.setText("Available");
        else statusLabel.setText("Unavailable");
        rateLabel.setText(sendRequest("GET_PRODUCT_RATE " + product.getId()));
    }

    public void openProduct(MouseEvent mouseEvent) {
        //((ProductsManager) manager).openProductPage(product.getId());
        try {
            FXMLLoader loader = getLoader(Addresses.PRODUCT_PAGE);
            Parent root = loader.load();
            ProductPageController controller = loader.getController();
            controller.setInfos(product, auction);
            controller.init();
            Scene scene = new Scene(root);
            stage.setTitle("AP Project");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void auctionDetails(ActionEvent actionEvent) {
        //((ProductsManager) manager).openAuctionDetails(auction, product);
        try {
            FXMLLoader loader = getLoader(Addresses.AUCTION_DETAILS);
            Parent root = loader.load();
            AuctionDetailsController controller = loader.getController();
            controller.setInfos(auction, product);
            Scene scene = new Scene(root);
            popup.setTitle("AP Project");
            popup.setScene(scene);
            popup.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
