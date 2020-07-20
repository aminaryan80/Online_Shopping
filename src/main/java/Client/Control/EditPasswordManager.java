package Client.Control;

import Models.Account.Account;

public class EditPasswordManager extends Manager {
    public EditPasswordManager(Account account) {
        super(account);
        //loadFxml(Addresses.EDIT_PASSWORD, true);
    }

    public int editPassword(String currentPassword, String newPassword) {
        if (currentPassword.equals(account.getPassword())) {
            account.setPassword(newPassword);
            return 0;
        }
        return 1;
    }
}
