package Client;

import Models.Account.Account;
import Models.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args) throws IOException {
        /*File file = new File(Test.class.getClassLoader().getResource("images/product.jpg").getFile());
        System.out.println(file.getPath());
        File file2 = new File(getAddress("fuck3.png"));
        FileUtils.copyFile(file, file2);*/

        //System.out.println(sendRequest("Hello. I'm working."));

        String response = sendRequest("GET_ALL_ACCOUNTS");
        System.out.println(response);
        // it goes like this:
        ArrayList<Account> userArray = Gson.INSTANCE.get().fromJson(response,new TypeToken<ArrayList<Account>>(){}.getType());
        for (Account account : userArray) {
            System.out.println(account.getUsername());
        }
    }

    private static String getAddress(String fileName) {
        String address = String.valueOf(Test.class.getClassLoader().getResource("images/product.jpg"));
        address = address.replace("file:/", "");
        address = address.replace("product.jpg", "products/");
        address = address + fileName;
        address = address.replace("/", "\\");
        return address;
    }

    public static String sendRequest(String request) {
        Socket socket;
        DataInputStream input;
        DataOutputStream output;
        String response = null;
        try {
            socket = new Socket("localhost", 8080);
            System.out.println("Connected");
            // takes input from terminal
            input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            output = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            // Sends request and Receives response
            output.writeUTF(request);
            output.flush();
            do {
                response = input.readUTF();
            } while (response == null);
            // close the connection
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}