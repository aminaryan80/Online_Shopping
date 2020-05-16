package Models.Shop.Product;

import Models.Account.Account;

public class Comment {
    private Account account;
    private Product product;
    private String text;
    private CommentStatus status;
    private boolean hasPurchased;

    public Comment(Account account, Product product, String text, CommentStatus status, boolean hasPurchased) {
        this.account = account;
        this.product = product;
        this.text = text;
        this.status = status;
        this.hasPurchased = hasPurchased;
    }

    private enum CommentStatus {
        PENDING, CONFIRMED, DENIED;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "account=" + account +
                ", product=" + product +
                ", text='" + text + '\'' +
                ", status=" + status +
                ", hasPurchased=" + hasPurchased +
                '}';
    }
}
