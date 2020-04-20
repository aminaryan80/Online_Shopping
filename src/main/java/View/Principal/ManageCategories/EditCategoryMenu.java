package View.Principal.ManageCategories;

import Control.Manager;
import View.ErrorProcessor;
import View.MainMenu;

public class EditCategoryMenu extends MainMenu {
    public EditCategoryMenu(Manager manager) {
        super(manager);
        editCategory();
    }

    private void editCategory() {
        System.out.println("Enter field:");
        showFilters();
        String input = scanner.nextLine();
        switch (input) {
            case "beginning date":

                break;
            case "ending date":

                break;
            case "discount percent":

                break;
            case "maximum amount":

                break;
            case "discount user count":

                break;
            default:
                ErrorProcessor.invalidInput();
        }
    }

    private void showFilters() {
        System.out.println("beginning date\n" +
                "ending date\n" +
                "discount percent\n" +
                "maximum amount\n" +
                "discount user count");
    }

}
