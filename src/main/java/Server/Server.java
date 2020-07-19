package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    //AccountRequestsProcessor accountRequestsProcessor = new AccountRequestsProcessor();

    public Server() {
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
                String request = "";
                // reads message
                request = input.readUTF();
//                System.out.println(line + "\n");
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

    private String processRequest(String request) {
        if (request.equals("GET_ALL_ACCOUNTS")) {
            //return accountRequestsProcessor.getAllAccounts();
        }
        return null;
    }
}
