import Control.MainManager;
import Control.Manager;

public class Main {
    public static void main(String[] args) {
        MainManager manager = new MainManager(null);
        manager.getMenu().execute();
    }
}