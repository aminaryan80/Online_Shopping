package ViewController.principal;

import Control.Principal.PrincipalManager;
import Models.Account.Account;
import Models.Account.Customer;
import ViewController.Controller;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class CreateNewDiscountController extends Controller {

    public TextField percentField;
    public TextField customerField;
    public TextField maxAmountField;
    public TextField useCountField;
    public TextField beginningDateField;
    public TextField endingDateField;
    private ArrayList<String> customersNames = new ArrayList<>();

    public void createDiscount(ActionEvent actionEvent) {
        ArrayList<String> inputs = new ArrayList<>();
        inputs.add(percentField.getText());
        inputs.add(maxAmountField.getText());
        inputs.add(useCountField.getText());
        inputs.add(beginningDateField.getText());
        inputs.add(endingDateField.getText());
        ((PrincipalManager) manager).createDiscountCode(inputs, customersNames);
        ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();

    }

    public void addCustomer(ActionEvent actionEvent) {
        Account account;
        if ((account = Account.getAccountByUsername(customerField.getText())) != null) {
            if (account instanceof Customer) {
                customersNames.add(customerField.getText());
            } else manager.error("Customer doesn't exists with this name.");
        } else manager.error("Customer doesn't exists with this name.");
    }
}
