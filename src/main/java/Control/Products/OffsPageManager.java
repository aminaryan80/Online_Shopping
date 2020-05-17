package Control.Products;

import Control.Manager;
import Models.Account.Account;
import View.Products.ProductsMenu;

public class OffsPageManager extends Manager {

    public OffsPageManager(Account account) {
        super(account);
        this.menu = new ProductsMenu(this);
    }
}
