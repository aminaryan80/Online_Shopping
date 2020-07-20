package Client.Control.RequestProcessor;

import Client.Control.CustomerManagers.PurchaseManager;
import Client.Control.CustomerManagers.ViewCartManager;
import Client.Control.EditPasswordManager;
import Client.Control.Manager;
import Client.Control.Principal.PrincipalManager;
import Client.Control.UserPanel.LoginToExistingAccountManager;
import Models.Account.Account;
import Models.Account.Customer;
import Models.Account.Principal;
import Models.Account.Seller;
import Models.Gson;
import Models.Shop.Category.Category;
import Models.Shop.Off.Discount;
import Models.Shop.Product.Product;
import Models.Shop.Request.*;
import com.google.gson.reflect.TypeToken;

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
        } else if ((matcher = getMatcher(request, "UPDATE_PROFILE (\\S+) ((.|\\n)+)")).find()) {
            response = updateProfile(matcher);
        } else if ((matcher = getMatcher(request, "EDIT_PASSWORD (\\S+) (\\S+) (\\S+)")).find()) {
            response = editPassword(matcher);
        } else if ((matcher = getMatcher(request, "GET_CUSTOMER_LOGS (\\S+)")).find()) {
            response = getCustomerLogs(matcher);
        } else if ((matcher = getMatcher(request, "GET_SELLER_LOGS (\\S+)")).find()) {
            response = getSellerLogs(matcher);
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
        } else if (getMatcher(request, "GET_ALL_CATEGORIES").find()) {
            response = getAllCategories();
        } else if ((matcher = getMatcher(request, "TOTAL_AMOUNT (\\S+)")).find()) {
            response = getTotalAmount(matcher.group(1),null);
        } else if ((matcher = getMatcher(request, "GET_CART_PRODUCTS (\\S+)")).find()) {
            response = getCartProducts(matcher.group(1));
        } else if ((matcher = getMatcher(request, "PRODUCT_QUANTITY (INCREASE|DECREASE) (\\S+) (\\S+)")).find()) {
            response = changeCartProductQuantity(matcher.group(1), matcher.group(2), matcher.group(3));
        } else if ((matcher = getMatcher(request, "IS_CART_EMPTY (\\S+)")).find()) {
            response = isCartEmpty(matcher.group(1));
        } else if ((matcher = getMatcher(request, "CLEAR_CART (\\S+)")).find()) {
            response = clearCart(matcher.group(1));
        } else if ((matcher = getMatcher(request, "CAN_PAY (\\S+) (\\S+)")).find()) {
            response = canPay(matcher.group(1), matcher.group(2));
        } else if (((matcher = getMatcher(request, "IS_DISCOUNTCODE_VALID (\\S+) (\\S+)")).find())) {
            response = isDiscountcodeValid(matcher.group(1), matcher.group(2));
        } else if (((matcher = getMatcher(request, "DOES_DISCOUNT_BELONG_TO_CUSTOMER (\\S+) (\\S+)")).find())) {
            response = doesDiscountBelongToCustomer(matcher.group(1), matcher.group(2));
        } else if (((matcher = getMatcher(request, "CAN_USE_DISCOUNT (\\S+) (\\S+)")).find())) {
            response = canUseDiscount(matcher.group(1), matcher.group(2));
        } else if (((matcher = getMatcher(request, "IS_DISCOUNTCODE_ACTIVE (\\S+) (\\S+)")).find())) {
            response = isDiscountActive(matcher.group(1), matcher.group(2));
        } else if (((matcher = getMatcher(request, "TOTAL_AMOUNT (\\S+) (\\S+)")).find())) {
            response = getTotalAmount(matcher.group(1), matcher.group(2));
        } else if (((matcher = getMatcher(request, "IS_PHONENUMBER_VALID (\\S+)")).find())) {
            response = isPhoneNumberValid(matcher.group(1));
        } else if ((matcher = getMatcher(request,"PAY (\\S+) (\\S+) (\\S+) (\\S+)")).find()) {
            response = pay(matcher.group(1),matcher.group(2),matcher.group(3),matcher.group(4));
        }
            return response;

    }

    private static String pay(String username, String discountId, String address, String phoneNumber) {
        PurchaseManager purchaseManager = new PurchaseManager(Account.getAccountByUsername(username));
        ArrayList<String> info = new ArrayList<>();
        info.add(address);
        info.add(phoneNumber);
        try {
            purchaseManager.pay(info,discountId);
        } catch (PurchaseManager.WrongDiscountIdException e) {
            e.printStackTrace();
        } finally {
            return "purchased successfully!";
        }
    }

    private static String isPhoneNumberValid(String phoneNumber) {
        Manager manager = new Manager(null) {
            @Override
            public boolean checkPhoneNumber(String phoneNumber) {
                return super.checkPhoneNumber(phoneNumber);
            }
        };
        if(manager.checkPhoneNumber(phoneNumber)) return "YES";
        else return "NO";
    }

    private static String isDiscountActive(String username, String discountId) {
        PurchaseManager purchaseManager = new PurchaseManager(Account.getAccountByUsername(username));
        if(purchaseManager.isDiscountActive(discountId)) return "YES";
        else return "NO";
    }

    private static String canUseDiscount(String username, String discountId) {
        PurchaseManager purchaseManager = new PurchaseManager(Account.getAccountByUsername(username));
        if(purchaseManager.canUseDiscount(discountId)) return "YES";
        else return "NO";
    }

    private static String doesDiscountBelongToCustomer(String username, String discountId) {
        PurchaseManager purchaseManager = new PurchaseManager(Account.getAccountByUsername(username));
        if(purchaseManager.doesDiscountBelongToCustomer(discountId)) return "YES";
        else return "NO";
    }

    private static String isDiscountcodeValid(String username, String discountId) {
        PurchaseManager purchaseManager = new PurchaseManager(Account.getAccountByUsername(username));
        if (purchaseManager.isDiscountCodeValid(discountId)) return "YES";
        else return "NO";
    }

    private static String canPay(String username, String discountId) {
        PurchaseManager purchaseManager = new PurchaseManager(Account.getAccountByUsername(username));
        try {
            if (purchaseManager.canPay(discountId)) return "YES";
            else return "NO";
        } catch (PurchaseManager.WrongDiscountIdException e) {
            e.printStackTrace();
        } catch (PurchaseManager.UsedDiscountIdException e) {
            e.printStackTrace();
        }
        return "NO";
    }

    private static String getAllCategories() {
        return Gson.INSTANCE.get().toJson(Category.getAllCategories());
    }

    private static String getCustomerLogs(Matcher matcher) {
        Customer customer = (Customer) Customer.getAccountByUsername(matcher.group(1));
        return Gson.INSTANCE.get().toJson(customer.getAllLogs());
    }

    private static String getSellerLogs(Matcher matcher) {
        Seller seller = (Seller) Seller.getAccountByUsername(matcher.group(1));
        return Gson.INSTANCE.get().toJson(seller.getAllLogs());
    }

    private static String clearCart(String username) {
        ((Customer) Account.getAccountByUsername(username)).getCart().empty();
        return "cart emptied successfully!";
    }

    private static String isCartEmpty(String username) {
        if (((Customer) Account.getAccountByUsername(username)).getCart().getProducts().size() == 0) {
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

    private static String getTotalAmount(String username,String discountId) {
        Discount discount = Discount.getDiscountById(discountId);
        return "" + ((Customer) Account.getAccountByUsername(username)).getCart().getTotalPrice(discount) + "$";
    }

    private static String editPassword(Matcher matcher) {
        EditPasswordManager editPasswordManager = new EditPasswordManager(Account.getAccountByUsername(matcher.group(1)));
        return editPasswordManager.editPassword(matcher.group(2), matcher.group(3)) + "";
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

    private static String updateProfile(Matcher matcher) {
        Account account = Account.getAccountByUsername(matcher.group(1));
        ArrayList<String> inputs = new ArrayList<>(Gson.INSTANCE.get().fromJson(matcher.group(2),
                new TypeToken<ArrayList<String>>() {
                }.getType()));
        PrincipalManager manager = new PrincipalManager(account);
        if (manager.isEnteredInputValid(inputs.get(2), inputs.get(3))) {
            account.setFirstName(inputs.get(0));
            account.setLastName(inputs.get(1));
            account.setEmail(inputs.get(2));
            account.setPhoneNumber(inputs.get(3));
        }
        if (account instanceof Seller) {
            ((Seller) account).setCompanyName(inputs.get(4));
        }
        return "0";
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
