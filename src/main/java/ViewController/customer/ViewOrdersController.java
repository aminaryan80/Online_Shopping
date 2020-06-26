package ViewController.customer;

import Control.CustomerManagers.ViewOrdersManager;
import Models.Shop.Log.BuyingLog;
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

    private String logId;

    public void init() {
        ArrayList<OrderTableItem> orderTableItems = getOrderTableItems();
        ArrayList<Object> objects = new ArrayList<>();
        objects.addAll(orderTableItems);
        initTable(objects);
    }

    public void initTable(ArrayList<Object> tableObjects) {
        ArrayList<OrderTableItem> orderTableItems = new ArrayList<>();
        for (Object tableProduct : tableObjects) {
            orderTableItems.add((OrderTableItem) tableProduct);
        }
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
        if (!orderIdLabel.getText().isEmpty())
            ((ViewOrdersManager) manager).showProductsByLogId(logId);
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Select an Order then proceed to show products!", ButtonType.OK);
            alert.show();
        }
    }

    public void select(MouseEvent mouseEvent) {
        if (!(mouseEvent.getTarget() instanceof LabeledText)) {
            TableRow tableRow = ((TableCell) mouseEvent.getTarget()).getTableRow();
            if (tableRow.getIndex() < tableView.getItems().size()) {
                OrderTableItem orderTableItem = (OrderTableItem) tableView.getItems().get(tableRow.getIndex());
                if (orderTableItem != null) {
                    BuyingLog log = ((ViewOrdersManager) manager).getLogById(orderTableItem.getId());
                    logId = log.getId();
                    orderIdLabel.setText("#" + logId);
                    dateLabel.setText("Date: " + log.getDate().getYear() + "/" + log.getDate().getMonth() + "/" + log.getDate().getDayOfMonth());
                    moneyPaidLabel.setText("Paid money: " + log.getMoney() + "$");
                    discountAmountLabel.setText("Discount amount: " + log.getAmount() + "$");
                }
            }
        }
    }

    public void sort(ActionEvent actionEvent) {
        manager.openSort(this, manager);
    }
}
