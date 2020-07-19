package Bank;

import Models.Gson;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Receipt {

    private String sourceId;
    private String destId;
    private String description;
    private String type;
    private int money;
    private String id;
    private boolean paid;
    private static Random random = new Random();
    private static ArrayList<Receipt> receipts = new ArrayList<>();

    public Receipt(String type, String money, String sourceId, String destId, String description) {
        this.type = type;
        this.description = description;
        this.destId = destId;
        this.sourceId = sourceId;
        this.money = Integer.parseInt(money);
        this.paid = false;
        this.id = String.valueOf(random.nextInt(1000000));
        receipts.add(this);
    }

    public String getId() {
        return id;
    }

    public static ArrayList<Receipt> getTransactions(Account account, String type) {
        ArrayList<Receipt> validReceipts = new ArrayList<>();
        for (Receipt receipt : receipts) {
            if (type.equals("+")) {
                if (receipt.destId.equals(account.getId())) {
                    validReceipts.add(receipt);
                }
            } else if (type.equals("-")) {
                if (receipt.sourceId.equals(account.getId())) {
                    validReceipts.add(receipt);
                }
            } else if (type.equals("*")) {
                if (receipt.sourceId.equals(account.getId()) || receipt.destId.equals(account.getId())) {
                    validReceipts.add(receipt);
                }
            } else {
                if (isThereReceiptWithId(type) && (getReceiptById(type).destId.equals(account.getId()) || getReceiptById(type).sourceId.equals(account.getId()))) {
                    validReceipts.add(receipt);
                }
            }
        }
        return validReceipts;
    }

    public static boolean isThereReceiptWithId(String id) {
        for (Receipt receipt : receipts) {
            if (receipt.id.equals(id)) {
                return true;
            }
        }
        return false;
    }

    public static Receipt getReceiptById(String id) {
        for (Receipt receipt : receipts) {
            if (receipt.id.equals(id)) {
                return receipt;
            }
        }
        return null;
    }

    public boolean isPaid() {
        return paid;
    }

    public String pay() {
        if (type.equals("move")) {
            if (!Account.isThereAccountWithId(sourceId) || !Account.isThereAccountWithId(destId)) {
                return "invalid account id";
            }
            Account source = Account.getAccountById(sourceId);
            if (source.getMoney() < money) {
                return "source account does not have enough money";
            }
            source.pay(-money);
            Account.getAccountById(destId).pay(money);
        } else if (type.equals("withdraw")) {
            if (!Account.isThereAccountWithId(sourceId)) {
                return "invalid account id";
            }
            Account source = Account.getAccountById(sourceId);
            if (source.getMoney() < money) {
                return "source account does not have enough money";
            }
            source.pay(-money);
        } else {
            if (!Account.isThereAccountWithId(destId)) {
                return "invalid account id";
            }
            Account.getAccountById(destId).pay(money);
        }
        paid = true;
        return "done successfully";
    }

    public static void open() throws Exception {
        File folder = new File("BankDataBase" + "\\" + "Receipts");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        else {
            for (File file : folder.listFiles()) {
                receipts.add(open(file));
            }
        }
    }

    public static Receipt open(File file) throws Exception {
        StringBuilder json = new StringBuilder();
        Scanner reader = new Scanner(file);
        while (reader.hasNext()) {
            json.append(reader.next());
        }
        reader.close();
        return Gson.INSTANCE.get().fromJson(json.toString(), Receipt.class);
    }

    public static void save() throws Exception {
        for (Receipt receipt: receipts) {
            save(receipt);
        }
    }

    public static void save(Receipt receipt) throws Exception {
        String jsonAccount = Gson.INSTANCE.get().toJson(receipt);
        FileWriter file = new FileWriter("BankDataBase" + "\\" + "Receipts"+ "\\" + receipt.getId() + ".json");
        file.write(jsonAccount);
        file.close();
    }
}
