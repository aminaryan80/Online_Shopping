package Server.Control.Principal;

import Models.Account.Account;
import Models.Account.Customer;
import Models.Shop.Off.Discount;
import Server.Control.Manager;

import java.time.LocalDate;
import java.util.ArrayList;

public class PrincipalManager extends Manager {

    public PrincipalManager(Account account) {
        super(account);
    }

    public int createDiscountCode(ArrayList<String> newDiscountInputs, ArrayList<String> allowedCustomersNames) {
        // 0:discount percent - 1:maximumDiscount - 2:discountUseCount - 3:beginningDate - 4:endingDate
        if (isDiscountInputsValid(newDiscountInputs)) {
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
            return 0;
        }
        return 1;
    }

    private boolean isDiscountInputsValid(ArrayList<String> inputs) {
        if (!checkPercent(inputs.get(0))) {
            return false;
        }
        if (!checkNumber(inputs.get(1))) {
            return false;
        }
        if (!checkNumber(inputs.get(2))) {
            return false;
        }
        if (!checkDate(inputs.get(3))) {
            return false;
        }
        return checkDate(inputs.get(4));
    }

    private ArrayList<Customer> getCustomersListByNames(ArrayList<String> allowedCustomersNames) {
        ArrayList<Customer> customers = new ArrayList<>();
        for (String customerName : allowedCustomersNames) {
            customers.add((Customer) Account.getAccountByUsername(customerName));
        }
        return customers;
    }

    public boolean isEnteredInputValid(String email, String phoneNumber) {
        return checkEmail(email) && checkPhoneNumber(phoneNumber);
    }
}
