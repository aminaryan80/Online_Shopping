package View;

public class MainMenu extends CommandProcessor {
    protected void editAttribute(String field) {
        if (manager.isEnteredAccountFieldValid()) {
            manager.editAccountAttribute(newAttribute)
        }
    }

    protected void viewPersonalInfo() {

    }
}
