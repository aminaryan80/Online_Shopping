package Models.Shop;

import Control.Manager;
import Control.Seller.OffsManager;
import Models.Account.Seller;

import java.util.ArrayList;
import java.util.Date;

public class AddOffRequest extends Request{
    private String offId;
    private String beginningDate;
    private String endingDate;
    private double discountAmount;
    private ArrayList<String> products;

    public AddOffRequest(String id, Seller seller, Manager manager, String offId,
                         String beginningDate, String endingDate, double discountAmount, ArrayList<String> products) {
        super(id, seller, manager);
        this.type = RequestType.ADD_OFF;
        this.offId = offId;
        this.beginningDate = beginningDate;
        this.endingDate = endingDate;
        this.discountAmount = discountAmount;
        this.products = products;
    }

    public void accept() {
        ((OffsManager) manager).addOff(id, beginningDate, endingDate, discountAmount, products);
    }

    @Override
    public String toString() {
        return "AddOffRequest{" +
                "offId='" + offId + '\'' +
                ", beginningDate='" + beginningDate + '\'' +
                ", endingDate='" + endingDate + '\'' +
                ", discountAmount=" + discountAmount +
                ", products=" + products +
                ", id='" + id + '\'' +
                ", seller=" + seller +
                ", type=" + type +
                '}';
    }
}
