package Models.Shop;

import Control.Manager;
import Models.Account.Account;
import Models.Account.Seller;

import java.util.ArrayList;

public abstract class Request {
    protected static ArrayList<Request> allRequests = new ArrayList<Request>();
    protected String id;
    protected Manager manager;
    protected Seller seller;
    protected RequestType type;

    public Request(String id, Seller seller, Manager manager) {
        this.id = id;
        this.seller = seller;
        this.manager = manager;
        allRequests.add(this);
    }

    public abstract void accept();

    public static ArrayList<String> viewRequestsInShort() {
        ArrayList<String> allRequestsShortViews = new ArrayList<String>();
        for (Request request : allRequests) {
            allRequestsShortViews.add(request.id + " : " + request.type);
        }
        return allRequestsShortViews;
    }

    public static Request getRequestById(String id) {
        for (Request request : allRequests) {
            if (request.id.equals(id)) {
                return request;
            }
        }
        return null;
    }

    public static boolean hasRequestById(String id) {
        for (Request request : allRequests) {
            if (request.id.equals(id)) {
                return true;
            }
        }
        return false;
    }

    public static void deleteRequest(Request request) {
        allRequests.remove(request);
    }

    protected enum RequestType {
        ADD_PRODUCT, EDIT_PRODUCT, ADD_OFF, EDIT_OFF
    }

    @Override
    public abstract String toString();
}

