package Models.Shop;

import Models.Account.Account;

public class Comment {
    private Account account;
    private Product product;
    private String text;
    private CommentStatus status;
    private boolean hasPurchased;

    private enum CommentStatus {
        PENDING, CONFIRMED, DENIED;
    }
}
