package Control;

import Models.Account.Account;
import Models.Shop.Category;
import View.Menu;

import java.util.Scanner;

public abstract class Manager {
    protected Scanner scanner;
    protected Account account;
    protected static Category mainCategory = new Category("mainCategory", null, null, null);
    protected Menu menu;

    public Manager(Account account) {
        this.account = account;
    }

    public Menu getMenu() {
        return menu;
    }

    public boolean userExistsWithUsername(String username) {
       return true;
    }

    public boolean checkEmail(String email){
        return true;
    }

    public boolean checkPhoneNumber(String phoneNumber) {
        return true;
    }

    public Account getAccount() {
        return account;
    }

    public Category getMainCategory() {
        return mainCategory;
    }
}
