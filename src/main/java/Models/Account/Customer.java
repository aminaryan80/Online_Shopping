package Models.Account;

import Models.Shop.Cart;

public class Customer extends Account {
    private Cart cart;

    public Cart getCart() {
        return cart;
    }
}
