package Models.Account;

import Models.Shop.Discount;
import Models.Shop.Log;

import java.util.List;

public class Account {
    protected String username;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String phoneNumber;
    protected String password;
    protected List<Log> logs;
    protected double balance;
    protected List<Discount> discounts;

    public boolean canChangePassword(String currentPassword) {
        return currentPassword.equals(password);
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
