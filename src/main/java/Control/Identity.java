package Control;

import Models.Address;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class Identity {
    private String id = "";
    private static ArrayList<String> identities = new ArrayList<String>();

    private static boolean doesExist(String id) {
        for (String identity : identities) {
            if (id.equals(identity))
                return true;
        }
        return false;
    }

    public static String getId() {
        while (true) {
            String id = UUID.randomUUID().toString().substring(0, 8);
            if (!doesExist(id)) {
                identities.add(id);
                return id;
            }
        }
    }

    //TODO file
    public static void open() throws Exception {
        File file = new File(Address.DISCOUNTS.get() + "\\" + "identities");
        if (!file.exists()) file.createNewFile();
        else {
            identities.addAll(open(file));
        }
    }

    private static ArrayList<String> open(File file) throws FileNotFoundException {
        ArrayList<String> IDs = new ArrayList<>();
        Scanner reader = new Scanner(file);
        if (reader.hasNext()) {
            IDs.add(reader.nextLine());
        }
        return IDs;
    }

    public static void save() throws IOException {
        for (String identity : identities) {
            save(identity);
        }
    }

    private static void save(String identity) throws IOException {
        FileWriter file = new FileWriter(Address.DISCOUNTS.get() + "\\" + "identities");
        file.append("\n"+identity);
        file.close();
    }


}
