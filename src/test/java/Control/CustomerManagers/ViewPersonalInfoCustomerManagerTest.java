package Control.CustomerManagers;

import Control.UtilTestObject;
import Models.Account.Customer;
import Models.Account.Seller;
import Models.Shop.Category.Category;
import Models.Shop.Product.Product;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class ViewPersonalInfoCustomerManagerTest {
    Customer customer = new Customer(UtilTestObject.CUSTOMER, "masih", "beigi", "masihbr@gamil.com", "09128569777", "hello", 500);
    ViewPersonalInfoCustomerManager viewPersonalInfoCustomerManager = new ViewPersonalInfoCustomerManager(customer);
    Seller seller = new Seller(UtilTestObject.SELLER, "masih", "beigi", "masihbr@gamil.com", "09128569777", "hello", 500, "apple");
    HashMap<String, Integer> features = new HashMap<>();
    Product product = new Product("macbook",
            "apple",
            10000, seller,
            true,
            new Category("1", null, features, new ArrayList<String>()),
            "des",
            null);
    Product product2 = new Product("macbook2",
            "apple",
            1020, seller,
            true,
            new Category("1", null, features, new ArrayList<String>()),
            "des",
            null);


    @Test
    public void viewCustomerPersonalInfo() {
        String expected = "utilTestObjectCode2001091720200518-1 : \n" +
                "firstName = masih\n" +
                "lastName = beigi\n" +
                "email = masihbr@gamil.com\n" +
                "phoneNumber = 09128569777\n" +
                "password = hello\n" +
                "balance = 500.0";
        assertEquals(expected, viewPersonalInfoCustomerManager.viewCustomerPersonalInfo());
    }

    @Test
    public void isEnteredAccountFieldValid() {
        assertFalse(viewPersonalInfoCustomerManager.isEnteredAccountFieldValid("chert"));
        assertTrue(viewPersonalInfoCustomerManager.isEnteredAccountFieldValid("name"));
        assertTrue(viewPersonalInfoCustomerManager.isEnteredAccountFieldValid("last name"));
        assertTrue(viewPersonalInfoCustomerManager.isEnteredAccountFieldValid("phone number"));
        assertTrue(viewPersonalInfoCustomerManager.isEnteredAccountFieldValid("email"));
        assertTrue(viewPersonalInfoCustomerManager.isEnteredAccountFieldValid("password"));
        assertTrue(viewPersonalInfoCustomerManager.isEnteredAccountFieldValid("balance"));
    }

    @Test
    public void editAccountAttribute() {
        viewPersonalInfoCustomerManager.editAccountAttribute("name", "ali");
        assertEquals(customer.getFirstName(), "ali");
        viewPersonalInfoCustomerManager.editAccountAttribute("last name", "rezaee");
        assertEquals(customer.getLastName(), "rezaee");
        viewPersonalInfoCustomerManager.editAccountAttribute("phone number", "09128339700");
        assertEquals(customer.getPhoneNumber(), "09128339700");
        viewPersonalInfoCustomerManager.editAccountAttribute("email", "aliRez80@gmail.com");
        assertEquals(customer.getEmail(), "aliRez80@gmail.com");
        viewPersonalInfoCustomerManager.editAccountAttribute("balance", "605");
        assertEquals(customer.getBalance(), 605, 2);
        viewPersonalInfoCustomerManager.editAccountAttribute("password", "123987456");
        assertEquals(customer.getPassword(), "123987456");
    }

    @Test
    public void isNewFieldAcceptable() {
        assertTrue(viewPersonalInfoCustomerManager.isNewFieldAcceptable("name", "everychert"));
        assertTrue(viewPersonalInfoCustomerManager.isNewFieldAcceptable("last name", "everychert"));
        assertTrue(viewPersonalInfoCustomerManager.isNewFieldAcceptable("password", "everychert"));
        assertTrue(viewPersonalInfoCustomerManager.isNewFieldAcceptable("email", "everychert"));
        assertFalse(viewPersonalInfoCustomerManager.isNewFieldAcceptable("phone number", "everychert"));
        assertFalse(viewPersonalInfoCustomerManager.isNewFieldAcceptable("phone number", "032"));
        assertTrue (viewPersonalInfoCustomerManager.isNewFieldAcceptable("phone number", "09129997777"));
        assertFalse(viewPersonalInfoCustomerManager.isNewFieldAcceptable("balance", "everychert"));
        assertTrue(viewPersonalInfoCustomerManager.isNewFieldAcceptable("balance", "703"));
        assertTrue(viewPersonalInfoCustomerManager.isNewFieldAcceptable("balance", "703.33"));
    }

    @Test
    public void checkPassword() {
        assertTrue(viewPersonalInfoCustomerManager.checkPassword("hello"));
    }
}