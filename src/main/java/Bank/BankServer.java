package Bank;

import Models.Gson;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class BankServer {

    public BankServer() {
        run();
    }

    private static void save(){
        try {
            Account.save();
            Receipt.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        loadData();
        Runtime.getRuntime().addShutdownHook(new Thread(BankServer::save));
        new BankServer();
    }

    private static void loadData() {
        try {
            Account.open();
            Receipt.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void run() {
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            serverSocket = new ServerSocket(5555);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            new BankThread(this, socket).run();
        }
    }

    public String handleCommand(String command) {
        if (command.matches("create_account \\S+ \\S+ \\S+ \\S+ \\S+")) {
            return createAccount(command.split(" "));
        } else if (command.matches("get_token \\S+ \\S+")) {
            return getToken(command.split(" "));
        } else if (command.matches("create_receipt \\S+ \\S+ \\S+ \\S+ \\S+ (\\S+)?")) {
            return createReceipt(command.split(" "));
        } else if (command.matches("get_transactions \\S+ \\S+")) {
            return getTransactions(command.split(" "));
        } else if (command.matches("pay \\S+")) {
            return pay(command.split(" ")[1]);
        } else if (command.matches("get_balance \\S+")) {
            return getBalance(command.split(" ")[1]);
        } else {
            return "invalid input";
        }
    }

    private String getBalance(String token) {
        if (!Token.isThereToken(token)) {
            return "token is invalid";
        }
        Token token1 = Token.getToken(token);
        if (token1.isExpired()) {
            return "token expired";
        }
        return String.valueOf(token1.getAccount().getMoney());
    }

    private synchronized String pay(String id) {
        if (!Receipt.isThereReceiptWithId(id)) {
            return "invalid receipt id";
        }
        Receipt receipt = Receipt.getReceiptById(id);
        if (receipt.isPaid()) {
            return "receipt is paid before";
        }
        return receipt.pay();
    }

    private String getTransactions(String[] data) {
        if (!Token.isThereToken(data[1])) {
            return "token is invalid";
        }
        Token token = Token.getToken(data[1]);
        if (token.isExpired()) {
            return "token expired";
        }
        ArrayList<Receipt> validReceipts = Receipt.getTransactions(Token.getToken(data[1]).getAccount(), data[2]);
        if (data[2].length() > 1 && validReceipts.size() == 0) {
            return "invalid receipt id";
        }
        StringBuilder respond = new StringBuilder("");
        for (Receipt validReceipt : validReceipts) {
            respond.append(Gson.INSTANCE.get().toJson(validReceipt) + "*");
        }
        if (respond.length() != 0) {
            respond.deleteCharAt(respond.length() - 1);
        }
        return respond.toString();
    }

    private String createReceipt(String[] data) {
        if (!data[2].equals("move") && !data[2].equals("deposit") && !data[2].equals("withdraw")) {
            return "invalid receipt type";
        }
        if (!data[3].matches("\\d+")) {
            return "invalid money";
        }
        if (!data[4].matches("\\d+") && !data[5].matches("\\d+")) {
            return "invalid parameters passed";
        }
        if (!Token.isThereToken(data[1])) {
            return "token is invalid";
        }
        Token token = Token.getToken(data[1]);
        if (token.isExpired()) {
            return "token expired";
        }
        if (!Account.isThereAccountWithId(data[4]) && !data[4].equals("-1")) {
            return "source account id is invalid";
        }
        if (!Account.isThereAccountWithId(data[5]) && !data[5].equals("-1")) {
            return "dest account id is invalid";
        }
        if (data[4].equals(data[5])) {
            return "equal source and dest account";
        }
        if (data[2].equals("move")) {
            if (data[4].equals("-1") || data[5].equals("-1")) {
                return "invalid account id";
            }
            if (!data[4].equals(Token.getToken(data[1]).getAccount().getId())) {
                return "token is invalid";
            }
        } else if (data[2].equals("withdraw")) {
            if (data[4].equals("-1")) {
                return "invalid account id";
            }
            if (!data[4].equals(Token.getToken(data[1]).getAccount().getId())) {
                return "token is invalid";
            }
        } else {
            if (data[5].equals("-1")) {
                return "invalid account id";
            }
        }
        if (data.length == 7 && !data[6].matches("\\w+")) {
            return "your input contains invalid characters";
        }
        return new Receipt(data[2], data[3], data[4], data[5], data[6]).getId();
    }

    private String getToken(String[] data) {
        if (!Account.isThereAccountWithName(data[1])) {
            return "invalid username";
        }
        if (!Account.isPasswordCorrect(data[1], data[2])) {
            return "invalid password";
        }
        return new Token(Account.getAccountByName(data[1])).getToken();
    }

    private synchronized String createAccount(String[] data) {
        if (!data[4].equals(data[5])) {
            return "passwords do not match";
        }
        if (Account.isThereAccountWithName(data[3])) {
            return "username is not available";
        }
        return new Account(data[1], data[2], data[3], data[4]).getId();
    }
}
