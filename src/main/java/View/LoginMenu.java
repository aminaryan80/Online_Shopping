package View;

public class LoginMenu extends CommandProcessor {

    private void register(String type, String username) {
        manager.createAccount(firstname, lastname, phoneNumber, email, balance, type, username, company);
    }

    private void login(String username) {
        String password;
        manager.login(username, password);
    }
}
