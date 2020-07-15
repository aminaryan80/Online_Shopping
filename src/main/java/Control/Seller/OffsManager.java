package Control.Seller;

import Control.Manager;
import Models.Account.Account;
import Models.Account.Seller;
import Models.Shop.Off.Auction;
import ViewController.Controller;
import ViewController.userPanel.Seller.EditOffController;

import java.time.LocalDate;
import java.util.List;

public class OffsManager extends Manager {
    public OffsManager(Account account, Addresses address, Manager manager) {
        super(account, address, manager);
        Controller controller = loadFxml(Addresses.EDIT_OFFS);
        update(controller);
    }

    public void update(Controller c) {
        EditOffController controller = (EditOffController) c;
        controller.setSeller((Seller) account);
        controller.init();
    }

    public Auction editOffAttribute(String id, String field, String newValue) {
        Auction auction = ((Seller) account).getAuctionById(id);
        if (field.equals("beginningDate")) {
            auction.setBeginningDate(LocalDate.parse(newValue));
        } else if (field.equals("endingDate")) {
            auction.setEndingDate(LocalDate.parse(newValue));
        } else if (field.equals("amount")) {
            auction.setDiscountAmount(Double.parseDouble(newValue));
        }
        auction.setStatus(Auction.AuctionStatus.UNDER_REVIEW_FOR_EDITING);
        return auction;
    }

    public Auction addOff(String beginningDate, String endingDate,
                          double discountAmount, List<String> productsIds) {
        Auction auction = new Auction(productsIds, LocalDate.parse(beginningDate), LocalDate.parse(endingDate), discountAmount);
        ((Seller) account).addAuction(auction);
        return auction;
    }
}
