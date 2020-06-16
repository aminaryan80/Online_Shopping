package Control;

import Models.Account.Account;

public class EditPasswordManager extends Manager {
    public EditPasswordManager(Account account) {
        super(account);
        loadFxml(Addresses.EDIT_PASSWORD, true);
    }

    public void editPassword(String currentPassword, String newPassword) {
        if (currentPassword.equals(account.getPassword())) {
            account.setPassword(newPassword);
            success("Password successfully changed.");
        } else error("Invalid input");
    }
}
