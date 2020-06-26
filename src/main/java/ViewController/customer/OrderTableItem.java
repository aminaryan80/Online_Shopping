package ViewController.customer;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class OrderTableItem {
    public SimpleIntegerProperty number;
    public SimpleStringProperty id;
    public SimpleStringProperty sellerName;
    public SimpleStringProperty status;
    public SimpleStringProperty phoneNumber;
    public SimpleStringProperty address;

    public OrderTableItem(int number, String id, String sellerName,  String status,  String phoneNumber,  String address) {
        this.number = new SimpleIntegerProperty(number);
        this.id = new SimpleStringProperty(id);
        this.sellerName = new SimpleStringProperty(sellerName);
        this.status = new SimpleStringProperty(status);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.address = new SimpleStringProperty(address);
    }

    public int getNumber() {
        return number.get();
    }

    public String getId() {
        return id.get();
    }

    public String getAddress() {
        return address.get();
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public String getSellerName() {
        return sellerName.get();
    }

    public String getStatus() {
        return status.get();
    }
}
