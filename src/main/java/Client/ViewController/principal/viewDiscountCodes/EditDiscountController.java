package Client.ViewController.principal.viewDiscountCodes;

import Client.Control.Manager;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EditDiscountController extends ViewDiscountCodesController implements Initializable {
    public TextField newValueField;
    public RadioButton beginningDateRB;
    public RadioButton endingDateRB;
    public RadioButton percentRB;
    public RadioButton maxAmountRB;
    public RadioButton useCountRB;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void editDiscount(ActionEvent actionEvent) {
        if (beginningDateRB.isSelected()) {
            editBeginningDate();
        } else if (endingDateRB.isSelected()) {
            editEndingDate();
        } else if (percentRB.isSelected()) {
            editDiscountPercent();
        } else if (maxAmountRB.isSelected()) {
            editMaximumAmount();
        } else if (useCountRB.isSelected()) {
            editDiscountUserCount();
        }
        ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
        loadFxml(Manager.Addresses.VIEW_DISCOUNT_CODES);
    }

    private void editBeginningDate() {
        String input = newValueField.getText();
        String response = sendRequest("EDIT_DISCOUNT " + discountId + " BEGINNING_DATE " + input);
        if (response.equals("0")) {
            success("Discount changed successfully.");
        } else error("Something went wrong.");
    }

    private void editEndingDate() {
        String input = newValueField.getText();
        String response = sendRequest("EDIT_DISCOUNT " + discountId + " ENDING_DATE " + input);
        if (response.equals("0")) {
            success("Discount changed successfully.");
        } else error("Something went wrong.");
    }

    private void editDiscountPercent() {
        String input = newValueField.getText();
        String response = sendRequest("EDIT_DISCOUNT " + discountId + " PERCENT " + input);
        if (response.equals("0")) {
            success("Discount changed successfully.");
        } else error("Something went wrong.");
    }

    private void editMaximumAmount() {
        String input = newValueField.getText();
        String response = sendRequest("EDIT_DISCOUNT " + discountId + " MAXIMUM_AMOUNT " + input);
        if (response.equals("0")) {
            success("Discount changed successfully.");
        } else error("Something went wrong.");
    }

    private void editDiscountUserCount() {
        String input = newValueField.getText();
        String response = sendRequest("EDIT_DISCOUNT " + discountId + " USE_COUNT " + input);
        if (response.equals("0")) {
            success("Discount changed successfully.");
        } else error("Something went wrong.");
    }
}
