package Control.CustomerManagers;

import Control.UtilTestObject;
import Models.Account.Customer;
import Models.Account.Seller;
import Models.Shop.Category.Category;
import Models.Shop.Off.Auction;
import Models.Shop.Product.Comment;
import Models.Shop.Product.Comment.*;
import Models.Shop.Product.Product;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class ProductPageManagerTest {

    Customer customer = new Customer(UtilTestObject.CUSTOMER,"masih","beigi","masihbr@gamil.com","09128569777","hello",500);
    HashMap<String,Integer> features = new HashMap<>();
    Seller seller = new Seller(UtilTestObject.SELLER,"masih","beigi","masihbr@gamil.com","09128569777","hello",500,"apple");
    Product product = new Product("macbook",
            "apple",
            1000,seller,
            true,
            new Category("1","2",features,null),
            "des",
            null);
    Product product2 = new Product("macbook2","apple",
            1020,seller,true,
            new Category("1","2",features,null),"des",null);

    ProductPageManager productPageManager = new ProductPageManager(customer,product);

    @Test
    public void setCustomer() {
        Customer expectedCustomer = new Customer(UtilTestObject.CUSTOMER+"expected","masih","beigi","masihbr@gamil.com","09128569777","hello",500);
        productPageManager.setCustomer(expectedCustomer);
        assertEquals(expectedCustomer.getUsername(), productPageManager.getAccount().getUsername());
    }

    @Test
    public void digest() {
        String expectedResult = "\nDescription: " + "des" +
                "\nPrice: " + (double)1000 +
                "\nAuction amount: " + (double)10;
        ArrayList<String> productIds = new ArrayList<>();
        productIds.add(product.getId());
        product.setAuction(new Auction(productIds, LocalDate.parse("1998-01-01"),LocalDate.parse("2020-01-01"),10));
        assertEquals(productPageManager.digest(),expectedResult);
    }

    @Test
    public void attributes() {
        HashMap<String,Integer> features = new HashMap<>();
        features.put("a",1);
        Product product = new Product("macbook","apple",
                1000,seller,true,
                new Category("1","2",features,null),"des",null);
        ProductPageManager productPageManager2 = new ProductPageManager(customer,product);
        String expectedResult = "Name: " + "macbook" +
                "\nId: " + product.getId() +
                "\nCompany name: " + "apple" +
                "\nSeller: " + Seller.getAccountByUsername(UtilTestObject.SELLER).getName() +
                "\nDescription: " + "des" +
                "\n" + Category.getCategoryByName("1").getFeaturesNames().toString();
        assertEquals(expectedResult,productPageManager2.attributes());
    }

    @Test
    public void compare() {
        String expectedResult = product.getAttributes()+"\nCompared to\n"+product2.getAttributes();
        assertEquals(productPageManager.compare(product2),expectedResult);
    }

    @Test
    public void comments() {
        product.addRate(customer,4);
        product.addComment(new Comment(customer,product,"hello",CommentStatus.CONFIRMED,false));
        product.addComment(new Comment(customer,product,"heu",CommentStatus.CONFIRMED,false));
        String expectedResult ="[Rate:4.0\n" +
                ", comment 1:\n" +
                "hello\n" +
                ", comment 2:\n" +
                "heu\n" +
                "]";
        assertEquals(productPageManager.comments().toString(),expectedResult);
    }

    @Test
    public void addComment() {
        productPageManager.addComment("this is garbage","it won't work");
        assertEquals(product.getComments().get(0),"comment "+1+":"+"\n"+"this is garbage"+":\n"+"\t"+"it won't work"+"\n");
    }

    @Test
    public void getProduct() {
        assertEquals(productPageManager.getProduct().getId(),product.getId());
    }
}