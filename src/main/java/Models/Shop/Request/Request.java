package Models.Shop.Request;

import Control.Identity;
import Control.Manager;
import Models.Account.Seller;
import Models.Address;
import Models.Gson;
import org.apache.commons.io.FileUtils;

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

    public Request(Seller seller) {
        this.id = Identity.getId();
        this.seller = seller;
        this.sellerName = seller.getName();
        allRequests.add(this);
    }

    public Request() {
        this.id = Identity.getId();
        allRequests.add(this);
    }

    public static ArrayList<Request> getAllRequests() {
        return allRequests;
    }

    public static ArrayList<String> viewRequestsInShort() {
        ArrayList<String> allRequestsShortViews = new ArrayList<>();
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

    protected static void deleteRequest(Request request, String address) throws IOException {
        allRequests.remove(request);
        File file = new File(Address.REQUESTS.get() + "\\" + address + "\\" + request.getId() + ".json");
        try {
            if (file.exists())
                FileUtils.forceDelete(file);
        } catch (Exception ignored) {

        }
    }

    public static void open() {
        openAddOffRequests();
        openAddProductRequests();
        openEditOffRequests();
        openEditProductRequests();
        openDeleteProductRequest();
        openAddSellerRequests();
    }

    private static void openAddSellerRequests() {
        File folder = new File(Address.ADD_SELLER_REQUEST.get());
        if (!folder.exists()) folder.mkdirs();
        else {
            for (File file : folder.listFiles()) {
                allRequests.add(openAddSellerRequest(file));
            }
        }
    }

    private static void openDeleteProductRequest() {
        File folder = new File(Address.DELETE_PRODUCT_REQUEST.get());
        if (!folder.exists()) folder.mkdirs();
        else {
            for (File file : folder.listFiles()) {
                allRequests.add(openDeleteProductRequest(file));
            }
        }
    }

    private static void openAddOffRequests() {
        File folder = new File(Address.ADD_OFF_REQUESTS.get());
        if (!folder.exists()) folder.mkdirs();
        else {
            for (File file : folder.listFiles()) {
                allRequests.add(openAddOffRequest(file));
            }
        }
    }

    private static void openAddProductRequests() {
        File folder = new File(Address.ADD_PRODUCT_REQUESTS.get());
        if (!folder.exists()) folder.mkdirs();
        else {
            for (File file : folder.listFiles()) {
                allRequests.add(openAddProductRequest(file));
            }
        }
    }

    private static void openEditOffRequests() {
        File folder = new File(Address.EDIT_OFF_REQUESTS.get());
        if (!folder.exists()) folder.mkdirs();
        else {
            for (File file : folder.listFiles()) {
                allRequests.add(openEditOffRequest(file));
            }
        }
    }

    private static void openEditProductRequests() {
        File folder = new File(Address.EDIT_PRODUCT_REQUESTS.get());
        if (!folder.exists()) folder.mkdirs();
        else {
            for (File file : folder.listFiles()) {
                allRequests.add(openEditProductRequest(file));
            }
        }
    }

    private static AddSellerRequest openAddSellerRequest(File file) {
        StringBuilder json = fileToString(file);
        return Gson.INSTANCE.get().fromJson(json.toString(), AddSellerRequest.class);
    }

    private static DeleteProductRequest openDeleteProductRequest(File file) {
        StringBuilder json = fileToString(file);
        return Gson.INSTANCE.get().fromJson(json.toString(), DeleteProductRequest.class);
    }

    private static AddOffRequest openAddOffRequest(File file) {
        StringBuilder json = fileToString(file);
        return Gson.INSTANCE.get().fromJson(json.toString(), AddOffRequest.class);
    }

    private static AddProductRequest openAddProductRequest(File file) {
        StringBuilder json = fileToString(file);
        return Gson.INSTANCE.get().fromJson(json.toString(), AddProductRequest.class);
    }

    private static EditOffRequest openEditOffRequest(File file) {
        StringBuilder json = fileToString(file);
        return Gson.INSTANCE.get().fromJson(json.toString(), EditOffRequest.class);
    }

    private static EditProductRequest openEditProductRequest(File file) {
        StringBuilder json = fileToString(file);
        return Gson.INSTANCE.get().fromJson(json.toString(), EditProductRequest.class);
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

    public static void save() throws Exception {
        for (Request request : allRequests) {
            save(request);
        }
    }

    private static void save(Request request) throws Exception {
        if (request instanceof AddOffRequest) {
            saveAddOffRequest(request);
        } else if (request instanceof AddProductRequest) {
            saveAddProductRequest(request);
        } else if (request instanceof EditOffRequest) {
            saveEditOffRequest(request);
        } else if (request instanceof EditProductRequest) {
            saveEditProductRequest(request);
        } else if (request instanceof DeleteProductRequest) {
            saveDeleteProductRequest(request);
        } else if (request instanceof AddSellerRequest) {
            saveAddSellerRequest(request);
        }
    }

    private static void saveAddSellerRequest(Request request) throws IOException {
        AddSellerRequest addSellerRequest = (AddSellerRequest) request;
        String jsonRequest = Gson.INSTANCE.get().toJson(addSellerRequest);
        write(request, jsonRequest, Address.ADD_SELLER_REQUEST);
    }

    private static void saveDeleteProductRequest(Request request) throws IOException {
        DeleteProductRequest deleteProductRequest = (DeleteProductRequest) request;
        String jsonRequest = Gson.INSTANCE.get().toJson(deleteProductRequest);
        write(request, jsonRequest, Address.DELETE_PRODUCT_REQUEST);
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

    public abstract void accept() throws IOException;

    public abstract void decline() throws IOException;

    public String getId() {
        return id;
    }

    @Override
    public abstract String toString();

    protected enum RequestType {
        ADD_PRODUCT, EDIT_PRODUCT, ADD_OFF, EDIT_OFF, DELETE_PRODUCT, ADD_SELLER_REQUEST
    }

//    public static void loadReferences() throws IOException {
//        for (Request request : allRequests) {
//            request.seller = (Seller) Account.getAccountByUsername(request.sellerName);
//            request.loadReference();
//        }
//    }

    //   protected abstract void loadReference() throws IOException;
}

