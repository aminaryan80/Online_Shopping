package Models.Shop.Log;

import Server.Control.Identity;
import Models.Shop.Product.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Log {
    protected String id;
    protected LocalDateTime date;
    protected String stringDate;
    protected double money; //Selling log : received by seller | Buying log : paid by customer
    protected double amount; //Selling log : of auction | Buying log :  of discount
    protected String address;
    protected String phoneNumber;
    protected List<Product> products; //Selling log : sold by seller | Buying log :  bought by buyer
    protected HashMap<String, Integer> productIdToNumberMap = new HashMap<>();
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
        stringDate = "Date: " + date.getYear() + "/" + date.getMonth() + "/" + date.getDayOfMonth();
    }

    public void addProductsNumbers(String productId,int number) {
        productIdToNumberMap.put(productId,number);
    }

    public double getMoney() {
        return money;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public abstract String toString();

    public String getId() {
        return id;
    }

    public void setStatus() {
        this.status = Status.RECEIVED;
    }

    public String getName() {
        return name;
    }

    public enum Status {
        TO_BE_SEND, ON_THE_WAY, RECEIVED
    }

    public Status getStatus() {
        return status;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public double getAmount() {
        return amount;
    }

    public HashMap<String, Integer> getProductIdToNumberMap() {
        return productIdToNumberMap;
    }
}
