package Models.Shop;

import Control.Manager;
import Control.Seller.OffsManager;
import Models.Account.Seller;

public class EditOffRequest extends Request {
    private String offId;
    private String field;
    private String newValue;

    public EditOffRequest(String id, Seller seller, Manager manager, String offId, String field, String newValue) {
        super(id, seller, manager);
        this.type = RequestType.EDIT_OFF;
        this.offId = offId;
        this.field = field;
        this.newValue = newValue;
    }

    public void accept() {
        ((OffsManager) manager).editOffAttribute(id, field, newValue);
    }

    @Override
    public String toString() {
        return "EditOffRequest{" +
                "offId='" + offId + '\'' +
                ", field='" + field + '\'' +
                ", newValue='" + newValue + '\'' +
                ", id='" + id + '\'' +
                ", seller=" + seller +
                ", type=" + type +
                '}';
    }
}
