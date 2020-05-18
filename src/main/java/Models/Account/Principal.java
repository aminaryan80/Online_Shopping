package Models.Account;

import java.util.ArrayList;
import java.util.Arrays;

public class Principal extends Account {
    private final String[] changeableFields = {"password", "email", "firstName", "lastName", "phoneNumber"};

    public Principal(String username, String firstName, String lastName, String email, String phoneNumber, String password) {
        super(username, firstName, lastName, email, phoneNumber, password, 0);
    }

    public ArrayList<String> getChangeableFields() {
        return new ArrayList<>(Arrays.asList(changeableFields));
    }

    public static boolean isPrincipalExists() {
        for (Account account : allAccounts) {
            if (account instanceof Principal)
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return username + " : \n" +
                "firstName = " + firstName + '\n' +
                "lastName = " + lastName + '\n' +
                "email = " + email + '\n' +
                "phoneNumber = " + phoneNumber + '\n' +
                "password = " + password;
    }
}
