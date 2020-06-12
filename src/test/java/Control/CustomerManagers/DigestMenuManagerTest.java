package Control.CustomerManagers;

import Control.UtilTestObject;
import Models.Account.Customer;
import Models.Account.Seller;
import Models.Shop.Category.Category;
import Models.Shop.Product.Product;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DigestMenuManagerTest {
    Customer customer = new Customer(UtilTestObject.CUSTOMER,"masih","beigi","masihbr@gamil.com","09128569777","hello",500);
    Product product = new Product("macbook","apple",
            1000,new Seller(UtilTestObject.SELLER,"masih","beigi","masihbr@gamil.com","09128569777","hello",500,"apple"),true,
            new Category("1",null,null,new ArrayList<String>()),"",null);
    DigestMenuManager digestMenuManager = new DigestMenuManager(customer,product);
    @Test
    public void addToCart() {
        digestMenuManager.addToCart();
        assertTrue(digestMenuManager.getCart().getProducts().contains(product));
    }
}