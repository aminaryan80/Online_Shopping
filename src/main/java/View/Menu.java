package View;

import Control.MainManager;
import Control.Manager;

import java.util.Scanner;

public class Menu {
    protected Manager manager;
    protected Scanner scanner = new Scanner(System.in);

    public Menu(Manager manager) {
        this.manager = manager;
    }

    public void execute(){
        MainManager mainManager = new MainManager(null);
        mainManager.getMenu().execute();
    }
}
