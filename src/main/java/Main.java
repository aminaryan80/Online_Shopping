import Control.Manager;
import View.Menu;

public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager(null);
        manager.getMenu().start();
    }
}