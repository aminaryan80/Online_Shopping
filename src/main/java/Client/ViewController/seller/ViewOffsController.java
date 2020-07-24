package Client.ViewController.seller;

import Server.Control.Manager;
import Client.ViewController.Controller;
import Models.Gson;
import Models.Shop.Off.Auction;
import Models.Shop.Product.Product;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ViewOffsController extends Controller implements Initializable {

    public TextField id;
    public TextField beginningDate;
    public TextField endingDate;
    public TextField amount;
    public TableView<Product> products;
    public TableView<Auction> offs;
    public TableColumn<Auction, String> offsIdColumn;
    public TableColumn<Auction, Double> amountColumn;
    public TableColumn<Product, String> productsIdColumn;
    public TableColumn<Product, String> nameColumn;
    public TableColumn<Product, Double> priceColumn;
    public TextField productsIds;

    private Auction off;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<Auction> auctions = new ArrayList<>();
        auctions.addAll(Gson.INSTANCE.get().fromJson(sendRequest("GET_SELLER_AUCTIONS " + accountUsername),
                new TypeToken<ArrayList<Auction>>() {
                }.getType()));
        offs.setItems(FXCollections.observableArrayList(auctions));
        offsIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("discountAmount"));
    }

    public void add(ActionEvent actionEvent) {
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(accountUsername);
        inputs.add(beginningDate.getText());
        inputs.add(endingDate.getText());
        inputs.add(amount.getText());
        sendRequest("CREATE_AUCTION " + Gson.INSTANCE.get().toJson(inputs) + "&&&" +
                Gson.INSTANCE.get().toJson(Arrays.asList(productsIds.getText().split("\\s+"))));
        success("successful");
    }

    public void update(ActionEvent actionEvent) {
        if (!off.getBeginningDate().equals(LocalDate.parse(beginningDate.getText()))) {
            //((ViewOffsManager) manager).editOffAttribute(accountUsername, off.getId(), "beginningDate", beginningDate.getText());
            sendRequest("EDIT_AUCTION " + accountUsername + " " + off.getId() + " BEGINNING_DATE " + beginningDate.getText());
        }
        if (!off.getEndingDate().equals(LocalDate.parse(endingDate.getText()))) {
            //((ViewOffsManager) manager).editOffAttribute(accountUsername, off.getId(), "endingDate", endingDate.getText());
            sendRequest("EDIT_AUCTION " + accountUsername + " " + off.getId() + " ENDING_DATE " + endingDate.getText());
        }
        if (!(off.getDiscountAmount() == Double.parseDouble(amount.getText()))) {
            //((ViewOffsManager) manager).editOffAttribute(accountUsername, off.getId(), "amount", amount.getText());
            sendRequest("EDIT_AUCTION " + accountUsername + " " + off.getId() + " AMOUNT " + amount.getText());
        }
        success("successful");
    }

    public void updateScene(MouseEvent mouseEvent) {
        off = offs.getSelectionModel().getSelectedItem();
        if (off != null) {
            id.setText(off.getId());
            beginningDate.setText(off.getBeginningDate().toString());
            endingDate.setText(off.getEndingDate().toString());
            amount.setText(String.valueOf(off.getDiscountAmount()));
            products.setItems(FXCollections.observableArrayList(getAuctionProducts()));
            productsIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        }
    }

    private ArrayList<Product> getAuctionProducts() {
        ArrayList<Product> offProducts = new ArrayList<>();
        offProducts.addAll(Gson.INSTANCE.get().fromJson(sendRequest("GET_AUCTION_PRODUCTS " + off.getId()),
                new TypeToken<ArrayList<Product>>() {
                }.getType()));
        return offProducts;
    }

    public void sort(ActionEvent actionEvent) {
        manager.openSort(this, manager);
    }

    public void back() {
        loadFxml(Addresses.SELLER_MENU);
    }
}
