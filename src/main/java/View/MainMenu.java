package View;

import Control.MainManager;
import Control.Manager;
import Models.Shop.Category;

public class MainMenu extends Menu {

    public MainMenu(Manager manager) {
        super(manager);
        mainMenu();
    }

    private void mainMenu() {
        while (true) {
            String input = scanner.nextLine();
            if (getMatcher(input, "^dashboard$").find()) {
                //TODO
            } else if (getMatcher(input, "^products$").find()) {
                // TODO
            } else if (getMatcher(input, "^offs$").find()) {
                //TODO
            } else if (getMatcher(input, "^help$").find()) {
                help();
            } else if (getMatcher(input, "^exit$").find()) {
                return;
            } else ErrorProcessor.invalidInput();
        }
    }

    private void help() {
        System.out.println("dashboard\n" +
                "products\n" +
                "offs\n" +
                "help\n" +
                "exit");
    }
}
