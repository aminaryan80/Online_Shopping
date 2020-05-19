package Models.Shop.Product;

import Control.Identity;
import Models.Account.Account;
import Models.Address;
import Models.Gson;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
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

    public enum CommentStatus {
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

    public static void open() throws Exception {
        File folder = new File(Address.COMMENTS.get());
        if (!folder.exists()) folder.mkdirs();
        else {
            for (File file : folder.listFiles()) {
                allComments.add(open(file));
            }
        }
    }

    public static Comment open(File file) throws Exception {
        StringBuilder json = new StringBuilder();
        Scanner reader = new Scanner(file);
        while (reader.hasNext()) json.append(reader.next());
        reader.close();
        return Gson.INSTANCE.get().fromJson(json.toString(), Comment.class);
    }

    public static void save() throws Exception {
        for (Comment comment : allComments) {
            save(comment);
        }
    }

    public static void save(Comment comment) throws Exception {
        String jsonAccount = Gson.INSTANCE.get().toJson(comment);
        FileWriter file = new FileWriter(Address.RATES.get() + "\\" + comment.getId() + ".json");
        file.write(jsonAccount);
        file.close();
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
