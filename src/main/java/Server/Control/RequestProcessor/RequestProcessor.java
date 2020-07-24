package Server.Control.RequestProcessor;

import Models.Account.Account;
import Models.Account.Customer;
import Models.Account.Principal;
import Models.Account.Seller;
import Models.Gson;
import Models.Shop.Category.Category;
import Models.Shop.Category.Feature;
import Models.Shop.Off.Auction;
import Models.Shop.Off.Discount;
import Models.Shop.Product.Product;
import Models.Shop.Request.*;
import Server.Control.CustomerManagers.ProductPageManager;
import Server.Control.CustomerManagers.PurchaseManager;
import Server.Control.CustomerManagers.ViewCartManager;
import Server.Control.CustomerManagers.ViewOrdersManager;
import Server.Control.EditPasswordManager;
import Server.Control.Manager;
import Server.Control.Principal.ManageAllProductsManager;
import Server.Control.Principal.ManageCategories.EditCategoryManager;
import Server.Control.Principal.ManageCategories.ManageCategoriesManager;
import Server.Control.Principal.ManageRequestsManager;
import Server.Control.Principal.ManageUsersManager;
import Server.Control.Principal.PrincipalManager;
import Server.Control.Principal.ViewDiscountCodes.EditDiscountCodeManager;
import Server.Control.Principal.ViewDiscountCodes.ViewDiscountCodesManager;
import Server.Control.Products.ProductsManager;
import Server.Control.Seller.ManageProductsManager;
import Server.Control.Seller.SellerManager;
import Server.Control.Seller.ViewOffsManager;
import Server.Control.UserPanel.CreateNewAccountManager;
import Server.Control.UserPanel.LoginToExistingAccountManager;
import com.google.gson.reflect.TypeToken;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestProcessor {

    public RequestProcessor() {
        ServerSocket server;
        Socket socket;
        DataInputStream input;
        DataOutputStream output;
        try {
            server = new ServerSocket(8080);
            System.out.println("Server started");
            while (true) {
                System.out.println("Waiting for a client ...");
                socket = server.accept();
                System.out.println("Client accepted");
                // takes input from the client socket
                input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                output = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                String request;
                // reads message
                request = input.readUTF();
                String response = processRequest(request);
                output.writeUTF(response);
                output.flush();
                // close connection
                socket.close();
                input.close();
                output.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String processRequest(String request) {
        Matcher matcher;
        String response = null;
        if ((matcher = getMatcher(request, "LOGIN (\\S+) (\\S+)")).find()) {
            response = login(matcher);
        } else if ((matcher = getMatcher(request, "LOGOUT (\\S+)")).find()) {
            response = logout(matcher);
        } else if ((matcher = getMatcher(request, "REGISTER (\\S+) (\\S+) ((.|\\n)+)")).find()) {
            response = register(matcher);
        } else if (getMatcher(request, "IS_PRINCIPAL_EXISTS").find()) {
            response = isPrincipalExists();
        } else if ((matcher = getMatcher(request, "GET_ACCOUNT (\\S+)")).find()) {
            response = getAccount(matcher);
        } else if ((matcher = getMatcher(request, "GET_REQUEST_DETAILS (\\S+)")).find()) {
            response = getRequestDetails(matcher);
        } else if ((matcher = getMatcher(request, "GET_DISCOUNT_DETAILS (\\S+)")).find()) {
            response = getDiscountDetails(matcher);
        } else if ((matcher = getMatcher(request, "ACCEPT_REQUEST (\\S+)")).find()) {
            response = acceptRequest(matcher);
        } else if ((matcher = getMatcher(request, "DECLINE_REQUEST (\\S+)")).find()) {
            response = declineRequest(matcher);
        } else if ((matcher = getMatcher(request, "DELETE_ACCOUNT (\\S+) (\\S+)")).find()) {
            response = deleteAccount(matcher);
        } else if ((matcher = getMatcher(request, "DELETE_PRODUCT (\\S+)")).find()) {
            response = deleteProduct(matcher);
        } else if ((matcher = getMatcher(request, "DELETE_DISCOUNT (\\S+)")).find()) {
            response = deleteDiscount(matcher);
        } else if ((matcher = getMatcher(request, "DELETE_SELLER_PRODUCT (\\S+) (\\S+)")).find()) {
            response = deleteProductBySeller(matcher);
        } else if ((matcher = getMatcher(request, "ADD_CATEGORY ((.|\\n)+)")).find()) {
            response = addCategory(matcher);
        } else if ((matcher = getMatcher(request, "CREATE_DISCOUNT ((.|\\n)+)")).find()) {
            response = createDiscount(matcher);
        } else if ((matcher = getMatcher(request, "CREATE_AUCTION ((.|\\n)+)")).find()) {
            response = createAuction(matcher);
        } else if ((matcher = getMatcher(request, "CREATE_PRODUCT ((.|\\n)+)")).find()) {
            response = createProduct(matcher);
        } else if ((matcher = getMatcher(request, "CREATE_PROFILE_MANAGER ((.|\\n)+)")).find()) {
            response = createProfileManager(matcher);
        } else if ((matcher = getMatcher(request, "ADD_COMMENT (\\S+) (\\S+) ((.|\\n)+)")).find()) {
            response = addComment(matcher);
        } else if ((matcher = getMatcher(request, "EDIT_CATEGORY (\\S+) (\\S+) (.+)")).find()) {
            response = editCategory(matcher);
        } else if ((matcher = getMatcher(request, "EDIT_DISCOUNT (\\S+) (\\S+) (\\S+)")).find()) {
            response = editDiscount(matcher);
        } else if ((matcher = getMatcher(request, "EDIT_AUCTION (\\S+) (\\S+) (\\S+) (\\S+)")).find()) {
            response = editAuction(matcher);
        } else if ((matcher = getMatcher(request, "EDIT_PRODUCT (\\S+) (\\S+) (\\S+) (\\S+)")).find()) {
            response = editProduct(matcher);
        } else if ((matcher = getMatcher(request, "EDIT_PASSWORD (\\S+) (\\S+) (\\S+)")).find()) {
            response = editPassword(matcher);
        } else if ((matcher = getMatcher(request, "UPDATE_PROFILE (\\S+) ((.|\\n)+)")).find()) {
            response = updateProfile(matcher);
        } else if ((matcher = getMatcher(request, "GET_CUSTOMER_LOGS (\\S+)")).find()) {
            response = getCustomerLogs(matcher);
        } else if ((matcher = getMatcher(request, "GET_SELLER_LOGS (\\S+)")).find()) {
            response = getSellerLogs(matcher);
        } else if ((matcher = getMatcher(request, "GET_SELLER_AUCTIONS (\\S+)")).find()) {
            response = getSellerAuctions(matcher);
        } else if ((matcher = getMatcher(request, "GET_SELLER_PRODUCTS (\\S+)")).find()) {
            response = getSellerProducts(matcher);
        } else if ((matcher = getMatcher(request, "GET_AUCTION_PRODUCTS (\\S+)")).find()) {
            response = getAuctionProducts(matcher);
        } else if ((matcher = getMatcher(request, "GET_PRODUCT_BUYERS (\\S+)")).find()) {
            response = getProductBuyers(matcher);
        } else if ((matcher = getMatcher(request, "GET_CATEGORY (\\S+)")).find()) {
            response = getCategoryByName(matcher);
        } else if ((matcher = getMatcher(request, "GET_PRODUCT_PRICE (\\S+)")).find()) {
            response = getProductPrice(matcher);
        } else if ((matcher = getMatcher(request, "GET_PRODUCT_RATE (\\S+)")).find()) {
            response = getProductRate(matcher);
        } else if ((matcher = getMatcher(request, "GET_PRODUCT_COMMENTS (\\S+)")).find()) {
            response = getProductComments(matcher);
        } else if ((matcher = getMatcher(request, "GET_PRODUCT_FEATURES (\\S+)")).find()) {
            response = getProductFeatures(matcher);
        } else if ((matcher = getMatcher(request, "GET_PRODUCT_AUCTION (\\S+)")).find()) {
            response = getProductAuction(matcher);
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
        } else if (getMatcher(request, "GET_ALL_PRODUCTS").find()) {
            response = getAllProducts();
        } else if ((matcher = getMatcher(request, "LOAD_PRODUCTS (\\S+)")).find()) {
            response = loadProducts(matcher);
        } else if ((matcher = getMatcher(request, "HAS_PRODUCT_IN_CART (\\S+) (\\S+)")).find()) {
            response = hasProductInCart(matcher);
        } else if ((matcher = getMatcher(request, "ADD_PRODUCT_TO_CART (\\S+) (\\S+)")).find()) {
            response = addProductToCart(matcher);
        } else if ((matcher = getMatcher(request, "RATE_PRODUCT (\\S+) (\\S+)")).find()) {
            response = rateProduct(matcher);
        } else if ((matcher = getMatcher(request, "TOTAL_AMOUNT (\\S+)")).find()) {
            response = getTotalAmount(matcher.group(1), null);
        } else if ((matcher = getMatcher(request, "GET_CART_PRODUCTS (\\S+)")).find()) {
            response = getCartProducts(matcher.group(1));
        } else if ((matcher = getMatcher(request, "PRODUCT_QUANTITY (INCREASE|DECREASE) (\\S+) (\\S+)")).find()) {
            response = changeCartProductQuantity(matcher.group(1), matcher.group(2), matcher.group(3));
        } else if ((matcher = getMatcher(request, "IS_CART_EMPTY (\\S+)")).find()) {
            response = isCartEmpty(matcher.group(1));
        } else if ((matcher = getMatcher(request, "CLEAR_CART (\\S+)")).find()) {
            response = clearCart(matcher.group(1));
        } else if ((matcher = getMatcher(request, "CAN_PAY (\\S+) (\\S+) (\\S+)")).find()) {
            response = canPay(matcher.group(1), matcher.group(2),matcher.group(3));
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
        } else if ((matcher = getMatcher(request, "PAY (\\S+) (\\S+) (\\S+) (\\S+) (\\S+)")).find()) {
            response = pay(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4),matcher.group(5));
        } else if ((matcher = getMatcher(request, "GET_BUYING_LOGS (\\S+)")).find()) {
            response = getBuyingLogs(matcher.group(1));
        } else if ((matcher = getMatcher(request, "SHOW_BOUGHT_PRODUCTS (\\S+) (\\S+)")).find()) {
            response = showBoughtProducts(matcher.group(1), matcher.group(2));
        } else if ((matcher = getMatcher(request, "GET_BUYING_LOG (\\S+) (\\S+)")).find()) {
            response = getBuyingLog(matcher.group(1), matcher.group(2));
        }
        return response;

    }

    private static String getBuyingLog(String username, String lodId) {
        ViewOrdersManager viewOrdersManager = new ViewOrdersManager(Account.getAccountByUsername(username));
        return Gson.INSTANCE.get().toJson(viewOrdersManager.getLogById(lodId));
    }

    private static String showBoughtProducts(String username, String logId) {
        ViewOrdersManager viewOrdersManager = new ViewOrdersManager(Account.getAccountByUsername(username));
        viewOrdersManager.setLogToShowProducts(logId);
        return Gson.INSTANCE.get().toJson(viewOrdersManager.getOrderProductsToShow());
    }

    private static String getBuyingLogs(String username) {
        ViewOrdersManager viewOrdersManager = new ViewOrdersManager(Account.getAccountByUsername(username));
        return Gson.INSTANCE.get().toJson(viewOrdersManager.getLogs());
    }

    private static String pay(String username, String discountId, String address, String phoneNumber,String payingMethod) {
        PurchaseManager purchaseManager = new PurchaseManager(Account.getAccountByUsername(username));
        ArrayList<String> info = new ArrayList<>();
        info.add(address);
        info.add(phoneNumber);
        try {
            purchaseManager.pay(info, discountId,payingMethod);
        } catch (PurchaseManager.WrongDiscountIdException e) {
            e.printStackTrace();
        }
        return "purchased successfully!";
    }

    private static String isPhoneNumberValid(String phoneNumber) {
        Manager manager = new Manager(null) {
            @Override
            public boolean checkPhoneNumber(String phoneNumber) {
                return super.checkPhoneNumber(phoneNumber);
            }
        };
        if (manager.checkPhoneNumber(phoneNumber)) return "YES";
        else return "NO";
    }

    private static String isDiscountActive(String username, String discountId) {
        PurchaseManager purchaseManager = new PurchaseManager(Account.getAccountByUsername(username));
        if (purchaseManager.isDiscountActive(discountId)) return "YES";
        else return "NO";
    }

    private static String canUseDiscount(String username, String discountId) {
        PurchaseManager purchaseManager = new PurchaseManager(Account.getAccountByUsername(username));
        if (purchaseManager.canUseDiscount(discountId)) return "YES";
        else return "NO";
    }

    private static String doesDiscountBelongToCustomer(String username, String discountId) {
        PurchaseManager purchaseManager = new PurchaseManager(Account.getAccountByUsername(username));
        if (purchaseManager.doesDiscountBelongToCustomer(discountId)) return "YES";
        else return "NO";
    }

    private static String isDiscountcodeValid(String username, String discountId) {
        PurchaseManager purchaseManager = new PurchaseManager(Account.getAccountByUsername(username));
        if (purchaseManager.isDiscountCodeValid(discountId)) return "YES";
        else return "NO";
    }

    private static String canPay(String username, String discountId,String payingMethod) {
        PurchaseManager purchaseManager = new PurchaseManager(Account.getAccountByUsername(username));
        try {
            if (purchaseManager.canPay(discountId,payingMethod)) return "YES";
            else return "NO";
        } catch (PurchaseManager.WrongDiscountIdException | PurchaseManager.UsedDiscountIdException e) {
            e.printStackTrace();
        }
        return "NO";
    }

    private static String createDiscount(Matcher matcher) {
        String[] inputs = matcher.group(1).split("&&&");
        PrincipalManager principalManager = new PrincipalManager(null);
        return principalManager.createDiscountCode(Gson.INSTANCE.get().fromJson(inputs[0], new TypeToken<ArrayList<String>>() {
                }.getType()),
                Gson.INSTANCE.get().fromJson(inputs[1], new TypeToken<ArrayList<String>>() {
                }.getType())) + "";
    }

    private static String createAuction(Matcher matcher) {
        String[] inputs = matcher.group(1).split("&&&");
        ArrayList<String> input = new ArrayList<>(Gson.INSTANCE.get().fromJson(inputs[0], new TypeToken<ArrayList<String>>() {
        }.getType()));
        ViewOffsManager viewOffsManager = new ViewOffsManager(null);
        viewOffsManager.addOff(input.get(0), input.get(1), input.get(2), Double.parseDouble(input.get(3)),
                Gson.INSTANCE.get().fromJson(inputs[1], new TypeToken<List<String>>() {
                }.getType()));
        return "0";
    }

    private static String createProduct(Matcher matcher) {
        String[] inputs = matcher.group(1).split("&&&");
        ArrayList<String> input = new ArrayList<>(Gson.INSTANCE.get().fromJson(inputs[0], new TypeToken<ArrayList<String>>() {
        }.getType()));
        ManageProductsManager manageProductsManager = new ManageProductsManager(Account.getAccountByUsername(input.get(5)));
        manageProductsManager.addProduct(input.get(0), Category.getCategoryByName(input.get(1)),
                Double.parseDouble(input.get(2)), Boolean.parseBoolean(input.get(3)), input.get(4),
                Gson.INSTANCE.get().fromJson(inputs[1], new TypeToken<ArrayList<Feature>>() {
                }.getType()));
        return "0";
    }

    private static String acceptRequest(Matcher matcher) {
        ManageRequestsManager manageRequestsManager = new ManageRequestsManager(null);
        return manageRequestsManager.acceptRequest(matcher.group(1)) + "";
    }

    private static String declineRequest(Matcher matcher) {
        ManageRequestsManager manageRequestsManager = new ManageRequestsManager(null);
        return manageRequestsManager.declineRequest(matcher.group(1)) + "";
    }

    private static String getRequestDetails(Matcher matcher) {
        ManageRequestsManager manageRequestsManager = new ManageRequestsManager(null);
        return manageRequestsManager.showRequestDetails(matcher.group(1));
    }

    private static String getDiscountDetails(Matcher matcher) {
        ViewDiscountCodesManager viewDiscountCodesManager = new ViewDiscountCodesManager(null);
        return viewDiscountCodesManager.viewDiscountCode(matcher.group(1));
    }

    private static String editDiscount(Matcher matcher) {
        EditDiscountCodeManager editDiscountCodeManager = new EditDiscountCodeManager(null);
        return editDiscountCodeManager.editDiscount(matcher.group(1), matcher.group(2), matcher.group(3)) + "";
    }

    private static String editAuction(Matcher matcher) {
        ViewOffsManager viewOffsManager = new ViewOffsManager(null);
        viewOffsManager.editOffAttribute(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4));
        return "0";
    }

    private static String editProduct(Matcher matcher) {
        ManageProductsManager manageProductsManager = new ManageProductsManager(null);
        manageProductsManager.editProduct(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4));
        return "0";
    }

    private static String editCategory(Matcher matcher) {
        EditCategoryManager editCategoryManager = new EditCategoryManager(null);
        return editCategoryManager.editCategory(matcher.group(1), matcher.group(2), matcher.group(3)) + "";
    }

    private static String addCategory(Matcher matcher) {
        String[] inputs = matcher.group(1).split("&&&");
        ManageCategoriesManager manageCategoriesManager = new ManageCategoriesManager(null);
        return manageCategoriesManager.addCategory(inputs[0], inputs[1], Gson.INSTANCE.get().fromJson(inputs[2],
                new TypeToken<HashMap<String, Integer>>() {
                }.getType()), new ArrayList<>()) + "";
    }

    private static String getAllProducts() {
        return Gson.INSTANCE.get().toJson(Product.getAllProducts());
    }

    private static String loadProducts(Matcher matcher) {
        ProductsManager productsManager;
        if (matcher.group(1).equals("true"))
            productsManager = new ProductsManager(null, true);
        else
            productsManager = new ProductsManager(null, false);
        return Gson.INSTANCE.get().toJson((productsManager.loadProducts()));
    }

    private static String hasProductInCart(Matcher matcher) {
        Account account = Account.getAccountByUsername(matcher.group(1));
        ProductPageManager productPageManager = new ProductPageManager(account, matcher.group(2));
        return productPageManager.hasProductInCart() + "";
    }

    private static String addProductToCart(Matcher matcher) {
        Account account = Account.getAccountByUsername(matcher.group(1));
        ProductPageManager productPageManager = new ProductPageManager(account, matcher.group(2));
        return productPageManager.addToCart() + "";
    }

    private static String rateProduct(Matcher matcher) {
        ProductPageManager productPageManager = new ProductPageManager(null, matcher.group(1));
        return productPageManager.rateProduct(matcher.group(1), Integer.parseInt(matcher.group(2)));
    }

    private static String createProfileManager(Matcher matcher) {
        ManageUsersManager manageUsersManager = new ManageUsersManager(null);
        return manageUsersManager.createManagerProfile(Gson.INSTANCE.get().fromJson(matcher.group(1),
                new TypeToken<ArrayList<String>>() {
                }.getType())) + "";
    }

    private static String addComment(Matcher matcher) {
        Account account = Account.getAccountByUsername(matcher.group(1));
        ProductPageManager productPageManager = new ProductPageManager(account, matcher.group(2));
        ArrayList<String> inputs = new ArrayList<>(Gson.INSTANCE.get().fromJson(matcher.group(3),
                new TypeToken<ArrayList<String>>() {
                }.getType()));
        productPageManager.addComment(inputs.get(0), inputs.get(1));
        return "0";
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

    private static String getSellerAuctions(Matcher matcher) {
        Seller seller = (Seller) Seller.getAccountByUsername(matcher.group(1));
        return Gson.INSTANCE.get().toJson(seller.getAuctions());
    }

    private static String getSellerProducts(Matcher matcher) {
        Seller seller = (Seller) Seller.getAccountByUsername(matcher.group(1));
        return Gson.INSTANCE.get().toJson(Product.getProductsBySeller(seller));
    }

    private static String getAuctionProducts(Matcher matcher) {
        Auction auction = Auction.getAuctionById(matcher.group(1));
        return Gson.INSTANCE.get().toJson(auction.getProducts());
    }

    private static String getProductBuyers(Matcher matcher) {
        Product product = Product.getProductById(matcher.group(1));
        return Gson.INSTANCE.get().toJson(product.getAllBuyers());
    }

    private static String getCategoryByName(Matcher matcher) {
        Category category = Category.getCategoryByName(matcher.group(1));
        return Gson.INSTANCE.get().toJson(category);
    }

    private static String getProductPrice(Matcher matcher) {
        Product product = Product.getProductById(matcher.group(1));
        return product.getAuctionedPrice() + "";
    }

    private static String getProductRate(Matcher matcher) {
        Product product = Product.getProductById(matcher.group(1));
        return product.getRate() + "";
    }

    private static String getProductComments(Matcher matcher) {
        ProductPageManager productPageManager = new ProductPageManager(null, matcher.group(1));
        return Gson.INSTANCE.get().toJson(productPageManager.commentsFXML());
    }

    private static String getProductFeatures(Matcher matcher) {
        Product product = Product.getProductById(matcher.group(1));
        return Gson.INSTANCE.get().toJson(product.getFeatures());
    }

    private static String getProductAuction(Matcher matcher) {
        Product product = Product.getProductById(matcher.group(1));
        return Gson.INSTANCE.get().toJson(product.getAuction());
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

    private static String getTotalAmount(String username, String discountId) {
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
        Account account = Account.getAccountByUsername(matcher.group(1));
        if (account instanceof Principal) {
            return "P&&&" + Gson.INSTANCE.get().toJson(account);
        } else if (account instanceof Customer) {
            return "C&&&" + Gson.INSTANCE.get().toJson(account);
        } else if (account instanceof Seller) {
            return "S&&&" + Gson.INSTANCE.get().toJson(account);
        }
        return null;
    }

    private static String deleteAccount(Matcher matcher) {
        ManageUsersManager manageUsersManager = new ManageUsersManager(null);
        return manageUsersManager.deleteUsername(matcher.group(1), matcher.group(2)) + "";
    }

    private static String deleteProduct(Matcher matcher) {
        ManageAllProductsManager manageUsersManager = new ManageAllProductsManager(null);
        return manageUsersManager.removeProductById(matcher.group(1)) + "";
    }

    private static String deleteProductBySeller(Matcher matcher) {
        SellerManager sellerManager = new SellerManager(null);
        sellerManager.deleteProductById(matcher.group(1), matcher.group(2));
        return "0";
    }

    private static String deleteDiscount(Matcher matcher) {
        ViewDiscountCodesManager viewDiscountCodesManager = new ViewDiscountCodesManager(null);
        return viewDiscountCodesManager.deleteDiscountCode(matcher.group(1)) + "";
    }

    private static String login(Matcher matcher) {
        LoginToExistingAccountManager l = new LoginToExistingAccountManager(null);
        return l.login(matcher.group(1), matcher.group(2));
    }

    private static String logout(Matcher matcher) {
        LoginToExistingAccountManager l = new LoginToExistingAccountManager(null);
        return l.logout(matcher.group(1));
    }

    private static String register(Matcher matcher) {
        CreateNewAccountManager createNewAccountManager = new CreateNewAccountManager(null);
        ArrayList<String> inputs = new ArrayList<>(Gson.INSTANCE.get().fromJson(matcher.group(3),
                new TypeToken<ArrayList<String>>() {
                }.getType()));
        return createNewAccountManager.createNewAccount(matcher.group(1), matcher.group(2), inputs) + "";
    }

    private static String isPrincipalExists() {
        if (Principal.isPrincipalExists()) {
            return "true";
        } else return "false";
    }

    private static Matcher getMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }
}
