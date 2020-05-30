package Control.Principal;

import Control.Manager;
import Models.Account.Account;
import Models.Account.Principal;
import Models.Shop.Category.Sort;
import Models.Shop.Product.Product;
import View.ErrorProcessor;
import View.Principal.ManageUsersMenu;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ManageUsersManager extends Manager {

    private ArrayList<Account> users;
    private Sort currentSort;

    public ManageUsersManager(Account account) {
        super(account);
        this.users = Account.getAllAccounts();
        new ManageUsersMenu(this);
    }

    public ArrayList<String> showUsers() {
        return Account.showAccountsInShort();
    }

    public String viewUsername(String username) {
        if (Account.hasAccountWithUsername(username))
            return Account.getAccountByUsername(username).toString();
        return null;
    }

    public String showAvailableSorts() {
        return "name";
    }

    public boolean isEnteredSortFieldValid(String sort) {
        return sort.equals("name");
    }

    public ArrayList<String> sort(String sort, boolean isAscending) {
        users = Account.getAllAccounts();
        currentSort = new Sort(sort, isAscending);
        applySort();
        return userInShort();
    }

    private ArrayList<String> userInShort() {
        ArrayList<String> usersInShort = new ArrayList<>();
        for (Account user : users) {
            userInShort().add(user.getUsername() + "  " + user.getName() + "  " + user.getLastName());
        }
        return usersInShort;
    }

    public String currentSort() {
        return currentSort.toString();
    }

    public ArrayList<String> disableSort() {
        currentSort = null;
        users = Account.getAllAccounts();
        return userInShort();
    }

    private void applySort() {
        if (currentSort == null) {
            return;
        }
        Account[] usersForSort = users.toArray(new Account[0]);
        for (int i = 0; i < usersForSort.length; i++) {
            for (int j = i + 1; j < usersForSort.length; j++) {
                if (usersForSort[i].getName().compareTo(usersForSort[j].getName()) > 0) {
                    Account temp = usersForSort[i];
                    usersForSort[i] = usersForSort[j];
                    usersForSort[j] = temp;
                }
            }
        }
        if (!currentSort.isAscending()) {
            Collections.reverse(users);
        }
        users = (ArrayList<Account>) Arrays.asList(usersForSort);
    }

    public void deleteUsername(String username) {
        if (!username.equals(account.getUsername()))
            Account.deleteAccount(Account.getAccountByUsername(username));
        else ErrorProcessor.cantDeleteYourAccount();
    }

    public void createManagerProfile(ArrayList<String> inputs) {
        // username, password, email, phoneNumber, firstName, lastName
        new Principal(inputs.get(0), inputs.get(4), inputs.get(5), inputs.get(2), inputs.get(3), inputs.get(1));
    }
}
