package Models.Account;

import Models.Shop.Cart;
import Models.Shop.Log.BuyingLog;
import Models.Shop.Off.Discount;
import Models.Shop.Product.Product;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Customer extends Account {
    private Cart cart;
    private ArrayList<BuyingLog> allLogs;
    private ArrayList<String> discountsIds;
    private Wallet wallet;

    public Customer(String username, String firstName, String lastName, String email, String phoneNumber, String password, double balance) {
        super(username, firstName, lastName, email, phoneNumber, password, balance);
        this.cart = new Cart();
        this.allLogs = new ArrayList<>();
        discountsIds = new ArrayList<>();
        wallet = new Wallet(balance, username, password, myBankId);
    }

    public void payMoney(double money,String payingMethod) {
        if(payingMethod.equals("credit")){
            wallet.addAmount(-money);
        } else {
            wallet.moveInBank(money,Principal.getTheBankId());
        }
    }

    @Override
    protected void createBankAccount(Account account) {
        try {
            Socket socket = new Socket("127.0.0.1", BANK_PORT);
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            dataOutputStream.writeUTF("create_account" + " " + firstName + " " + lastName + " " + username + " " + password + " " + password);
            dataOutputStream.flush();
            String bankId = dataInputStream.readUTF();
            this.setBankId(bankId);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteProductFromCarts(Product product) {
        for (Account account : allAccounts) {
            try {
                if (((Customer) account).hasProductById(product.getId())) {
                    ((Customer) account).getCart().removeProduct(product);
                }
            } catch (Exception ignored) {

            }
        }
    }

    public Wallet getWallet() {
        return wallet;
    }

    public ArrayList<BuyingLog> getAllLogs() {
        return allLogs;
    }

    public void addDiscount(Discount discount) {
        if (discount != null)
            discountsIds.add(discount.getId());
    }

    public void deleteDiscount(Discount discount) {
        discountsIds.remove(discount.getId());
    }

    public BuyingLog getLogById(String id) {
        for (BuyingLog log : allLogs) {
            if (log.getId().equals(id)) {
                return log;
            }
        }
        return null;
    }

    public Cart getCart() {
        return cart;
    }

    public boolean hasProductById(String id) {
        for (Product product : cart.getProducts()) {
            if (product.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public void addLog(BuyingLog buyingLog) {
        allLogs.add(buyingLog);
    }

    @Override
    public String toString() {
        return username + " : \n" +
                "firstName = " + firstName + '\n' +
                "lastName = " + lastName + '\n' +
                "email = " + email + '\n' +
                "phoneNumber = " + phoneNumber + '\n' +
                "password = " + password + '\n' +
                "balance = " + balance;
    }

    public boolean hasBoughtProduct(String productId) {
        for (BuyingLog log : allLogs) {
            for (String boughtProductId : log.getProductIdToNumberMap().keySet()) {
                if (productId.equals(boughtProductId)) return true;
            }
        }
        return false;
    }
}
