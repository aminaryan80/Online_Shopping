package Control;

import Models.Account.Account;
import Models.Shop.Cart;
import Models.Shop.Category;
import View.Menu;

import java.util.Scanner;

public abstract class Manager {
    protected Scanner scanner;
    protected Account account;
    protected Category mainCategory;
    protected Cart cart;
    private Menu menu;

    public Manager(Account account,Menu menu) {
        this.account = account;
        this.menu = menu;
    }

    public Menu getMenu(){
        return menu;
    }

    public boolean userExistsWithUsername(String username) {
       return true;
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
