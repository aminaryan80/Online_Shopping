package Client.ViewController.principal.viewDiscountCodes;

import Models.Shop.Category.Sort;
import Server.Control.Manager;
import Client.ViewController.Controller;
import Models.Shop.Off.Discount;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class ViewDiscountCodesController extends Controller implements Initializable {

    protected static String discountId;
    public TextField discountIdField;
    public Label viewDiscountLabel;
    public TableView<Discount> discountsTable;
    public TableColumn<Discount, String> discountIdCol;
    public TableColumn<Discount, Double> discountPercentCol;
    public TableColumn<Discount, Integer> discountUseCountLimit;
    public TableColumn<Discount, LocalDate> beginningDateCol;
    public TableColumn<Discount, LocalDate> endingDateCol;
    private Sort currentSort;
    private List<Discount> discounts;
    private List<Discount> myDiscounts;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initialize();
    }

    private void initialize() {
        myDiscounts = getAllDiscounts();
        discounts = new ArrayList<>();
        discounts.addAll(myDiscounts);
        discountsTable.setItems(FXCollections.observableArrayList(myDiscounts));
        discountIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        discountPercentCol.setCellValueFactory(new PropertyValueFactory<>("discountPercent"));
        discountUseCountLimit.setCellValueFactory(new PropertyValueFactory<>("discountUseCount"));
        beginningDateCol.setCellValueFactory(new PropertyValueFactory<>("beginningDate"));
        endingDateCol.setCellValueFactory(new PropertyValueFactory<>("endingDate"));
    }

    public void initTable(ArrayList<Object> tableObjects) {
        ArrayList<Discount> tableDiscounts = new ArrayList<>();
        for (Object tableProduct : tableObjects) {
            tableDiscounts.add((Discount) tableProduct);
        }
        discountsTable.setItems(FXCollections.observableArrayList(tableDiscounts));
        discountIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        discountPercentCol.setCellValueFactory(new PropertyValueFactory<>("discountPercent"));
        discountUseCountLimit.setCellValueFactory(new PropertyValueFactory<>("discountUseCount"));
        beginningDateCol.setCellValueFactory(new PropertyValueFactory<>("beginningDate"));
        endingDateCol.setCellValueFactory(new PropertyValueFactory<>("endingDate"));
    }

    public void viewDiscount(ActionEvent actionEvent) {
        viewDiscountLabel.setText(sendRequest("GET_DISCOUNT_DETAILS " + discountIdField.getText()));
    }

    public void editDiscount(ActionEvent actionEvent) {
        discountId = discountIdField.getText();
        loadFxml(Addresses.EDIT_DISCOUNTS, true);
    }

    public void deleteDiscount(ActionEvent actionEvent) {
        discountId = discountIdField.getText();
        String response = sendRequest("DELETE_DISCOUNT " + discountId);
        if (response.equals("0")) {
            success("Discount deleted successfully.");
        } else error("Something went wrong.");
        initialize();
    }

    public void sort(ActionEvent actionEvent) {
        openSort(this);
    }

    public void back() {
        loadFxml(Addresses.PRINCIPAL_MENU);
    }

    public ArrayList<Object> disableSort() {
        currentSort = null;
        return new ArrayList<>(myDiscounts);
    }

    public ArrayList<String> getSortFields() {
        ArrayList<String> fields = new ArrayList<>();
        fields.add("discountpercent");
        fields.add("beginningdate");
        fields.add("endingdate");
        return fields;
    }

    public ArrayList<Object> sort(String sort, boolean isAscending) {
        discounts = new ArrayList<>();
        discounts.addAll(myDiscounts);
        currentSort = new Sort(sort, isAscending);
        applySort();
        return new ArrayList<>(discounts);
    }

    private void applySort() {
        if (currentSort == null) {
            return;
        }
        String field = currentSort.getField();
        if (field.equals("discountpercent")) {
            sortByDiscountPercentage();
        } else if (field.equals("beginningdate")) {
            sortByBeginningDate();
        } else {
            sortByEndingDate();
        }
        if (!currentSort.isAscending()) {
            Collections.reverse(discounts);
        }
    }

    private void sortByDiscountPercentage() {
        Discount[] discountsForSort = discounts.toArray(new Discount[0]);
        for (int i = 0; i < discountsForSort.length; i++) {
            for (int j = i + 1; j < discountsForSort.length; j++) {
                if (discountsForSort[i].getDiscountPercent() > discountsForSort[j].getDiscountPercent()) {
                    Discount temp = discountsForSort[i];
                    discountsForSort[i] = discountsForSort[j];
                    discountsForSort[j] = temp;
                }
            }
        }
        discounts = Arrays.asList(discountsForSort);
    }

    private void sortByBeginningDate() {
        Discount[] discountsForSort = discounts.toArray(new Discount[0]);
        for (int i = 0; i < discountsForSort.length; i++) {
            for (int j = i + 1; j < discountsForSort.length; j++) {
                if (discountsForSort[i].getBeginningDate().isBefore(discountsForSort[j].getBeginningDate())) {
                    Discount temp = discountsForSort[i];
                    discountsForSort[i] = discountsForSort[j];
                    discountsForSort[j] = temp;
                }
            }
        }
        discounts = Arrays.asList(discountsForSort);
    }

    private void sortByEndingDate() {
        Discount[] discountsForSort = discounts.toArray(new Discount[0]);
        for (int i = 0; i < discountsForSort.length; i++) {
            for (int j = i + 1; j < discountsForSort.length; j++) {
                if (discountsForSort[i].getEndingDate().isBefore(discountsForSort[j].getEndingDate())) {
                    Discount temp = discountsForSort[i];
                    discountsForSort[i] = discountsForSort[j];
                    discountsForSort[j] = temp;
                }
            }
        }
        discounts = Arrays.asList(discountsForSort);
    }
}
