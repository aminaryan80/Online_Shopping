package View;

import Control.MainManager;
import Control.Manager;

import java.util.Scanner;

public abstract class Menu {
    protected Manager manager;
    protected Scanner scanner = new Scanner(System.in);

    public Menu(Manager manager) {
        this.manager = manager;
    }

    public abstract void execute();
}
