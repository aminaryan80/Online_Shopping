package View.Principal.ManageCategories;

import Control.Manager;
import Control.Principal.ManageCategories.EditCategoryManager;
import Control.Principal.ManageCategories.ManageCategoriesManager;
import Models.Shop.Category.Category;
import Models.Shop.Product.Product;
import View.ErrorProcessor;
import View.Menu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

public class ManageCategoriesMenu extends Menu {
    public ManageCategoriesMenu(Manager manager) {
        super(manager);
        manageCategoriesMenu();
    }

    private void manageCategoriesMenu() {
        showCategories();
        while (true) {
            Matcher matcher;
            String input = scanner.nextLine();
            if ((matcher = getMatcher(input, "^edit (\\S+)$")).find()) {
                editCategory(matcher.group(1));
            } else if ((matcher = getMatcher(input, "^add (\\S+)$")).find()) {
                addCategory(matcher.group(1));
            } else if ((matcher = getMatcher(input, "^remove (\\S+)$")).find()) {
                deleteCategory(matcher.group(1));
            } else if (getMatcher(input, "^help$").find()) {
                help();
            } else if (getMatcher(input, "^back$").find()) {
                return;
            } else ErrorProcessor.invalidInput();
        }
    }

    private void showCategories() {
        System.out.println(manager.showCategories());
    }

    private void editCategory(String categoryName) {
        if (!Category.hasCategoryWithName(categoryName)) {
            ErrorProcessor.invalidCategoryName();
        } else
            new EditCategoryManager(manager.getAccount(), Category.getCategoryByName(categoryName));
    }

    private void addCategory(String categoryName) {
        showCategories();
        System.out.println("In which category you want to add a new one?"); // this should change //
        String supCategoryName = scanner.nextLine();
        if (((ManageCategoriesManager) manager).canAddCategory(supCategoryName, categoryName)) {
            ((ManageCategoriesManager) manager).addCategory(supCategoryName, categoryName,
                    getFeaturesToAddCategory(), getProductsNamesToAddCategory());
        }
    }

    private HashMap<String, Integer> getFeaturesToAddCategory() {
        System.out.println("Enter category feature in this format:(Enter 0 to stop)\n" +
                "[Feature name]\n" +
                "[Feature type](text|number)");
        HashMap<String, Integer> features = new HashMap<>();
        while (true) {
            String featureName = scanner.nextLine();
            if (featureName.equals("0"))
                break;
            String featureType = scanner.nextLine();
            if (featureType.equals("0"))
                break;
            if (featureType.matches("^(text|number)$")) {
                features.put(featureName, featureType.equals("text") ? 1 : 0);
            } else ErrorProcessor.invalidInput();
        }
        return features;
    }

    private ArrayList<String> getProductsNamesToAddCategory() {
        System.out.println("Enter product id:(Enter 0 to stop)");
        ArrayList<String> productsNames = new ArrayList<>();
        while (true) {
            String productId = scanner.nextLine();
            if (productId.equals("0"))
                break;
            if (Product.getProductById(productId) != null) {
                productsNames.add(productId);
            }
            ErrorProcessor.invalidProductId();
        }
        return productsNames;
    }

    private void deleteCategory(String categoryName) {
        if(((ManageCategoriesManager) manager).canDeleteCategory(categoryName)) {
            try {
                ((ManageCategoriesManager) manager).deleteCategory(categoryName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void help() {
        System.out.println("add [category]\n" +
                "edit [category]\n" +
                "remove [category]\n" +
                "help\n" +
                "back");
    }
}
