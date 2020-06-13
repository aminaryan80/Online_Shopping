package Models.Shop.Request;

import Models.Account.Seller;

import java.io.IOException;

public class AddSellerRequest extends Request {
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private double balance;
    private String companyName;

    public AddSellerRequest(String userName, String firstName, String lastName, String email, String phoneNumber,
                            String password, double balance, String companyName) {
        super();
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.balance = balance;
        this.companyName = companyName;
        this.type = RequestType.ADD_SELLER_REQUEST;
    }

//    @Override
//    protected void loadReference() {
//
//    }

    @Override
    public void accept() throws IOException {
        new Seller(userName, firstName, lastName, email, phoneNumber, password, balance, companyName);
        deleteRequest(this, "add seller requests");
    }

    @Override
    public void decline() throws IOException {
        deleteRequest(this, "add seller requests");
    }

    @Override
    public String toString() {
        return "AddSellerRequest : " +
                "\nid='" + id + '\'' +
                "\nseller=" + userName +
                "\ntype=" + type;
    }
}
