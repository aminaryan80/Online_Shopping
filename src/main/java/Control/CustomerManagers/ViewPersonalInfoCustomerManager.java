package Control.CustomerManagers;

import Control.Manager;
import Models.Account.Account;
import View.CustomerMenus.customer.ViewPersonalInfoCustomerMenu;

public class ViewPersonalInfoCustomerManager extends Manager {
    public ViewPersonalInfoCustomerManager(Account account) {
        super(account);
        this.menu = new ViewPersonalInfoCustomerMenu(this);
    }

    // edit [field]
    public boolean isEnteredAccountFieldValid(String field) {
        //if(field.matches("(?i)(first name|last name|"))
        return true;
    }

    public void editAccountAttribute(String field, String newAttribute) {

    }

    public boolean isNewFieldAcceptable(String field, String newAttribute) {
        return true;
    }

    public boolean isEnteredFieldValid(String type) {
        return true;
    }

}
