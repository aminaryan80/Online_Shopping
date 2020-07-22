package Client.Control.Seller;

import Client.Control.Manager;
import Client.ViewController.Controller;
import Models.Account.Account;
import Models.Account.Seller;
import Models.Shop.Off.Auction;
import Models.Shop.Request.AddOffRequest;
import Models.Shop.Request.EditOffRequest;

import java.time.LocalDate;
import java.util.List;

public class ViewOffsManager extends Manager {
    public ViewOffsManager(Account account) {
        super(account);
    }

    public ViewOffsManager(Account account, Addresses address, Manager manager) {
        super(account, address, manager);
        Controller controller = loadFxml(Addresses.EDIT_OFFS);
        update(controller);
    }

    public void update(Controller c) {
        /*EditOffController controller = (EditOffController) c;
        controller.setSeller((Seller) account);
        controller.init();*/
    }

    public Auction editOffAttribute(String sellerUsername, String id, String field, String newValue) {
        Seller seller = (Seller) Account.getAccountByUsername(sellerUsername);
        Auction auction = seller.getAuctionById(id);
        if (field.equals("BEGINNING_DATE")) {
            auction.setBeginningDate(LocalDate.parse(newValue));
        } else if (field.equals("ENDING_DATE")) {
            auction.setEndingDate(LocalDate.parse(newValue));
        } else if (field.equals("AMOUNT")) {
            auction.setDiscountAmount(Double.parseDouble(newValue));
        }
        auction.setStatus(Auction.AuctionStatus.UNDER_REVIEW_FOR_EDITING);
        new EditOffRequest(seller, auction);
        return auction;
    }

    public void addOff(String accountUsername, String beginningDate, String endingDate,
                       double discountAmount, List<String> productsIds) {
        Auction auction = new Auction(productsIds, LocalDate.parse(beginningDate), LocalDate.parse(endingDate), discountAmount);
        Seller seller = (Seller) Account.getAccountByUsername(accountUsername);
        seller.addAuction(auction);
        new AddOffRequest(seller, auction);
    }
}
