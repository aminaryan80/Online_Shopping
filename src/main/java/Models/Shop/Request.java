package Models.Shop;

import Control.Manager;
import Models.Account.Account;
import Models.Account.Seller;
import Models.Address;
import Models.Gson;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Request {
    protected static ArrayList<Request> allRequests = new ArrayList<Request>();
    protected String id;
    protected Seller seller;
    protected String sellerName;
    protected RequestType type;

    public Request(String id, Seller seller) {
        this.id = id;
        this.seller = seller;
        this.sellerName = seller.getName();
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

    public static void open(){
        File folder = new File(Address.REQUESTS.get());
        if(!folder.exists()) folder.mkdirs();
        else {
            for (File file : folder.listFiles()) {
                allRequests.add(open(file));
            }
        }
    }

    public static Request open(File file){
        StringBuilder json = new StringBuilder();
        try {
            Scanner reader = new Scanner(file);
            while (reader.hasNext()) {
                json.append(reader.next());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return Gson.INSTANCE.get().fromJson(json.toString(),Request.class);
    }

    public static void save(){
        for (Request request : allRequests) {
            save(request);
        }
    }

    public static void save(Request request){
        try {
            String jsonAccount = Gson.INSTANCE.get().toJson(request);
            try {
                FileWriter file = new FileWriter(Address.CATEGORIES.get() +"\\"+request.getId()+".json");
                file.write(jsonAccount);
                file.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void loadReferences() {
        for (Request request : allRequests) {
            request.seller = (Seller) Account.getAccountByUsername(request.sellerName);
            request.loadReference();
        }
    }

    protected abstract void loadReference();

    public String getId() {
        return id;
    }

    @Override
    public abstract String toString();
}

