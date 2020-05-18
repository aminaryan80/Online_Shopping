package Control.CustomerManagers;

import Control.Manager;
import Control.UtilTestObject;
import Models.Account.Account;
import Models.Account.Customer;
import Models.Shop.Product.Product;
import View.CustomerMenus.product.DigestMenu;

public class DigestMenuManager extends Manager {
    protected Customer customer = (Customer) account;
    protected Product product;

    public DigestMenuManager(Account account, Product product) {
        super(account);
        this.product = product;
        if (!account.getUsername().equals(UtilTestObject.CUSTOMER))
            this.menu = new DigestMenu(this);
    }

    public void addToCart() {
        customer.getCart().addProduct(product);
    }

//    public void selectSeller(String sellerUsername) {
//    // TODO WHAT TO DO WHAT NOT TO DO?
//    }
}
