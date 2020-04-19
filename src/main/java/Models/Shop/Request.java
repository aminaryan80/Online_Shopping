package Models.Shop;

import Models.Account.Account;

import java.util.ArrayList;

public class Request {
    private static ArrayList<Request> allRequests = new ArrayList<>();
    private String id;
    private Account seller;
    private RequestType type;

    public Request(String id, Account seller, RequestType type) {
        this.id = id;
        this.seller = seller;
        this.type = type;
    }

    public static ArrayList<String> viewRequestsInShort() {

    }

    public static Request getRequestById(String id) {

    }

    public static boolean hasRequestById(String id) {

    }

    public static void deleteRequest(Request request) {

    }

    enum RequestType {
        ADD_PRODUCT, REMOVE_PRODUCT, EDIT_PRODUCT, DELETE_AUCTION, ADD_AUCTION, EDIT_AUCTION;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id='" + id + '\'' +
                ", seller=" + seller +
                ", type=" + type +
                '}';
    }
}

