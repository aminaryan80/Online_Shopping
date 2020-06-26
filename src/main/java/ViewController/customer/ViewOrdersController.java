package ViewController.customer;

import Control.CustomerManagers.ViewCartManager;
import Control.CustomerManagers.ViewOrdersManager;
import Models.Shop.Log.BuyingLog;
import Models.Shop.Product.Product;
import ViewController.Controller;
import ViewController.customer.cart.CartTableItem;
import com.sun.javafx.scene.control.skin.LabeledText;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewOrdersController extends Controller {


    public Label orderIdLabel;
    public Label dateLabel;
    public Label moneyPaidLabel;
    public Label discountAmountLabel;
    public TableColumn numberColumn;
    public TableColumn orderIdColumn;
    public TableColumn sellerNameColumn;
    public TableColumn statusColumn;
    public TableColumn addressColumn;
    public TableColumn phoneNumberColumn;
    public TableView tableView;

    public void init() {
        ArrayList<OrderTableItem> orderTableItems = getOrderTableItems();
        ObservableList<OrderTableItem> data = FXCollections.observableList(orderTableItems);

        numberColumn.setCellValueFactory(new PropertyValueFactory<CartTableItem, Integer>("number"));
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<CartTableItem, String>("id"));
        sellerNameColumn.setCellValueFactory(new PropertyValueFactory<CartTableItem, String>("sellerName"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<CartTableItem, String>("status"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<CartTableItem, Integer>("phoneNumber"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<CartTableItem, Double>("address"));

        tableView.setItems(data);
    }

    private ArrayList<OrderTableItem> getOrderTableItems() {
        ArrayList<OrderTableItem> orderTableItems = new ArrayList<>();
        if (manager instanceof ViewOrdersManager) {
            ArrayList<BuyingLog> logs = ((ViewOrdersManager) manager).getLogs();
            int number = 1;
            for (BuyingLog log : logs) {
                orderTableItems.add(new OrderTableItem(
                        number,
                        log.getId(),
                        log.getName(),
                        log.getStatus().toString(),
                        log.getPhoneNumber(),
                        log.getAddress()
                ));
                number++;
            }
        }
        return orderTableItems;
    }


    public void showBoughtProducts(ActionEvent actionEvent) {

    }

    public void select(MouseEvent mouseEvent) {
        if (!(mouseEvent.getTarget() instanceof LabeledText)) {
            TableRow tableRow = ((TableCell) mouseEvent.getTarget()).getTableRow();
            OrderTableItem orderTableItem = (OrderTableItem) tableView.getItems().get(tableRow.getIndex());
            if (orderTableItem != null) {
                BuyingLog log = ((ViewOrdersManager)manager).getLogById(orderTableItem.getId());
                orderIdLabel.setText("#"+log.getId());
                dateLabel.setText("Date: "+ log.getDate().toString());
                moneyPaidLabel.setText("Paid money: "+log.getMoney()+"$");
                discountAmountLabel.setText("Discount amount: "+log.getAmount()+"$");
            }
        }
    }
}
