package View;

public class MainMenu extends CommandProcessor {
    protected void viewPersonalInfo() {

    }

    protected void editAttribute(String field) {
        if (manager.isEnteredAccountFieldValid()) {
            manager.editAccountAttribute(newAttribute)
        }
    }
}
