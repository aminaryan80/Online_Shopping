package Bank;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Test {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 5555);
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String command = scanner.nextLine();
                dataOutputStream.writeUTF(command);
                dataOutputStream.flush();
                if (command.equals("exit") || command.equals("exit_all")) {
                    break;
                }
                String respond = dataInputStream.readUTF();
                System.out.println(respond);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
