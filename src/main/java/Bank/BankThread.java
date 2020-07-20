package Bank;

import java.io.*;
import java.net.Socket;

public class BankThread extends Thread{

    private BankServer server;
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public BankThread(BankServer server, Socket socket) {
        this.server = server;
        this.socket = socket;
        try {
            this.dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            this.dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String command = "";
        while (true) {
            try {
                command = dataInputStream.readUTF();
                if (command.equals("exit")) {
                    break;
                }
                if (command.equals("exit_all")) {
                    System.exit(1);
                }
                dataOutputStream.writeUTF(server.handleCommand(command));
                dataOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
