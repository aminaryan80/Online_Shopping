package Control.CustomerManagers;

import Control.UtilTestObject;
import Models.Account.Customer;
import Models.Shop.Off.Discount;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class CustomerManagerTest {

    Customer customer = new Customer(UtilTestObject.CUSTOMER,"masih","beigi","masihbr@gamil.com","09128569777","hello",500);
    CustomerManager customerManager = new CustomerManager(customer);
    @Test
    public void viewCustomerBalance() {
        double excpectedBalance=500;
        assertTrue(customerManager.viewCustomerBalance()==excpectedBalance);
    }

    @Test
    public void viewDiscountCodes() {
        ArrayList<String> customers = new ArrayList<>();
        customers.add(customer.getUsername());
        Discount discount = new Discount(LocalDate.parse("2000-01-01"),LocalDate.parse("2002-01-01"),20,50,1,customers);
        customer.addDiscount(discount);
        assertEquals(customerManager.viewDiscountCodes().get(0),discount.getId()+": "+discount.getDiscountPercent());
    }
}