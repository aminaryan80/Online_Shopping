package Models.Shop.Request;

import Control.Manager;
import Models.Account.Account;
import Models.Account.Seller;
import Models.Address;
import Models.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Request {
    protected static ArrayList<Request> allRequests = new ArrayList<>();
    protected String id;
    protected Manager manager;
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
        ArrayList<String> allRequestsShortViews = new ArrayList<>();
        for (Request request : allRequests) {
            allRequestsShortViews.add(request.id + " : " + request.type);
        }
        return allRequestsShortViews;
    }

    public abstract void decline();

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

    public String getId() {
        return id;
    }

    @Override
    public abstract String toString();

    protected enum RequestType {
        ADD_PRODUCT, EDIT_PRODUCT, ADD_OFF, EDIT_OFF
    }

    public static void open() {
        openAddOffRequests();
        openAddProductRequests();
        openEditOffRequests();
        openEditProductRequests();
    }
    
    public static void openAddOffRequests(){
        File folder = new File(Address.ADD_OFF_REQUESTS.get());
        if(!folder.exists()) folder.mkdirs();
        else {
            for (File file : folder.listFiles()) {
                allRequests.add(openAddOffRequest(file));
            }
        }
    }

    public static void openAddProductRequests(){
        File folder = new File(Address.ADD_PRODUCT_REQUESTS.get());
        if(!folder.exists()) folder.mkdirs();
        else {
            for (File file : folder.listFiles()) {
                allRequests.add(openAddProductRequest(file));
            }
        }
    }

    public static void openEditOffRequests(){
        File folder = new File(Address.EDIT_OFF_REQUESTS.get());
        if(!folder.exists()) folder.mkdirs();
        else {
            for (File file : folder.listFiles()) {
                allRequests.add(openEditOffRequest(file));
            }
        }
    }

    public static void openEditProductRequests(){
        File folder = new File(Address.EDIT_PRODUCT_REQUESTS.get());
        if(!folder.exists()) folder.mkdirs();
        else {
            for (File file : folder.listFiles()) {
                allRequests.add(openEditProductRequest(file));
            }
        }
    }

    public static AddOffRequest openAddOffRequest(File file){
        StringBuilder json = fileToString(file);
        return Gson.INSTANCE.get().fromJson(json.toString(),AddOffRequest.class);
    }

    public static AddProductRequest openAddProductRequest(File file){
        StringBuilder json = fileToString(file);
        return Gson.INSTANCE.get().fromJson(json.toString(),AddProductRequest.class);
    }

    public static EditOffRequest openEditOffRequest(File file){
        StringBuilder json = fileToString(file);
        return Gson.INSTANCE.get().fromJson(json.toString(),EditOffRequest.class);
    }

    public static EditProductRequest openEditProductRequest(File file){
        StringBuilder json = fileToString(file);
        return Gson.INSTANCE.get().fromJson(json.toString(),EditProductRequest.class);
    }

    private static StringBuilder fileToString(File file) {
        StringBuilder json = new StringBuilder();
        try {
            Scanner reader = new Scanner(file);
            while (reader.hasNext()) {
                json.append(reader.next());
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static void save() throws Exception{
        for (Request request : allRequests) {
            save(request);
        }
    }

    public static void save(Request request) throws Exception{
        if (request instanceof AddOffRequest) {
            saveAddOffRequest(request);
        } else if (request instanceof AddProductRequest) {
            saveAddProductRequest(request);
        } else if (request instanceof EditOffRequest) {
            saveEditOffRequest(request);
        } else if (request instanceof EditProductRequest){
            saveEditProductRequest(request);
        }
    }

    private static void saveAddOffRequest(Request request) throws Exception {
        AddOffRequest addOffRequest = (AddOffRequest) request;
        String jsonRequest = Gson.INSTANCE.get().toJson(addOffRequest);
        write(request, jsonRequest, Address.ADD_OFF_REQUESTS);
    }

    private static void saveAddProductRequest(Request request) throws Exception {
        AddProductRequest addProductRequest = (AddProductRequest) request;
        String jsonRequest = Gson.INSTANCE.get().toJson(addProductRequest);
        write(request, jsonRequest, Address.ADD_PRODUCT_REQUESTS);
    }

    private static void saveEditOffRequest(Request request) throws Exception {
        EditOffRequest editOffRequest = (EditOffRequest) request;
        String jsonRequest = Gson.INSTANCE.get().toJson(editOffRequest);
        write(request, jsonRequest, Address.EDIT_PRODUCT_REQUESTS);
    }

    private static void saveEditProductRequest(Request request) throws Exception {
      EditProductRequest editProductRequest = (EditProductRequest) request;
        String jsonRequest = Gson.INSTANCE.get().toJson(editProductRequest);
        write(request, jsonRequest, Address.EDIT_PRODUCT_REQUESTS);
    }

    private static void write(Request request, String jsonRequest, Address addProductRequests) throws IOException {
        FileWriter file = new FileWriter(addProductRequests.get() + "\\" + request.getId() + ".json");
        file.write(jsonRequest);
        file.close();
    }

    public static void loadReferences() {
        for (Request request : allRequests) {
            request.seller = (Seller) Account.getAccountByUsername(request.sellerName);
            request.loadReference();
        }
    }

    protected abstract void loadReference();
}

