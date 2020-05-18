package Control;

import Models.Address;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.Assert.*;

public class IdentityTest {

    @Test
    public void getId() {
        assertNotEquals(Identity.getId(),Identity.getId());
    }

    @Test
    public void open() {
        File folder = new File(Address.IDNETITIES.get());
        if(!folder.exists())
        assertTrue(folder.mkdirs());
        else assertFalse(folder.mkdirs());
        File file = (new File(Address.IDNETITIES.get()));
        try {
            if(!file.exists())
            assertTrue(file.createNewFile());
            else assertFalse(file.createNewFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Identity.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue((new File(Address.IDNETITIES.get()).exists()));
    }

    @Test
    public void save() {
        String id = Identity.getId();
        try {
            Identity.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File(Address.IDNETITIES.get() + "\\" + "identities.json");
        ArrayList<String> IDs = new ArrayList<>();
        Scanner reader = null;
        try {
            reader = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (reader.hasNext()) {
            IDs.add(reader.nextLine());
        }
        reader.close();
        assertTrue(IDs.contains(id));
    }
}