package Control;

import java.util.ArrayList;
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
}
