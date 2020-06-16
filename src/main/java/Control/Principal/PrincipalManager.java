package Control.Principal;

import Control.EditPasswordManager;
import Control.Manager;
import Control.Principal.ManageCategories.ManageCategoriesManager;
import Control.Principal.ViewDiscountCodes.ViewDiscountCodesManager;
import Models.Account.Account;
import Models.Account.Customer;
import Models.Shop.Off.Discount;
import ViewController.principal.PrincipalController;

import java.time.LocalDate;
import java.util.ArrayList;

public class PrincipalManager extends Manager {

    public PrincipalManager(Account account, Addresses address, Manager manager) {
        super(account, address, manager);
        //this.menu = new PrincipalMenu(this);
        PrincipalController controller = (PrincipalController) loadFxml(Addresses.PRINCIPAL_MENU);
        controller.setPrincipal(account);
        controller.init();
    }

    public void createDiscountCode(ArrayList<String> newDiscountInputs, ArrayList<String> allowedCustomersNames) {
        // 0:discount percent - 1:maximumDiscount - 2:discountUseCount - 3:beginningDate - 4:endingDate
        Discount discount = new Discount(
                LocalDate.parse(newDiscountInputs.get(3)),
                LocalDate.parse(newDiscountInputs.get(4)),
                Integer.parseInt(newDiscountInputs.get(0)),
                Double.parseDouble(newDiscountInputs.get(1)),
                Integer.parseInt(newDiscountInputs.get(2)),
                allowedCustomersNames
        );
        for (Customer customer : getCustomersListByNames(allowedCustomersNames)) {
            customer.addDiscount(discount);
        }
    }

    private ArrayList<Customer> getCustomersListByNames(ArrayList<String> allowedCustomersNames) {
        ArrayList<Customer> customers = new ArrayList<>();
        for (String customerName : allowedCustomersNames) {
            customers.add((Customer) Account.getAccountByUsername(customerName));
        }
        return customers;
    }

    public boolean isEnteredInputValid(String email, String phoneNumber) {
        if (checkEmail(email) && checkPhoneNumber(phoneNumber)) {
            success("Profile updated successfully.");
            return true;
        } else error("Invalid input");
        return false;
    }

    public void editPassword() {
        new EditPasswordManager(account);
    }

    public void openManageUsers() {
        new ManageUsersManager(account, Addresses.PRINCIPAL_MENU, this);
    }

    public void openManageProducts() {
        new ManageAllProductsManager(account, Addresses.PRINCIPAL_MENU, this);
    }

    public void openManageCategories() {
        new ManageCategoriesManager(account, Addresses.PRINCIPAL_MENU, this);
    }

    public void openManageRequests() {
        new ManageRequestsManager(account, Addresses.PRINCIPAL_MENU, this);
    }

    public void openOffsMenu() {
        // TODO WTF
    }

    public void openViewDiscountCodes() {
        new ViewDiscountCodesManager(account, Addresses.PRINCIPAL_MENU, this);
    }
}
