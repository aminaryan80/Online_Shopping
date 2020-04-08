package View;

import Control.Manager;

import java.util.Scanner;
import java.util.regex.Matcher;

public class CommandProcessor {
    private Manager manager;
    private String command;
    private Scanner scanner = new Scanner(System.in);
    public void mainMenu() {

    }

    public void productsMenu() {

    }

    public void accountMenu(String type){

    }

    public void principalMenu() {
        String password = "Jesus";
        String newPassword = "Christ";
        manager.changePassword(password, newPassword);
    }

    public void sellerMenu(){

    }

    public void customerMenu(){

    }

    public void productPage() {

    }

    public void auctionMenu() {

    }

    private void userMenu() {
//        register(type,username);
//        login(username);
    }

    private void register(String type,String username) {
        manager.createAccount(firstname,lastname,phoneNumber,email,balance,type,username,company);
    }

    private void login(String username){
        String password;
        manager.login(username,password);
    }

    private Matcher getMatcher(String input,String regex){
    }
}
