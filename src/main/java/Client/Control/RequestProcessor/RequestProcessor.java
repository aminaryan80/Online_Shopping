package Client.Control.RequestProcessor;

import Client.Control.CustomerManagers.ViewCartManager;
import Client.Control.Manager;
import Client.Control.UserPanel.LoginToExistingAccountManager;
import Models.Account.Account;
import Models.Account.Customer;
import Models.Account.Principal;
import Models.Account.Seller;
import Models.Gson;
import Models.Shop.Off.Discount;
import Models.Shop.Product.Product;
import Models.Shop.Request.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestProcessor {

    public static String processRequest(String request) {
        Matcher matcher;
        String response = null;
        if ((matcher = getMatcher(request, "LOGIN (\\S+) (\\S+)")).find()) {
            response = login(matcher);
        } else if ((matcher = getMatcher(request, "GET_ACCOUNT (\\S+)")).find()) {
            response = getAccount(matcher);
        } else if (getMatcher(request, "GET_ALL_PRINCIPALS").find()) {
            response = getAllPrincipals();
        } else if (getMatcher(request, "GET_ALL_CUSTOMERS").find()) {
            response = getAllCustomers();
        } else if (getMatcher(request, "GET_ALL_SELLERS").find()) {
            response = getAllSellers();
        } else if (getMatcher(request, "GET_ALL_DISCOUNTS").find()) {
            response = getAllDiscounts();
        } else if (getMatcher(request, "GET_ALL_REQUESTS").find()) {
            response = getAllRequests();
        } else if ((matcher = getMatcher(request, "TOTAL_AMOUNT (\\S+)")).find()) {
            response = getTotalAmount(matcher.group(1));
        } else if ((matcher = getMatcher(request, "GET_CART_PRODUCTS (\\S+)")).find()) {
            response = getCartProducts(matcher.group(1));
        } else if ((matcher = getMatcher(request, "PRODUCT_QUANTITY (INCREASE|DECREASE) (\\S+) (\\S+)")).find()) {
            response = changeCartProductQuantity(matcher.group(1), matcher.group(2), matcher.group(3));
        } else if ((matcher = getMatcher(request,"IS_CART_EMPTY (\\S+)")).find()){
            response = isCartEmpty(matcher.group(1));
        } else if ((matcher =getMatcher(request,"CLEAR_CART (\\S+)")).find()){
            response = clearCart(matcher.group(1));
        }
            return response;
    }

    private static String clearCart(String username) {
        ((Customer) Account.getAccountByUsername(username)).getCart().empty();
        return "cart emptied successfully!";
    }

    private static String isCartEmpty(String username) {
        if(((Customer) Account.getAccountByUsername(username)).getCart().getProducts().size() == 0) {
            return "YES";
        } else return "NO";
    }

    private static String changeCartProductQuantity(String action, String productId, String username) {
        ViewCartManager viewCartManager = new ViewCartManager(Account.getAccountByUsername(username));
        if (action.equals("INCREASE")) {
            viewCartManager.productQuantity(productId, true);
            return "product added successfully";
        } else {
            viewCartManager.productQuantity(productId, false);
            return "product reduced successfully";
        }
    }

    private static String getCartProducts(String username) {
        HashMap<Product, Integer> productIntegerHashMap = ((Customer) Account.getAccountByUsername(username)).getCart().getProductsMap();
        return Gson.INSTANCE.get().toJson(productIntegerHashMap);
    }

    private static String getTotalAmount(String username) {
        return "" + ((Customer) Account.getAccountByUsername(username)).getCart().getTotalPrice(null) + "$";
    }

    private static String getAllPrincipals() {
        ArrayList<Principal> principals = new ArrayList<>();
        for (Account account : Account.getAllAccounts()) {
            if (account instanceof Principal) {
                principals.add((Principal) account);
            }
        }
        return Gson.INSTANCE.get().toJson(principals);
    }

    private static String getAllCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        for (Account account : Account.getAllAccounts()) {
            if (account instanceof Customer) {
                customers.add((Customer) account);
            }
        }
        return Gson.INSTANCE.get().toJson(customers);
    }

    private static String getAllSellers() {
        ArrayList<Seller> sellers = new ArrayList<>();
        for (Account account : Account.getAllAccounts()) {
            if (account instanceof Seller) {
                sellers.add((Seller) account);
            }
        }
        return Gson.INSTANCE.get().toJson(sellers);
    }


    private static String getAllDiscounts() {
        return Gson.INSTANCE.get().toJson(Discount.getAllDiscounts());
    }

    private static String getAllRequests() {
        ArrayList<AddOffRequest> addOffRequests = new ArrayList<>();
        ArrayList<AddProductRequest> addProductRequests = new ArrayList<>();
        ArrayList<AddSellerRequest> addSellerRequests = new ArrayList<>();
        ArrayList<DeleteProductRequest> deleteProductRequests = new ArrayList<>();
        ArrayList<EditOffRequest> editOffRequests = new ArrayList<>();
        ArrayList<EditProductRequest> editProductRequests = new ArrayList<>();
        for (Request request : Request.getAllRequests()) {
            if (request instanceof AddOffRequest) {
                addOffRequests.add((AddOffRequest) request);
            } else if (request instanceof AddProductRequest) {
                addProductRequests.add((AddProductRequest) request);
            } else if (request instanceof AddSellerRequest) {
                addSellerRequests.add((AddSellerRequest) request);
            } else if (request instanceof DeleteProductRequest) {
                deleteProductRequests.add((DeleteProductRequest) request);
            } else if (request instanceof EditOffRequest) {
                editOffRequests.add((EditOffRequest) request);
            } else if (request instanceof EditProductRequest) {
                editProductRequests.add((EditProductRequest) request);
            }
        }
        return Gson.INSTANCE.get().toJson(addOffRequests) + "&&&" + Gson.INSTANCE.get().toJson(addProductRequests) + "&&&"
                + Gson.INSTANCE.get().toJson(addSellerRequests) + "&&&" + Gson.INSTANCE.get().toJson(deleteProductRequests) + "&&&"
                + Gson.INSTANCE.get().toJson(editOffRequests) + "&&&" + Gson.INSTANCE.get().toJson(editProductRequests);
    }

    private static String getAccount(Matcher matcher) {
        return Gson.INSTANCE.get().toJson(Account.getAccountByUsername(matcher.group(1)));
    }

    private static String login(Matcher matcher) {
        LoginToExistingAccountManager l = new LoginToExistingAccountManager(null);
        return l.login(matcher.group(1), matcher.group(2));
    }


    private static Matcher getMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }
}
