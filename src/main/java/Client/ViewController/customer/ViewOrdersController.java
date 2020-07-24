package Client.ViewController.customer;

import Models.Account.Customer;
import Models.Shop.Category.Sort;
import Models.Shop.Log.Log;
import Server.Control.Manager;
import Client.ViewController.Controller;
import Client.ViewController.customer.cart.CartTableItem;
import Models.Gson;
import Models.Shop.Log.BuyingLog;
import com.google.gson.reflect.TypeToken;
import com.sun.javafx.scene.control.skin.LabeledText;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ViewOrdersController extends Controller implements Initializable {


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
    private Sort currentSort;
    private List<BuyingLog> logs;
    private List<BuyingLog> log;
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
//        if (manager instanceof ViewOrdersManager) {
//            ArrayList<BuyingLog> logs = ((ViewOrdersManager) manager).getLogs();
        logs = Gson.INSTANCE.get().fromJson(sendRequest("GET_BUYING_LOGS"+" "+accountUsername),new TypeToken<ArrayList<BuyingLog>>(){}.getType());
        int number = 1;
        log = new ArrayList<>();
        log.addAll(logs);
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

        return orderTableItems;
    }


    public void showBoughtProducts(ActionEvent actionEvent) {
        if (!orderIdLabel.getText().isEmpty()) {
            Stage workingStage = new Stage();
            ShowOrderProductsController controller = null;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(Addresses.SHOW_ORDER_PRODUCTS.getAddress()));
                Parent root = loader.load();
                controller = loader.getController();
                controller.init(sendRequest("SHOW_BOUGHT_PRODUCTS" + " " + accountUsername + " " + logId));
                Scene scene = new Scene(root);
                workingStage.setTitle("AP Project");
                workingStage.setScene(scene);
                workingStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//            ((ViewOrdersManager) manager).showProductsByLogId(logId);
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
                    BuyingLog log = Gson.INSTANCE.get().fromJson(sendRequest("GET_BUYING_LOG"+" "+accountUsername+" "+orderTableItem.getId()),BuyingLog.class);
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
        openSort(this);
    }

    public void back(ActionEvent actionEvent) {
        loadFxml(Addresses.CUSTOMER_MENU);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }

    public ArrayList<Object> sort(String sort, boolean isAscending) {
        currentSort = new Sort(sort, isAscending);
        log = new ArrayList<>();
        log.addAll(logs);
        applySort();
        return new ArrayList<>(log);
    }

    private void applySort() {
        if (currentSort == null) {
            return;
        }
        String field = currentSort.getField();
        if (field.equals("money")) {
            sortByMoney();
        } else {
            sortByDate();
        }
        if (!currentSort.isAscending()) {
            Collections.reverse(log);
        }
    }

    private void sortByMoney() {
        BuyingLog[] logsForSort = log.toArray(new BuyingLog[0]);
        for (int i = 0; i < logsForSort.length; i++) {
            for (int j = i + 1; j < logsForSort.length; j++) {
                if (logsForSort[i].getMoney() > logsForSort[j].getMoney()) {
                    BuyingLog temp = logsForSort[i];
                    logsForSort[i] = logsForSort[j];
                    logsForSort[j] = temp;
                }
            }
        }
        log = Arrays.asList(logsForSort);
    }

    private void sortByDate() {
        BuyingLog[] logsForSort = log.toArray(new BuyingLog[0]);
        for (int i = 0; i < logsForSort.length; i++) {
            for (int j = i + 1; j < logsForSort.length; j++) {
                if (logsForSort[i].getDate().isBefore(logsForSort[j].getDate())) {
                    BuyingLog temp = logsForSort[i];
                    logsForSort[i] = logsForSort[j];
                    logsForSort[j] = temp;
                }
            }
        }
        log = Arrays.asList(logsForSort);
    }

    public ArrayList<Object> disableSort() {
        currentSort = null;
        log = logs;
        return new ArrayList<>(log);
    }

    public ArrayList<String> getSortFields() {
        ArrayList<String> fields = new ArrayList<>();
        fields.add("money");
        fields.add("date");
        return fields;
    }
}
