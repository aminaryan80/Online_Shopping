package ViewController.principal.viewDiscountCodes;

import Control.Principal.ViewDiscountCodes.EditDiscountCodeManager;
import ViewController.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditDiscountController extends Controller {
    @FXML
    private TextField newValueField;
    @FXML
    private RadioButton beginningDateRB;
    @FXML
    private RadioButton endingDateRB;
    @FXML
    private RadioButton percentRB;
    @FXML
    private RadioButton maxAmountRB;
    @FXML
    private RadioButton useCountRB;

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
    }

    private void editBeginningDate() {
        String input = newValueField.getText();
        ((EditDiscountCodeManager) manager).editBeginningDate(input);
    }

    private void editEndingDate() {
        String input = newValueField.getText();
        ((EditDiscountCodeManager) manager).editEndingDate(input);
    }

    private void editDiscountPercent() {
        String input = newValueField.getText();
        ((EditDiscountCodeManager) manager).editDiscountPercent(input);
    }

    private void editMaximumAmount() {
        String input = newValueField.getText();
        ((EditDiscountCodeManager) manager).editMaximumAmount(input);
    }

    private void editDiscountUserCount() {
        String input = newValueField.getText();
        ((EditDiscountCodeManager) manager).editDiscountUserCount(input);
    }
}
