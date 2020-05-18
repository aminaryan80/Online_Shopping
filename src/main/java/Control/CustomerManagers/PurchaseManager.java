package Control.CustomerManagers;

import Control.Identity;
import Models.Account.Account;
import Models.Account.Seller;
import Models.Shop.Log.BuyingLog;
import Models.Shop.Log.Log;
import Models.Shop.Log.SellingLog;
import Models.Shop.Off.Discount;
import Models.Shop.Product.Product;
import View.CustomerMenus.purchase.PurchaseMenu;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

public class PurchaseManager extends CustomerManager {
    public PurchaseManager(Account account) {
        super(account);
        this.menu = new PurchaseMenu(this);
    }

    public boolean canPay(String discountId) throws WrongDiscountIdException {
        Discount discount = Discount.getDiscountById(discountId);
        if (discount == null && discountId != null) {
            throw new WrongDiscountIdException();
        } else return customer.getCart().getTotalPrice(discount) <= customer.getBalance();
    }

    // purchase
    public void pay(ArrayList<String> receiverInformation, String discountId) throws WrongDiscountIdException {
        Discount discount = Discount.getDiscountById(discountId);
        if (discount == null && discountId != null) {
            throw new WrongDiscountIdException();
        } else {
            double paymentAmount = customer.getCart().getTotalPrice(discount);
            ArrayList<Product> boughtProducts = customer.getCart().getProducts(); //TODO number of products not handled.
            HashSet<Seller> sellers = getSellers(boughtProducts);
            for (Seller seller : sellers) {
                ArrayList<Product> productsBoughtFromThisSeller = new ArrayList<>();
                for (Product product : boughtProducts) {
                    if (product.getSeller().equals(seller)) productsBoughtFromThisSeller.add(product);
                }
                seller.addLog(
                        getSellingLog(receiverInformation, productsBoughtFromThisSeller)
                );
                customer.addLog(
                        getBuyingLog(receiverInformation, discount, seller, productsBoughtFromThisSeller)
                );
            }
            customer.payMoney(paymentAmount);
            sellersGetPaid(boughtProducts);
        }
    }

    private BuyingLog getBuyingLog(ArrayList<String> receiverInformation, Discount discount, Seller seller, ArrayList<Product> productsBoughtFromThisSeller) {
        return new BuyingLog(
                LocalDateTime.now(),
                getMoneyPaidByCustomer(productsBoughtFromThisSeller,discount),
                getAmountOfDiscountCodeApplied(productsBoughtFromThisSeller, discount),
                productsBoughtFromThisSeller,
                seller.getName(),
                receiverInformation.get(0),
                receiverInformation.get(1),
                Log.Status.TO_BE_SEND
        );
    }

    private SellingLog getSellingLog(ArrayList<String> receiverInformation, ArrayList<Product> productsBoughtFromThisSeller) {
        return new SellingLog(
                LocalDateTime.now(),
                getMoneyReceivedBySeller(productsBoughtFromThisSeller),
                getAmountOfAuctionApplied(productsBoughtFromThisSeller),
                productsBoughtFromThisSeller,
                customer.getName(),
                receiverInformation.get(0),
                receiverInformation.get(1),
                Log.Status.TO_BE_SEND
        );
    }

    private double getAmountOfDiscountCodeApplied(ArrayList<Product> productsBoughtFromThisSeller, Discount discount) {
        return getMoneyReceivedBySeller(productsBoughtFromThisSeller) - getMoneyPaidByCustomer(productsBoughtFromThisSeller,discount);
    }

    private double getMoneyPaidByCustomer(ArrayList<Product> productsBoughtFromThisSeller, Discount discount) {
        if (discount == null) return getMoneyReceivedBySeller(productsBoughtFromThisSeller);
        else {
            double moneyPaidToThisSeller =  getMoneyReceivedBySeller(productsBoughtFromThisSeller);
            double moneyPaidToAllSellers = 0;
            for (Product product : customer.getCart().getProducts()) {
                moneyPaidToAllSellers += product.getAuctionedPrice();
            }
            return (moneyPaidToThisSeller/moneyPaidToAllSellers) * customer.getCart().getTotalPrice(discount);
        }
    }

    private double getAmountOfAuctionApplied(ArrayList<Product> productsBoughtFromThisSeller) {
        double amountOfAuctionApplied = 0;
        for (Product product : productsBoughtFromThisSeller) {
            amountOfAuctionApplied += product.getAuction().getDiscountAmount();
        }
        return amountOfAuctionApplied;
    }

    private HashSet<Seller> getSellers(ArrayList<Product> boughtProducts) {
        HashSet<Seller> sellers = new HashSet<>();
        for (Product product : boughtProducts) {
            sellers.add(product.getSeller());
        }
        return sellers;
    }

    private double getMoneyReceivedBySeller(ArrayList<Product> products) {
        double moneyReceivedBySeller = 0;
        for (Product product : products) {
            moneyReceivedBySeller += product.getAuctionedPrice();
        }
        return moneyReceivedBySeller;
    }

    private void sellersGetPaid(ArrayList<Product> boughtProducts) {
        for (Product product : boughtProducts) {
            product.getSeller().receiveProductMoney(product);
        }
    }

    public static class WrongDiscountIdException extends Exception {
        public WrongDiscountIdException() {
        }
    }
}
