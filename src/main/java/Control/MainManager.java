package Control;

import Models.Account.Account;

public class MainManager extends Manager {

    public MainManager(Account account) {
        super(account);
    }

    public String viewPersonalInfo() {
        return account.toString();
    }

    public boolean isDiscountCodeValid(String id) {

    }
}
