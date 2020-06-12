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

public class ViewCartManagerTest {
    Customer customer = new Customer(UtilTestObject.CUSTOMER,"masih","beigi","masihbr@gamil.com","09128569777","hello",500);
    ViewCartManager viewCartManager = new ViewCartManager(customer);
    Seller seller = new Seller(UtilTestObject.SELLER,"masih","beigi","masihbr@gamil.com","09128569777","hello",500,"apple");
    HashMap<String,Integer> features = new HashMap<>();
    Product product = new Product("macbook",
            "apple",
            10000,seller,
            true,
            new Category("1",null,features,new ArrayList<String>()),
            "des",
            null);
    Product product2 = new Product("macbook2",
            "apple",
            1020,seller,
            true,
            new Category("1",null,features,new ArrayList<String>()),
            "des",
            null);

    @Test
    public void showProducts() {
        System.out.println(viewCartManager.showProducts());
        assertTrue(viewCartManager.showProducts().equals(""));
        customer.getCart().addProduct(product);
        customer.getCart().addProduct(product2);
        System.out.println(viewCartManager.showProducts());
        assertFalse(viewCartManager.showProducts().equals(""));
    }

    @Test
    public void productQuantity() {
        customer.getCart().addProduct(product);
        try {
            viewCartManager.productQuantity("chert",true);
        } catch (ViewCartManager.ProductDoNotExistInCartException e) {
            e.printStackTrace();
        }
        try {
            viewCartManager.productQuantity(product.getId(),true);
            assertEquals(customer.getCart().getProductNumberInCartById(product.getId()),"2");
        } catch (ViewCartManager.ProductDoNotExistInCartException e) {
            e.printStackTrace();
        }
        try {
            viewCartManager.productQuantity(product.getId(),true);
            assertEquals(customer.getCart().getProductNumberInCartById(product.getId()),"3");
            viewCartManager.productQuantity(product.getId(),false);
            viewCartManager.productQuantity(product.getId(),false);
            assertEquals(customer.getCart().getProductNumberInCartById(product.getId()),"1");
            viewCartManager.productQuantity(product.getId(),false);
            assertEquals(customer.getCart().getProductNumberInCartById(product.getId()),null);
        }  catch (ViewCartManager.ProductDoNotExistInCartException e) {
            e.printStackTrace();
        }
        try {
            viewCartManager.productQuantity(product2.getId(),true);
            assertEquals(customer.getCart().getProductNumberInCartById(product.getId()),"2");
        } catch (ViewCartManager.ProductDoNotExistInCartException e) {
            assertEquals(e.getMessage(),"Product does not exist in cart");
            e.printStackTrace();
        }

    }

    @Test
    public void getTotalPrice() {
        assertEquals(viewCartManager.getTotalPrice(null),(double) 0,1);
        customer.getCart().addProduct(product);
        ArrayList<String> customers = new ArrayList<>();
        customers.add(customer.getUsername());
        Discount discount = new Discount(LocalDate.parse("1990-01-01"),LocalDate.parse("2200-01-01"),
                20,400,1,customers);
        assertEquals(viewCartManager.getTotalPrice(discount),(double) 9600,1);
        customer.getCart().removeProduct(product);
        assertEquals(viewCartManager.getTotalPrice(null),(double) 0,1);
    }

    @Test
    public void doesProductExistInCart() {
        assertFalse(viewCartManager.doesProductExistInCart(product));
        customer.getCart().addProduct(product);
        assertTrue(viewCartManager.doesProductExistInCart(product));
        customer.getCart().removeProduct(product);
        assertFalse(viewCartManager.doesProductExistInCart(product));
    }

    @Test
    public void showAvailableSorts() {
    }

    @Test
    public void sort() {
    }

    @Test
    public void isEnteredSortFieldValid() {
        assertFalse(viewCartManager.isEnteredSortFieldValid("chert"));
        assertTrue(viewCartManager.isEnteredSortFieldValid("price"));
        assertTrue(viewCartManager.isEnteredSortFieldValid("name"));
        assertTrue(viewCartManager.isEnteredSortFieldValid("rating"));
    }

    @Test
    public void currentSort() {
    }

    @Test
    public void disableSort() {
    }

    @Test
    public void isCartEmpty() {
        assertTrue(viewCartManager.isCartEmpty());
        customer.getCart().addProduct(product);
        assertFalse(viewCartManager.isCartEmpty());
    }
}