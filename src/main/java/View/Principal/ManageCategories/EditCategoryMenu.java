package View.Principal.ManageCategories;

import Control.Manager;
import Control.Principal.ManageCategories.EditCategoryManager;
import Models.Shop.Category.Category;
import View.ErrorProcessor;

import java.util.regex.Matcher;

public class EditCategoryMenu extends ManageCategoriesMenu {
    public EditCategoryMenu(Manager manager) {
        super(manager);
        editCategory();
    }

    private void editCategory() {
        System.out.println("Enter field:");
        showFilters();
        String input = scanner.nextLine();
        switch (input) {
            case "name":
                editName();
                break;
            case "features":
                editFeatures();
                break;
            default:
                ErrorProcessor.invalidInput();
        }
    }

    private void editName() {
        String newName;
        while (true) {
            System.out.println("enter the new name");
            newName = scanner.nextLine();
            if (Category.hasCategoryWithName(newName)) {
                ErrorProcessor.invalidCategoryName();
            } else {
                break;
            }
        }
        ((EditCategoryManager) manager).editName(newName);
    }

    private void editFeatures() {
        while (true) {
            Matcher matcher;
            String command = scanner.nextLine();
            if (command.equals("add feature")) {
                addFeature();
            } else if ((matcher = getMatcher(command, "edit (\\S+) name")).find()) {
                editFeature(matcher.group(1));
            } else if ((matcher = getMatcher(command, "remove (\\S+)")).find()) {
                removeFeature(matcher.group(1));
            } else if (command.equals("help")) {
                help();
            } else if (command.equals("back")) {
                return;
            } else {
                ErrorProcessor.invalidInput();
            }
        }
    }

    private void help() {
        System.out.println("add feature\n" +
                "edit [feature] name\n" +
                "remove [feature]\n" +
                "help\n" +
                "back");
    }

    private void removeFeature(String feature) {
        if (!((EditCategoryManager) manager).hasFeatureWithName(feature)) {
            ErrorProcessor.invalidInput();
        } else {
            ((EditCategoryManager) manager).removeFeature(feature);
        }
    }

    private void addFeature() {
        System.out.println("enter the name of the feature");
        String feature = scanner.nextLine();
        if (((EditCategoryManager) manager).hasFeatureWithName(feature)) {
            ErrorProcessor.invalidInput();
        } else {
            ((EditCategoryManager) manager).addFeature(feature);
        }
    }

    private void editFeature(String feature) {
        if (!((EditCategoryManager) manager).hasFeatureWithName(feature)) {
            ErrorProcessor.invalidInput();
            return;
        }
        System.out.println("enter the new name");
        String newName = scanner.nextLine();
        if (((EditCategoryManager) manager).hasFeatureWithName(newName)) {
            ErrorProcessor.invalidInput();
            return;
        }
        ((EditCategoryManager) manager).editFeature(feature, newName);
    }

    private void showFilters() {
        System.out.println("name\n" +
                "features");
    }

}
