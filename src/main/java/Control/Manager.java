package Control;

import Models.Account.Account;
import Models.Shop.Cart;
import Models.Shop.Category;

import java.util.Scanner;

public class Manager {
    protected Scanner scanner;
    protected Account account;
    protected Category mainCategory;
    protected Cart cart;

    public Manager(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public boolean userExistsWithUsername(String username) {

    }

    public void changeFirstName(String newFirstName) {

    }

    public void changeLastName(String newLastName) {

    }

    public void login(String username,String password) {

    }

    public boolean checkEmail(String email){
        return true;
    }

    public boolean checkPhoneNumber(String phoneNumber){
        return true;
    }

    public void createAccount(String type, String username, String password, String email, String firstName,
                              String lastName, String phoneNumber, double balance) {

    }
}
