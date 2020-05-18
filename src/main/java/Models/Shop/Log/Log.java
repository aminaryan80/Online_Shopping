package Models.Shop.Log;

import Control.Identity;
import Models.Shop.Product.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Log {
    //TODO change name to receipt
    protected String id;
    protected LocalDateTime date;
    protected double money; //Selling log : received by seller | Buying log : paid by customer
    protected double amount; //Selling log : of auction | Buying log :  of discount
    protected String address;
    protected String phoneNumber;
    protected List<Product> products =  new ArrayList<>(); //Selling log : sold by seller | Buying log :  bought by buyer
    protected String name; //Selling log : buyer's name |  Buying log : seller's name
    protected Status status;

    public Log(LocalDateTime date, double money,double amount,ArrayList<Product> products,
               String name, String address, String phoneNumber,Status status) {
        this.id = Identity.getId();
        this.date = date;
        this.money = money;
        this.amount = amount;
        this.products = products;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    public abstract String viewLogInShort();

    @Override
    public abstract String toString();

    public boolean hasProductInLog(Product product) {
        for (Product p : products) {
            if (p == product)
                return true;
        }
        return false;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public enum Status {
        TO_BE_SEND, ON_THE_WAY, RECEIVED
    }
}
