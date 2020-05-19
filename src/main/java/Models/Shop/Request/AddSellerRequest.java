package Models.Shop.Request;

import Models.Account.Account;
import Models.Account.Seller;
import Models.Shop.Off.Auction;

import java.io.IOException;

public class AddSellerRequest extends Request {

    String userName;
    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    String password;
    double balance;
    String companyName;

    public AddSellerRequest(String userName, String firstName, String lastName, String email, String phoneNumber,
                            String password, double balance, String companyName) {
        super();
        this.type = RequestType.ADD_SELLER_REQUEST;
    }

    @Override
    protected void loadReference() {

    }

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
                "\nseller=" + seller +
                "\ntype=" + type;
    }
}
