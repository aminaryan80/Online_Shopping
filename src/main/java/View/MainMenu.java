package View;

import Control.Manager;

public class MainMenu extends Menu {

    public MainMenu(Manager manager) {
        super(manager);
    }

    public void execute(){
        String input;
        while((input = scanner.nextLine().trim()).equalsIgnoreCase("exit")) {
            if(ConsoleCommand.EXIT.getStringMatcher(input).find()){
                return;
            }
        }
    }

    public void mainMenu() {

    }

    private void openLoginMenu() {

    }

    private void openProductsMenu() {

    }

    private void openAuctionsMenu() {

    }

    protected void viewPersonalInfo() {

    }

    protected void editAttribute(String field) {

    }
}
