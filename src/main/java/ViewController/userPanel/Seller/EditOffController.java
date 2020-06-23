package ViewController.userPanel.Seller;

import Control.Seller.OffsManager;
import Models.Account.Seller;
import Models.Shop.Off.Auction;
import Models.Shop.Product.Product;
import Models.Shop.Request.AddOffRequest;
import Models.Shop.Request.EditOffRequest;
import ViewController.Controller;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class EditOffController extends Controller {

    @FXML
    private TextField id;
    @FXML
    private TextField beginningDate;
    @FXML
    private TextField endingDate;
    @FXML
    private TextField amount;
    @FXML
    private TableView<Product> products;
    @FXML
    private TableView<Auction> offs;
    @FXML
    private TableColumn <Auction, String> offsIdColumn;
    @FXML
    private TableColumn<Auction, Double> amountColumn;
    @FXML
    private TableColumn<Product, String> productsIdColumn;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private TextField productsIds;
    private Seller seller;
    private Auction off;

    public void init() {
        InitOffs();
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    private void InitOffs() {
        offs.setItems(FXCollections.observableArrayList(seller.getAuctions()));
        offsIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("discountAmount"));
    }

    public void update(ActionEvent actionEvent) {
        if (!off.getBeginningDate().equals(LocalDate.parse(beginningDate.getText()))) {
            Auction auction = ((OffsManager) manager).editOffAttribute(off.getId(), "beginningDate", beginningDate.getText());
            new EditOffRequest((Seller) manager.getAccount(), auction);
        }
        if (!off.getEndingDate().equals(LocalDate.parse(endingDate.getText()))) {
            Auction auction = ((OffsManager) manager).editOffAttribute(off.getId(), "endingDate", endingDate.getText());
            new EditOffRequest((Seller) manager.getAccount(), auction);
        }
        if (!(off.getDiscountAmount() == Double.parseDouble(amount.getText()))) {
            Auction auction = ((OffsManager) manager).editOffAttribute(off.getId(), "amount", amount.getText());
            new EditOffRequest((Seller) manager.getAccount(), auction);
        }
    }

    public void add(ActionEvent actionEvent) {
        Auction auction = ((OffsManager) manager).addOff(beginningDate.getText(), endingDate.getText(),
                Double.parseDouble(amount.getText()), (ArrayList<String>) Arrays.asList(productsIds.getText().split("\\s+")));
        new AddOffRequest((Seller) manager.getAccount(), auction);
    }

    public void updateScene(ActionEvent actionEvent) {
        off = offs.getSelectionModel().getSelectedItem();
        id.setText(off.getId());
        beginningDate.setText(off.getBeginningDate().toString());
        endingDate.setText(off.getEndingDate().toString());
        amount.setText(String.valueOf(off.getDiscountAmount()));
        products.setItems(FXCollections.observableArrayList(off.getProducts()));
        productsIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
