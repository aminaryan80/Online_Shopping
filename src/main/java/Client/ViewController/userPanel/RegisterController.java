package Client.ViewController.userPanel;

import Client.ViewController.MainController;
import Models.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RegisterController extends MainController implements Initializable {

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!isPrincipalExists) {
            registerAsPrincipal();
        }
    }

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
            type = "PRINCIPAL";
        } else if (sellerRB.isSelected()) {
            type = "SELLER";
        } else if (customerRB.isSelected()) {
            type = "CUSTOMER";
        }
        String response = sendRequest("REGISTER " + username + " " + type + " " + Gson.INSTANCE.get().toJson(inputs));
        if (response.equals("0")) {
            success("Account created successfully.");
        } else error("Something went wrong.");
        //((CreateNewAccountManager) manager).createNewAccount(username, type, inputs);
        ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();
    }

    public void registerAsPrincipal() {
        customerRB.setDisable(true);
        sellerRB.setDisable(true);
        principalRB.setSelected(true);
        principalRBSelected(null);
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
