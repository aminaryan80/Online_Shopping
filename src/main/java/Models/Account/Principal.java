package Models.Account;

public class Principal extends Account {

    public Principal(String username, String firstName, String lastName, String email, String phoneNumber, String password) {
        super(username, firstName, lastName, email, phoneNumber, password, 0);
    }

    @Override
    protected void loadReference() {

    }
}
