package Models.Account;

import java.util.ArrayList;
import java.util.Arrays;

public class Principal extends Account {
    private final String[] changeableFields = {"password", "email", "firstname", "lastname", "phone number"};

    public Principal(String username, String firstName, String lastName, String email, String phoneNumber, String password) {
        super(username, firstName, lastName, email, phoneNumber, password, 0);
    }

    public ArrayList<String> getChangeableFields() {
        return new ArrayList<>(Arrays.asList(changeableFields));
    }

    @Override
    protected void loadReference() {

    }
}
