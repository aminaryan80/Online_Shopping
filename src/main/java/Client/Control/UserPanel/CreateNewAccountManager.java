package Client.Control.UserPanel;

import Client.Control.Manager;
import Client.ViewController.userPanel.RegisterController;
import Models.Account.Account;
import Models.Account.Customer;
import Models.Account.Principal;
import Models.Shop.Request.AddSellerRequest;

import java.util.ArrayList;

public class CreateNewAccountManager extends Manager {
    public CreateNewAccountManager(Account account, boolean status) {
        super(account);
        RegisterController controller = (RegisterController) loadFxml(Addresses.REGISTER, true);
        if (status) {
            controller.registerAsPrincipal();
        }
    }

    public void createNewAccount(ArrayList<String> inputs, String username, String type) {
        // 0password, 1email, 2phoneNumber, 3firstName, 4lastName, 5(balance), 6(companyName)
        if (canCreateNewAccount(username, type)) {
            if (isEnteredInputValid(inputs, type)) {
                switch (type) {
                    case "customer":
                        // String username, String firstName, String lastName, String email, String phoneNumber, String password, double balance
                        new Customer(username, inputs.get(3), inputs.get(4), inputs.get(1), inputs.get(2), inputs.get(0), Double.parseDouble(inputs.get(5)));
                        break;
                    case "seller":
                        // String username, String firstName, String lastName, String email, String phoneNumber, String password, double balance, String companyName
                        new AddSellerRequest(username, inputs.get(3), inputs.get(4), inputs.get(1), inputs.get(2),
                                inputs.get(0), Double.parseDouble(inputs.get(5)), inputs.get(6));
                        break;
                    case "principal":
                        // String username, String firstName, String lastName, String email, String phoneNumber, String password
                        new Principal(username, inputs.get(3), inputs.get(4), inputs.get(1), inputs.get(2), inputs.get(0));
                        isPrincipalExists = true;
                        break;
                }
            } else error("Invalid input");
        }
    }

    public boolean canCreateNewAccount(String username, String type) {
        if (type.equals("principal") && isPrincipalExists()) {
            error("Cannot create new principal account.");
            return false;
        }
        if (userExistsWithUsername(username)) {
            error("User exists with username.");
            return false;
        }
        return true;
    }

    private boolean isEnteredInputValid(ArrayList<String> inputs, String type) {
        // 0password, 1email, 2phoneNumber, 3firstName, 4lastName, 5(balance), 6(companyName)
        if (!checkEmail(inputs.get(1))) {
            return false;
        }
        if (!checkPhoneNumber(inputs.get(2))) {
            return false;
        }
        if (type.equals("customer") || type.equals("seller")) {
            return checkNumber(inputs.get(5));
        }
        return true;
    }
}
