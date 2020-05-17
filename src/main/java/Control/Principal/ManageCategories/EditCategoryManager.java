package Control.Principal.ManageCategories;

import Models.Account.Account;
import Models.Shop.Category.Category;
import View.Principal.ManageCategories.EditCategoryMenu;

import java.util.ArrayList;

public class EditCategoryManager extends ManageCategoriesManager {

    Category category;
    ArrayList<String> features;

    public EditCategoryManager(Account account, Category category) {
        super(account);
        new EditCategoryMenu(this);
        this.category = category;
        this.features = category.getFeaturesNames();
    }

    public void editName(String newName) {
        category.setName(newName);
        category.changeCategoryNameForProducts();
    }

    public boolean hasFeatureWithName(String name) {
        return features.contains(name);
    }

    public void addFeature(String feature) {
        features.add(feature);
        category.addFeature(feature);
    }

    public void editFeature(String oldName, String newName) {
        features.remove(oldName);
        features.add(newName);
        category.editFeature(oldName, newName);
    }

    public void removeFeature(String feature) {
        features.remove(feature);
        category.removeFeature(feature);
    }
}
