package Control.Seller;

import Control.Manager;
import Models.Account.Account;
import Models.Account.Seller;
import Models.Shop.Off.Auction;
import Models.Shop.Product.Product;
import View.Seller.OffsMenu;
import ViewController.Controller;
import ViewController.userPanel.Seller.EditOffController;

import java.time.LocalDate;
import java.util.ArrayList;

public class OffsManager extends Manager {
    public OffsManager(Account account) {
        super(account);
        new OffsMenu(this);
    }

    public OffsManager(Account account, Addresses address, Manager manager) {
        super(account, address, manager);
        //new ManageUsersMenu(this);
        Controller controller = loadFxml(Addresses.EDIT_OFFS);
        ((EditOffController) controller).setSeller((Seller) account);
        ((EditOffController) controller).init();
    }

    public String viewOffById(String id) {
        return ((Seller) account).getAuctionById(id).toString();
    }

    public boolean isOffFieldValid(String field) {
        return field.equals("beginningDate") || field.equals("endingDate") || field.equals("status") ||
                field.equals("amount");
    }

    public boolean isEnteredIdValid(String id) {
        return ((Seller) account).hasAuctionWithId(id);
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
                       double discountAmount, ArrayList<String> productsIds) {
        Auction auction = new Auction( productsIds, LocalDate.parse(beginningDate), LocalDate.parse(endingDate), discountAmount);
        ((Seller) account).addAuction(auction);
        return auction;
    }

    public boolean hasProductWithId(String id) {
        return Product.hasProductWithId(id, account.getUsername());
    }

    public boolean hasAuction(String id) {
        return Product.getProductById(id).hasAuction();
    }

    private ArrayList<Product> getProductsListByNames(ArrayList<String> productsNames) {
        ArrayList<Product> products = new ArrayList<Product>();
        for (String productsName : productsNames) {
            ArrayList<Product> productsWithTheSameName = Product.getProductsByName(productsName);
            for (Product product : productsWithTheSameName) {
                if (product.getSeller().equals(account)) {
                    products.add(product);
                }
            }
        }
        return products;
    }
}
