package Control.CustomerManagers;

import Control.Manager;
import Models.Account.Account;
import Models.Account.Customer;
import View.CustomerMenus.customer.ViewPersonalInfoCustomerMenu;

public class ViewPersonalInfoCustomerManager extends Manager {
    private Customer customer = (Customer) account;

    public ViewPersonalInfoCustomerManager(Account account) {
        super(account);
        this.menu = new ViewPersonalInfoCustomerMenu(this);
    }

    public String viewCustomerPersonalInfo() {
        return account.toString();
    }

    // edit [field]
    public boolean isEnteredAccountFieldValid(String field) {
        if (field.matches("(?i)(name|last name|phone number|email|password|balance)")) return true;
        else return false;
    }

    public void editAccountAttribute(String field, String newAttribute) {
        if(field.matches("name")){
            customer.setFirstName(newAttribute);
        } else if(field.matches("last name")){
            customer.setLastName(newAttribute);
        } else if(field.matches("email")){
            customer.setEmail(newAttribute);
        } else if(field.matches("phone number")) {
            customer.setPhoneNumber(newAttribute);
        }else if(field.matches("password")){
            customer.setPassword(newAttribute);
        } if(field.matches("balance")){
            customer.setBalance(Double.parseDouble(newAttribute));
        }
    }

    public boolean isNewFieldAcceptable(String field, String newAttribute) {
        if (field.matches("(?i)(name|last name|email|password)")) return true;
        else if (newAttribute.matches("\\d{11}")) return true;
        else if(newAttribute.matches("\\d+(\\.\\d+)?")) return true;
        else return false;
    }

    public boolean checkPassword(String oldPassword) {
        if(customer.getPassword().equals(oldPassword)) return true;
        else return false;
    }
}
