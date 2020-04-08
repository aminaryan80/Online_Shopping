import Models.Account.Account;

public class Manager {

    private Account account;

    public void changeFirstName(String newFirstName) {

    }

    public void changeLastName(String newLastName) {

    }

    public void changePassword(String password, String newPassword) {
        if (account.canChangePassword(password)) {
            account.setPassword(newPassword);
        } else System.out.println("Can't change password.");
    }
}
