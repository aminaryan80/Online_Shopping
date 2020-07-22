package Client.ViewController.customer.cart;

import Client.Control.CustomerManagers.PurchaseManager;
import Client.Control.Manager;
import Client.ViewController.Controller;
import Models.Gson;
import Models.Shop.Off.Discount;
import Models.Shop.Product.Product;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class PurchasePageController extends Controller implements Initializable {

    public TableView tableView;
    public TableColumn numberColumn;
    public TableColumn nameColumn;
    public TableColumn descriptionColumn;
    public TableColumn quantityColumn;
    public TableColumn priceColumn;
    public Label totalAmount;
    public TextField phoneNumberField;
    public TextField addressField;
    public TextField discountCodeField;
    public Label addressMassage;
    public Label phoneNumberMassage;
    public Label discountCodeMassage;
    public Label totalAmountToPay;
    public Button phoneNumberConfirm;
    public Button phoneNumberClear;
    public Button purchaseButton;
    public Button discountCodeConfirm;
    public Button discountCodeClear;

    @Override
    public void init() {
        totalAmount.setText(sendRequest("TOTAL_AMOUNT" + " " + accountUsername));
        ArrayList<CartTableItem> cartTableItems = getCartTableItems();
        ArrayList<Object> objects = new ArrayList<>();
        objects.addAll(cartTableItems);
        initTable(objects);
    }

    public void initTable(ArrayList<Object> tableObjects) {
        ArrayList<CartTableItem> cartTableItems = new ArrayList<>();
        for (Object tableProduct : tableObjects) {
            cartTableItems.add((CartTableItem) tableProduct);
        }
        ObservableList<CartTableItem> data = FXCollections.observableList(cartTableItems);
        numberColumn.setCellValueFactory(new PropertyValueFactory<CartTableItem, Integer>("number"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<CartTableItem, String>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<CartTableItem, String>("description"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<CartTableItem, Integer>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<CartTableItem, Double>("price"));
        tableView.setItems(data);
    }

    private ArrayList<CartTableItem> getCartTableItems() {
        ArrayList<CartTableItem> cartTableItems = new ArrayList<>();
//        if (manager instanceof PurchaseManager) {
//            HashMap<Product, Integer> productsInCart = ((PurchaseManager) manager).getProductsInCart();
        HashMap<Product, Integer> productsInCart = Gson.INSTANCE.get().fromJson(sendRequest("GET_CART_PRODUCTS" + " " + accountUsername), new TypeToken<HashMap<Product, Integer>>() {
        }.getType());
        int number = 1;
        for (Product product : productsInCart.keySet()) {
            cartTableItems.add(new CartTableItem(
                    number,
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    productsInCart.get(product),
                    product.getPrice()
            ));
            number++;
        }
        return cartTableItems;
    }


    public void clearAddressField(ActionEvent actionEvent) {
        addressField.setText("");
        addressMassage.setText("");
        addressMassage.setStyle("");
        addressMassage.setVisible(false);
    }

    public void confirmAddress(ActionEvent actionEvent) {
        if (addressField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Fill the text first!", ButtonType.OK);
            alert.show();
        } else {
            addressMassage.setText("CONFIRMED!");
            addressMassage.setStyle("-fx-fill: green");
            addressMassage.setVisible(true);
            phoneNumberField.setVisible(true);
            phoneNumberConfirm.setVisible(true);
            phoneNumberClear.setVisible(true);
            checkPurchaseOption();
        }
    }

    public void purchase(ActionEvent actionEvent) throws PurchaseManager.WrongDiscountIdException, PurchaseManager.UsedDiscountIdException {
//        ArrayList<String> info = new ArrayList<>();
//        info.add(addressField.getText());
//        info.add(phoneNumberField.getText());
//        if (((PurchaseManager) manager).canPay(discountCodeField.getText())) {
        if (sendRequest("CAN_PAY" + " " + accountUsername + " " + discountCodeField.getText()).equals("YES")) {
            sendRequest("PAY" + " " + accountUsername + " " + discountCodeField.getText() + " " + addressField.getText() + " " + phoneNumberField.getText());
//            ((PurchaseManager) manager).pay(info, discountCodeField.getText());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Purchase was successful!", ButtonType.OK);
            alert.show();
            back(null);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You can't pay!", ButtonType.OK);
            alert.show();
        }
    }

    public void clearPhoneNumberFiled(ActionEvent actionEvent) {
        phoneNumberField.setText("");
        phoneNumberMassage.setText("");
        phoneNumberMassage.setStyle("");
        phoneNumberMassage.setVisible(false);
    }

    public void clearDiscountCodeField(ActionEvent actionEvent) {
        discountCodeMassage.setText("");
        discountCodeField.setText("");
        discountCodeMassage.setStyle("");
        discountCodeMassage.setVisible(false);
        totalAmountToPay.setVisible(false);
        totalAmountToPay.setText("");
    }

    public void confirmDiscountCode(ActionEvent actionEvent) {
        if (discountCodeField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Fill the text first!", ButtonType.OK);
            alert.show();
        } else {
            String discountId = discountCodeField.getText();
//            PurchaseManager purchaseManager = ((PurchaseManager) manager);
//            if (purchaseManager.hasDiscountCode(discountId)) {
            if (!discountId.matches("(?i)no")) {
                if (sendRequest("IS_DISCOUNTCODE_VALID" + " " + accountUsername + " " + discountId).equals("YES")) {
                    if (sendRequest("DOES_DISCOUNT_BELONG_TO_CUSTOMER" + " " + accountUsername + " " + discountId).equals("YES")) {
                        if (sendRequest("CAN_USE_DISCOUNT" + " " + accountUsername + " " + discountId).equals("YES")) {
                            if (sendRequest("IS_DISCOUNTCODE_ACTIVE" + " " + accountUsername + " " + discountId).equals("YES")) {
                                totalAmountToPay.setText("Amount to pay: " + sendRequest("TOTAL_AMOUNT" + " " + accountUsername + " " + discountId));
                                totalAmountToPay.setVisible(true);
                                discountCodeMassage.setText("CONFIRMED!");
                                discountCodeMassage.setStyle("-fx-fill: green");
                                discountCodeMassage.setVisible(true);
                                checkPurchaseOption();
                            } else {
                                discountCodeMassage.setText("THIS CODE IS NOT ACTIVE!");
                                discountCodeMassage.setStyle("-fx-fill: red");
                                discountCodeMassage.setVisible(true);
                            }
                        } else {
                            discountCodeMassage.setText("THIS CODE HAS BEEN FULLY USED!");
                            discountCodeMassage.setStyle("-fx-fill: red");
                            discountCodeMassage.setVisible(true);
                        }
                    } else {
                        discountCodeMassage.setText("THIS CODE IS NOT YOURS!");
                        discountCodeMassage.setStyle("-fx-fill: red");
                        discountCodeMassage.setVisible(true);
                    }
                } else {
                    discountCodeMassage.setText("CODE NOT VALID!");
                    discountCodeMassage.setStyle("-fx-fill: red");
                    discountCodeMassage.setVisible(true);
                }
            } else {
                discountCodeMassage.setText("CONFIRMED!");
                discountCodeMassage.setStyle("-fx-fill: green");
                discountCodeMassage.setVisible(true);
                totalAmountToPay.setText("Amount to pay: " + sendRequest("TOTAL_AMOUNT" + " " + accountUsername));
                totalAmountToPay.setVisible(true);
                checkPurchaseOption();
            }
        }
    }

    private void checkPurchaseOption() {
        if (discountCodeMassage.getText().equals("CONFIRMED!"))
            if (phoneNumberMassage.getText().equals("CONFIRMED!"))
                if (addressMassage.getText().equals("CONFIRMED!")) {
                    purchaseButton.setVisible(true);
                }
    }

    public void confirmPhoneNumberField(ActionEvent actionEvent) {
        if (phoneNumberField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Fill the text first!", ButtonType.OK);
            alert.show();
        } else {
            if (sendRequest("IS_PHONENUMBER_VALID" + " " + phoneNumberField.getText()).equals("NO")) {
                phoneNumberMassage.setText("format : 09127773333 ");
                phoneNumberMassage.setStyle("-fx-fill: red");
                phoneNumberMassage.setVisible(true);
            } else {
                phoneNumberMassage.setText("CONFIRMED!");
                phoneNumberMassage.setStyle("-fx-fill: green");
                phoneNumberMassage.setVisible(true);
                discountCodeField.setVisible(true);
                discountCodeConfirm.setVisible(true);
                discountCodeClear.setVisible(true);
                checkPurchaseOption();
            }
        }
    }

    public void back(ActionEvent actionEvent) {
        loadFxml(Manager.Addresses.VIEW_CART);
    }

    public void sort(ActionEvent actionEvent) {
        manager.openSort(this, manager);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }
}
