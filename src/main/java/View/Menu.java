package View;

import Control.MainManager;
import Control.Manager;

import java.util.Scanner;
import java.util.regex.Matcher;

public class Menu {
    protected Manager manager;
    protected Scanner scanner = new Scanner(System.in);

    public Menu(Manager manager) {
        this.manager = manager;
    }

    public void start(){
        Manager mainManager = new MainManager(null);
        mainManager.getMenu().start();
    }
}
