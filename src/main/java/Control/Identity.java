package Control;

import Models.Address;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
            String id = UUID.randomUUID().toString().toUpperCase().substring(0, 8);
            if (!doesExist(id)) {
                identities.add(id);
                return id;
            }
        }
    }

    //TODO file
    public static void open() throws Exception {
        File file = new File(Address.IDNETITIES.get()+"\\" + "identities.json");
        if (!file.exists()) {
            File folder = new File(Address.IDNETITIES.get());
            folder.mkdirs();
            file.createNewFile();
        }
        else {
            identities.addAll(open(file));
        }
    }

    private static ArrayList<String> open(File file) throws FileNotFoundException {
        ArrayList<String> IDs = new ArrayList<>();
        Scanner reader = new Scanner(file);
        while (reader.hasNext()) {
            IDs.add(reader.nextLine());
        }
        reader.close();
        return IDs;
    }

    public static void save() throws IOException {
        for (String identity : identities) {
            save(identity);
        }
    }

    private static void save(String identity) throws IOException {
        FileWriter file = new FileWriter(Address.IDNETITIES.get() + "\\" + "identities.json");
        file.append("\n"+identity);
        file.close();
    }
}
