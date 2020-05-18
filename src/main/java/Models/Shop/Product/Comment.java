package Models.Shop.Product;

import Control.Identity;
import Models.Account.Account;

import java.util.ArrayList;
import java.util.zip.CheckedOutputStream;

public class Comment {
    private Account account;
    private Product product;
    private String text;
    private CommentStatus status;
    private boolean hasPurchased;
    private String id;
    private static ArrayList<Comment> allComments = new ArrayList<>();
    public Comment(Account account, Product product, String text, CommentStatus status, boolean hasPurchased) {
        this.account = account;
        this.product = product;
        this.text = text;
        this.status = status;
        this.hasPurchased = hasPurchased;
        id = Identity.getId();
        allComments.add(this);
    }

    private enum CommentStatus {
        PENDING, CONFIRMED, DENIED;
    }

    public String getText() {
        return text;
    }

    public String getId() {
        return id;
    }

    public static Comment getCommentById(String id){
        for (Comment comment : allComments) {
            if(comment.getId().equals(id)){
                return comment;
            }
        }
        return null;
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
