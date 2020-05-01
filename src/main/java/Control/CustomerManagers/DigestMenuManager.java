package Control.CustomerManagers;

import Models.Account.Account;
import Models.Shop.Product;
import View.CustomerMenus.DigestMenu;

public class DigestMenuManager extends ProductPageManager{

    public DigestMenuManager(Account account, Product product) {
        super(account, product);
        this.menu = new DigestMenu(this);
    }

    public void addToCart() {
    customer.getCart().addProduct(product);
    }

    public void selectSeller(String sellerUsername) {
    //TODO WHAT TO DO WHAT NOT TO DO?
    }
}
