package Models.Account;

import java.io.*;
import java.net.Socket;

public class Principal extends Account {

    public Principal(String username, String firstName, String lastName, String email, String phoneNumber, String password) {
        super(username, firstName, lastName, email, phoneNumber, password, 0);
    }

    @Override
    protected void createBankAccount(Account account) {
        String myBankId = getTheBankId();
        if(getTheBankId() == null) {
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
        } else {
            this.setBankId(myBankId);
        }
    }

    public static String getTheBankId() {
        for (Account account : allAccounts) {
            if (account instanceof Principal)
                if(account.getBankId()!=null) {
                    return account.getBankId();
                }
        }
        return null;
    }

    public static String shopBankId(){
        for (Account account : allAccounts) {
            if (account instanceof Principal)
                return account.getBankId();
        }
        return null;
    }

    public static boolean isPrincipalExists() {
        for (Account account : allAccounts) {
            if (account instanceof Principal)
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return username + " : \n" +
                "firstName = " + firstName + '\n' +
                "lastName = " + lastName + '\n' +
                "email = " + email + '\n' +
                "phoneNumber = " + phoneNumber + '\n' +
                "password = " + password;
    }
}
