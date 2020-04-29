package Control;

import Models.Account.Account;
import View.AuctionPage;

import java.util.List;

public class AuctionPageManager extends Manager {

    public AuctionPageManager(Account account) {
        super(account);
        this.menu = new AuctionPage(this);
    }

    // offs
    public List<String> showOffs() {
        return null;
    }

    // show product [productId]
    public void showProduct(String id) {

    }
}
