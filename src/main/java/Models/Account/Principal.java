package Models.Account;

import Models.Shop.Discount;

import java.util.ArrayList;
import java.util.List;

public class Principal extends Account {
    private ArrayList<Discount> discounts;

    public Principal(String username, String firstName, String lastName, String email, String phoneNumber, String password) {
        super(username, firstName, lastName, email, phoneNumber, password, 0);
        discounts = new ArrayList<>();
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }
}
