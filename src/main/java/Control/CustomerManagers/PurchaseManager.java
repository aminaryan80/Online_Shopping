package Control.CustomerManagers;

import Control.Manager;
import Control.UtilTestObject;
import Models.Account.Account;
import Models.Account.Customer;
import Models.Account.Seller;
import Models.Shop.Log.BuyingLog;
import Models.Shop.Log.Log;
import Models.Shop.Log.SellingLog;
import Models.Shop.Off.Auction;
import Models.Shop.Off.Discount;
import Models.Shop.Product.Product;
import View.CustomerMenus.purchase.PurchaseMenu;
import ViewController.Controller;
import ViewController.customer.cart.PurchasePageController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class PurchaseManager extends Manager {
    private Customer customer = (Customer) account;
    private PurchasePageController purchasePageController;

    public PurchaseManager(Account account) {
        super(account);
        if (!account.getUsername().equals(UtilTestObject.CUSTOMER))
            this.menu = new PurchaseMenu(this);
    }

    public PurchaseManager(Account account, Addresses viewCart, ViewCartManager viewCartManager) {
        super(account,viewCart,viewCartManager);
        Controller controller = loadFxml(Addresses.PURCHASE_PAGE,false,this);
        update(controller);
    }

    @Override
    public void update(Controller controller) {
        purchasePageController =(PurchasePageController) controller;
        purchasePageController.init();
    }

    public boolean canPay(String discountId) throws WrongDiscountIdException, UsedDiscountIdException {
        if(!discountId.matches("\\S{8}"))
            discountId=null;
        if (discountId == null) {
            return customer.getCart().getTotalPrice(null) <= customer.getBalance();
        }
        Discount discount = getDiscountById(discountId);
        if (DiscountWithThisIdDoesNotExist(discountId, discount)) {
            throw new WrongDiscountIdException("Wrong Discount Id has been entered");
        } else {
            if (doesDiscountBelongToCustomer(discount)) {
                if (!canUseDiscount(discount)) {
                    throw new UsedDiscountIdException("you can not use this discount anymore");
                } else return customer.getCart().getTotalPrice(discount) <= customer.getBalance();
            } else throw new WrongDiscountIdException("Discount Id entered DOES NOT BELONG TO YOU");
        }
    }

    public boolean isDiscountActive(String discountId){
        Discount discount = Discount.getDiscountById(discountId);
        return discount.isActive(LocalDate.now());
    }

    public boolean doesDiscountBelongToCustomer(Discount discount) {
        return discount.belongsToCustomer(customer);
    }

    public boolean doesDiscountBelongToCustomer(String discountId) {
        Discount discount = Discount.getDiscountById(discountId);
        return discount.belongsToCustomer(customer);
    }

    public boolean canUseDiscount(Discount discount) {
        return discount.canUseDiscount(customer);
    }

    public boolean canUseDiscount(String discountId) {
        Discount discount = Discount.getDiscountById(discountId);
        return discount.canUseDiscount(customer);
    }


    public boolean hasDiscountCode(String discountId) {
        if(discountId.matches("(?i)no")) {
            return false;
        } else return true;
    }

    // purchase
    public void pay(ArrayList<String> receiverInformation, String discountId) throws WrongDiscountIdException {
        if(!discountId.matches("\\S{8}"))
            discountId=null;
        Discount discount = getDiscountById(discountId);
        if (DiscountWithThisIdDoesNotExist(discountId, discount)) {
            throw new WrongDiscountIdException("Wrong Discount Id has been entered");
        } else {
            if (hasDiscountCode(discount)) {
                discount.useDiscount(customer); //if customer could'nt use it, he would'nt get here
            }
            double paymentAmount = customer.getCart().getTotalPrice(discount);
            ArrayList<Product> boughtProducts = customer.getCart().getProducts(); //TODO number of products not handled.
            addBuyers(boughtProducts);
            addLogs(receiverInformation, discount, boughtProducts);
            customer.payMoney(paymentAmount);
            sellersGetPaid(boughtProducts);
            customer.getCart().empty();
        }
    }

    private void addLogs(ArrayList<String> receiverInformation, Discount discount, ArrayList<Product> boughtProducts) {
        HashSet<Seller> sellers = getSellers(boughtProducts);
        for (Seller seller : sellers) { // each log is formed based on a seller
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
    }

    private void addBuyers(ArrayList<Product> boughtProducts) {
        for (Product product : boughtProducts) {
            product.addBuyer(customer);
        }
    }

    private boolean hasDiscountCode(Discount discount) {
        return discount != null;
    }

    private boolean DiscountWithThisIdDoesNotExist(String discountId, Discount discount) {
        return discount == null && discountId != null;
    }


    private BuyingLog getBuyingLog(ArrayList<String> receiverInformation, Discount discount, Seller seller, ArrayList<Product> productsBoughtFromThisSeller) {
        return new BuyingLog(
                LocalDateTime.now(),
                getMoneyPaidByCustomer(productsBoughtFromThisSeller, discount),
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
        return getMoneyReceivedBySeller(productsBoughtFromThisSeller) - getMoneyPaidByCustomer(productsBoughtFromThisSeller, discount);
    }

    private double getMoneyPaidByCustomer(ArrayList<Product> productsBoughtFromThisSeller, Discount discount) {
        if (discount == null) return getMoneyReceivedBySeller(productsBoughtFromThisSeller);
        else {
            double moneyPaidToThisSeller = getMoneyReceivedBySeller(productsBoughtFromThisSeller);
            double moneyPaidToAllSellers = 0;
            for (Product product : customer.getCart().getProducts()) {
                moneyPaidToAllSellers += product.getAuctionedPrice();
            }
            return (moneyPaidToThisSeller / moneyPaidToAllSellers) * customer.getCart().getTotalPrice(discount);
        }
    }

    private double getAmountOfAuctionApplied(ArrayList<Product> productsBoughtFromThisSeller) {
        double amountOfAuctionApplied = 0;
        for (Product product : productsBoughtFromThisSeller) {
            Auction thisProductAuction = product.getAuction();
            if (thisProductAuction == null) amountOfAuctionApplied += 0;
            else amountOfAuctionApplied += thisProductAuction.getDiscountAmount();
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

    public HashMap<Product, Integer> getProductsInCart() {
        return customer.getCart().getProductsMap();
    }

    public static class WrongDiscountIdException extends Exception {
        public WrongDiscountIdException(String message) {
            super(message);
        }
    }

    public static class UsedDiscountIdException extends Exception {
        public UsedDiscountIdException(String message) {
            super(message);
        }
    }

    public double getTotalPrice(Discount discount){
        return customer.getCart().getTotalPrice(discount);
    }

    public double getTotalPrice(String discountId){
        Discount discount = Discount.getDiscountById(discountId);
        return customer.getCart().getTotalPrice(discount);
    }
}
