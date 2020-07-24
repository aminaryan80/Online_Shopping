package Models.Account;

import java.io.*;
import java.net.Socket;

public class Wallet {
    private static double MINIMUM_AMOUNT = 5;
    private static double WAGE = 0.05;
    private static int BANK_PORT = 5555;
    private double amount;
    private String username;
    private String password;
    private String bankId;

    public Wallet(double amount, String username, String password, String bankId) {
        this.amount = amount;
        this.username = username;
        this.password = password;
        this.bankId = bankId;
    }

    public static void setMinimumAmount(double minimumAmount) {
        MINIMUM_AMOUNT = minimumAmount;
    }

    public static void setWAGE(double WAGE) {
        Wallet.WAGE = WAGE;
    }

    public static double getMinimumAmount() {
        return MINIMUM_AMOUNT;
    }

    public static double getWage() {
        return WAGE;
    }

    public void chargeWallet(double amount,String bankId){
        String token = getToken();
        try {
            Socket socket = new Socket("127.0.0.1", BANK_PORT);
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            dataOutputStream.writeUTF("create_receipt"+" "+token+" "+"withdraw"+" "+amount+" "+bankId+" "+"-1");
            dataOutputStream.flush();
            String receiptID = dataInputStream.readUTF();
            dataOutputStream.writeUTF("pay"+" "+receiptID);
            dataOutputStream.flush();
            if(dataInputStream.readUTF().equals("done successfully")) this.addAmount(amount);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void moveInBank(double amount,String otherBankId) {
        String token = getToken();
        try {
            Socket socket = new Socket("127.0.0.1", BANK_PORT);
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            dataOutputStream.writeUTF("create_receipt"+" "+token+" "+"move"+" "+amount+" "+bankId+" "+otherBankId);
            dataOutputStream.flush();
            String receiptID = dataInputStream.readUTF();
            dataOutputStream.writeUTF("pay"+" "+receiptID);
            dataOutputStream.flush();
            System.out.println(dataInputStream.readUTF());
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void moveInWallet(Wallet otherWallet,double amount) {
        if(this.getAmount()>amount) {
            this.addAmount(-amount);
            otherWallet.addAmount(amount);
        }
    }

    public void addAmount(double v) {
        amount += v;
    }

    public void withdrawFromWallet(double amount) {
        String token = getToken();
        try {
            Socket socket = new Socket("127.0.0.1", BANK_PORT);
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            dataOutputStream.writeUTF("create_receipt"+" "+token+" "+"deposit"+" "+amount+" "+"-1"+" "+bankId);
            dataOutputStream.flush();
            String receiptID = dataInputStream.readUTF();
            dataOutputStream.writeUTF("pay"+" "+receiptID);
            if(dataInputStream.readUTF().equals("done successfully")) this.addAmount(-amount);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getToken() {
        try {
            Socket socket = new Socket("127.0.0.1", BANK_PORT);
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            dataOutputStream.writeUTF("get_token"+" "+username+" "+password);
            dataOutputStream.flush();
            String response = dataInputStream.readUTF();
            socket.close();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public double getAmountInBank() {
        String token = getToken();
        try {
            Socket socket = new Socket("127.0.0.1", BANK_PORT);
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            dataOutputStream.writeUTF("get_balance"+" "+token);
            dataOutputStream.flush();
            String balance = dataInputStream.readUTF();
            if(balance!=null && !balance.isEmpty()) return Double.parseDouble(balance);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
