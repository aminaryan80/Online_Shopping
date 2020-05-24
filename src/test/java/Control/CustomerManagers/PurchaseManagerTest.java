package Control.CustomerManagers;

import Control.UtilTestObject;
import Models.Account.Customer;
import Models.Account.Seller;
import Models.Shop.Category.Category;
import Models.Shop.Off.Discount;
import Models.Shop.Product.Product;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class PurchaseManagerTest {
    HashMap<String,Integer> features = new HashMap<>();
    Seller seller = new Seller(UtilTestObject.SELLER,"masih","beigi","masihbr@gamil.com","09128569777","hello",500,"apple");
    Product product = new Product("macbook",
            "apple",
            1000,seller,
            true,
            new Category("1",null,features,null),
            "des",
            null);
    Product product2 = new Product("macbook2",
            "apple",
            1020,seller,
            true,
            new Category("1",null,features,null),
            "des",
            null);

    @Test
    public void canPay() {
        Customer customerTest = new Customer(UtilTestObject.CUSTOMER,"masih","beigi","masihbr@gamil.com","09128569777","hello",500);
        customerTest.getCart().addProduct(product);
        customerTest.getCart().addProduct(product2);
        PurchaseManager purchaseManagerTest = new PurchaseManager(customerTest);
        try {
        assertFalse(purchaseManagerTest.canPay(null));
        } catch (PurchaseManager.WrongDiscountIdException e) {
            e.printStackTrace();
        } catch (PurchaseManager.UsedDiscountIdException e) {
            e.printStackTrace();
        }
        customerTest.setBalance(5000);
        try {
            assertTrue(purchaseManagerTest.canPay(null));
        } catch (PurchaseManager.WrongDiscountIdException e) {
            e.printStackTrace();
        } catch (PurchaseManager.UsedDiscountIdException e) {
            e.printStackTrace();
        }
        boolean bool = true;
        try {
            purchaseManagerTest.canPay("chert");
            bool = false;
        } catch (PurchaseManager.WrongDiscountIdException e) {
            assertTrue(bool);
            e.printStackTrace();
        } catch (PurchaseManager.UsedDiscountIdException e) {
            e.printStackTrace();
        }
        ArrayList<String> customers = new ArrayList<>();
        customers.add(customerTest.getUsername());
        Discount discount = new Discount(LocalDate.parse("1990-01-01"),LocalDate.parse("2200-01-01"),
                20,400,1,customers);
        try {
            assertTrue(purchaseManagerTest.canPay(discount.getId()));
        } catch (PurchaseManager.WrongDiscountIdException e) {
            e.printStackTrace();
        } catch (PurchaseManager.UsedDiscountIdException e) {
            e.printStackTrace();
        }
        discount.useDiscount(customerTest);
        boolean bool2 = true;
        try {
            purchaseManagerTest.canPay(discount.getId());
            bool2 =  false;
        } catch (PurchaseManager.WrongDiscountIdException e) {
            e.printStackTrace();
        } catch (PurchaseManager.UsedDiscountIdException e) {
            e.printStackTrace();
            assertTrue(bool2);
        }

    }

    @Test
    public void pay() {
        ArrayList<String> info = new ArrayList<>();
        info.add("1");
        info.add("2");
        Customer customerTest = new Customer(UtilTestObject.CUSTOMER, "masih", "beigi", "masihbr@gamil.com",
                "09128569777", "hello", 5000);

        customerTest.getCart().addProduct(product);
//        customerTest.getCart().addProduct(product2);

        ArrayList<String> customers = new ArrayList<>();
        customers.add(customerTest.getUsername());
        Discount discount = new Discount(LocalDate.parse("1990-01-01"), LocalDate.parse("2200-01-01"),
                20, 400, 1, customers);
        PurchaseManager purchaseManagerTest = new PurchaseManager(customerTest);
        try {
            if (purchaseManagerTest.canPay(discount.getId())) {
                purchaseManagerTest.pay(info, discount.getId());
                assertEquals(customerTest.getBalance(), (double) 4200, 1);
            }
        } catch (PurchaseManager.WrongDiscountIdException e) {
            e.printStackTrace();
        } catch (PurchaseManager.UsedDiscountIdException e) {
            e.printStackTrace();
        }
        boolean bool = true;
        try {
            purchaseManagerTest.pay(info, "L");
            bool = false;
        } catch (PurchaseManager.WrongDiscountIdException e) {
            assertTrue(bool);
        }
    }
}