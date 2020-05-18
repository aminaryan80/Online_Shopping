package Control.CustomerManagers;

import Models.Account.Customer;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerManagerTest {


    @Test
    public void viewCustomerBalance() {
        double excpectedBalance=500;
        Customer customer = new Customer("utilTestObjectCode20010917","masih","beigi","masihbr@gamil.com","09128569777","hello",excpectedBalance);
        CustomerManager customerManager = new CustomerManager(customer);
        assertTrue(customerManager.viewCustomerBalance()==excpectedBalance);
    }

    @Test
    public void viewDiscountCodes() {

    }
}