package Models.Account;

import java.io.*;
import java.net.Socket;

public class Wallet {
    private static double MINIMUM_AMOUNT;
    private static double WAGE;
    private static int BANK_PORT = 5555;
    private double amount;

    public static void setMinimumAmount(double minimumAmount) {
        MINIMUM_AMOUNT = minimumAmount;
    }

    public static void setWAGE(double WAGE) {
        Wallet.WAGE = WAGE;
    }

    public void chargeWallet(String username,String password,double amount,String bankId){
        String token = getToken(username,password);
        try {
            Socket socket = new Socket("127.0.0.1", BANK_PORT);
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            dataOutputStream.writeUTF("create_receipt"+" "+token+" "+"withdraw"+" "+amount+" "+"-1"+" "+bankId);
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

    public void moveInBank(String username,String password,double amount,String BankId,String otherBankId) {
        String token = getToken(username,password);
        try {
            Socket socket = new Socket("127.0.0.1", BANK_PORT);
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            dataOutputStream.writeUTF("create_receipt"+" "+token+" "+"move"+" "+amount+" "+BankId+" "+otherBankId);
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

    private void addAmount(double v) {
        amount += v;
    }

    public void withdrawFromWallet(String username,String password,double amount,String bankId) {
        String token = getToken(username,password);
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

    private String getToken(String username,String password) {
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
}
