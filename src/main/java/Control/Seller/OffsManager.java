package Control.Seller;

import Models.Account.Account;
import Models.Account.Seller;
import Models.Shop.Auction;
import Models.Shop.Product;
import java.util.ArrayList;
import java.util.Date;

public class OffsManager extends SellerManager {
    public OffsManager(Account account) {
        super(account);
    }

    public String viewOffById(String id) {
        return ((Seller) account).getAuctionById(id).toString();
    }

    public boolean isOffFieldValid(String field) {
        if (field.equals("beginningDate") || field.equals("endingDate") || field.equals("status") ||
                field.equals("amount")) {
            return true;
        }
        return false;
    }

    public boolean isEnteredIdValid(String id) {
        return ((Seller) account).hasAuctionWithId(id);
    }

    public Auction editOffAttribute(String id, String field, String newValue) {
        Auction auction = ((Seller) account).getAuctionById(id);
        if (field.equals("beginningDate")) {
            auction.setBeginningDate(new Date(newValue));
        } else if (field.equals("endingDate")) {
            auction.setEndingDate(new Date(newValue));
        } else if (field.equals("amount")) {
            auction.setDiscountAmount(Double.parseDouble(newValue));
        }
        auction.setStatus(Auction.AuctionStatus.UNDER_REVIEW_FOR_EDITING);
        return auction;
    }

    public Auction addOff(String id, String beginningDate, String endingDate,
                       double discountAmount, ArrayList<String> productsNames) {
        Auction auction = new Auction(id, getProductsListByNames(productsNames), new Date(beginningDate), new Date(endingDate), discountAmount);
        ((Seller) account).addAuction(auction);
        return auction;
    }

    private ArrayList<Product> getProductsListByNames(ArrayList<String> productsNames) {
        ArrayList<Product> products = new ArrayList<Product>();
        for (String productsName : productsNames) {
            ArrayList<Product> productsWithTheSameName = Product.getProductsByName(productsName);
            for (Product product : productsWithTheSameName) {
                if (product.getSeller().equals((Seller) account)) {
                    products.add(product);
                }
            }
        }
        return products;
    }
}
