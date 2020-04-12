package Control;

import Models.Account.Account;
import Models.Shop.Cart;

public class Manager {
    protected Account account;
    protected Cart cart;

    public Manager(Account account) {
        this.account = account;
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
