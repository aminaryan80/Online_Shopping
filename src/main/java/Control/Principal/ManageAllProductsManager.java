package Control.Principal;

import Control.Manager;
import Models.Account.Account;
import Models.Account.Customer;
import Models.Shop.Product.Product;
import View.Principal.ManageAllProductsMenu;

public class ManageAllProductsManager extends Manager {
    public ManageAllProductsManager(Account account) {
        super(account);
        new ManageAllProductsMenu(this);
    }

    public void removeProductById(String id) {
        Product.deleteProduct(Product.getProductById(id));
        Customer.deleteProductFromCarts(Product.getProductById(id));
    }
}
