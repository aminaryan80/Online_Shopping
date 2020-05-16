package Control.UserPanel;

import Control.Manager;
import Models.Account.Account;
import Models.Account.Customer;
import Models.Account.Principal;
import Models.Account.Seller;
import View.ErrorProcessor;
import View.UserPanel.CreateNewAccountMenu;

import java.util.ArrayList;

public class CreateNewAccountManager extends Manager {
    public CreateNewAccountManager(Account account, String username, String type) {
        super(account);
        new CreateNewAccountMenu(this, username, type);
    }

    public void createNewAccount(ArrayList<String> inputs, String username, String type) {
        // 0username, 1password, 2email, 3phoneNumber, 4firstName, 5lastName, 6(balance), 7(companyName)
        switch (type) {
            case "customer":
                // String username, String firstName, String lastName, String email, String phoneNumber, String password, double balance
                new Customer(username, inputs.get(3), inputs.get(4), inputs.get(1), inputs.get(2), inputs.get(0), Double.parseDouble(inputs.get(5)));
                break;
            case "seller":
                // String username, String firstName, String lastName, String email, String phoneNumber, String password, double balance, String companyName
                new Seller(username, inputs.get(3), inputs.get(4), inputs.get(1), inputs.get(2), inputs.get(0),
                        Double.parseDouble(inputs.get(5)), inputs.get(6));
                break;
            case "principal":
                // String username, String firstName, String lastName, String email, String phoneNumber, String password
                new Principal(username, inputs.get(3), inputs.get(4), inputs.get(1), inputs.get(2), inputs.get(0));
                break;
        }
    }

    public boolean canCreateNewAccount(String username, String type) {
        if (checkAccountType(type)) {
            if (!userExistsWithUsername(username)) {
                return true;
            } else ErrorProcessor.userExistsWithThisUsername();
        } else ErrorProcessor.invalidType();
        return false;
    }
}
