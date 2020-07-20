package Client.ViewController.products;

import Client.ViewController.Controller;
import Models.Shop.Off.Auction;
import Models.Shop.Product.Product;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AuctionDetailsController extends Controller {


    public Label auctionLabel;
    public TextField beginningDateTextField;
    public TextField endingDateTextField;
    public TextField amountTextField;

    public void setInfos(Auction auction, Product product) {
        auctionLabel.setText("Auction #" + auction.getId() + ":");
        beginningDateTextField.setText(auction.getBeginningDate() + "");
        endingDateTextField.setText(auction.getEndingDate() + "");
        amountTextField.setText(product.getAuctionedPrice() + "");
    }
}
