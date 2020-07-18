package Client.ViewController.customer.cart;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CartTableItem {
    public SimpleIntegerProperty number;
    public SimpleStringProperty id;
    public SimpleStringProperty name;
    public SimpleStringProperty description;
    public SimpleIntegerProperty quantity;
    public SimpleDoubleProperty price;

    public CartTableItem(int number,String  id, String name, String description, int quantity, double price) {
        this.number =new SimpleIntegerProperty(number);
        this.id =new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.quantity =new SimpleIntegerProperty(quantity);
        this.price =new SimpleDoubleProperty(price);
    }

    public String getName() {
        return name.get();
    }

    public String getId() {
        return id.get();
    }

    public int getNumber() {
        return number.get();
    }

    public double getPrice() {
        return price.get();
    }

    public String getDescription() {
        return description.get();
    }
}
