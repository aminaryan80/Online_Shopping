package Control;

import Models.Account.Account;
import Models.Account.Seller;
import View.ErrorProcessor;
import View.ViewPersonalInfoMenu;

public class ViewPersonalInfoManager extends Manager {
    public ViewPersonalInfoManager(Account account) {
        super(account);
        new ViewPersonalInfoMenu(this);
    }

    public boolean isEnteredFieldValid(String field) {
        return account.getChangeableFields().contains(field);
    }

    public void editField(String field, String newValue) {
        switch (field) {
            case "password":
                account.setPassword(newValue);
                break;
            case "email":
                if (checkEmail(newValue))
                    account.setEmail(newValue);
                else ErrorProcessor.invalidInput();
                break;
            case "firstname":
                account.setFirstName(newValue);
                break;
            case "lastname":
                account.setLastName(newValue);
                break;
            case "phonenumber":
                if (checkPhoneNumber(newValue))
                    account.setPhoneNumber(newValue);
                else ErrorProcessor.invalidInput();
                break;
            case "balance":
                if (checkNumber(newValue))
                    account.setBalance(Double.parseDouble(newValue));
                else ErrorProcessor.invalidInput();
                break;
            case "companyname":
                ((Seller) account).setCompanyName(newValue);
                break;
        }
    }
}
