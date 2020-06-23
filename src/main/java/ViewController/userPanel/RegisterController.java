package ViewController.userPanel;

import Control.UserPanel.CreateNewAccountManager;
import ViewController.Controller;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class RegisterController extends Controller {

    public TextField usernameField;
    public TextField passwordField;
    public TextField emailField;
    public TextField phoneNumberField;
    public TextField firstNameField;
    public TextField lastNameField;
    public TextField balanceField;
    public TextField companyNameField;
    public RadioButton principalRB;
    public RadioButton sellerRB;
    public RadioButton customerRB;

    public void register(ActionEvent actionEvent) {
        // 0password, 1email, 2phoneNumber, 3firstName, 4lastName, 5(balance), 6(companyName)
        ArrayList<String> inputs = new ArrayList<>();
        String username = usernameField.getText();
        inputs.add(passwordField.getText());
        inputs.add(emailField.getText());
        inputs.add(phoneNumberField.getText());
        inputs.add(firstNameField.getText());
        inputs.add(lastNameField.getText());
        inputs.add(balanceField.getText());
        inputs.add(companyNameField.getText());
        String type = "";
        if (principalRB.isSelected()) {
            type = "principal";
        } else if (sellerRB.isSelected()) {
            type = "seller";
        } else if (customerRB.isSelected()) {
            type = "customer";
        }
        ((CreateNewAccountManager) manager).createNewAccount(inputs, username, type);
        ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
    }

    public void principalRBSelected(ActionEvent actionEvent) {
        balanceField.setVisible(false);
        companyNameField.setVisible(false);
    }

    public void sellerRBSelected(ActionEvent actionEvent) {
        balanceField.setVisible(true);
        companyNameField.setVisible(true);
    }

    public void customerRBSelected(ActionEvent actionEvent) {
        balanceField.setVisible(true);
        companyNameField.setVisible(false);
    }
}
