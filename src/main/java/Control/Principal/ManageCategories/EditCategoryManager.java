package Control.Principal.ManageCategories;

import Control.Manager;
import Models.Account.Account;
import Models.Shop.Category.Category;
import View.Principal.ManageCategories.EditCategoryMenu;

import java.util.ArrayList;

public class EditCategoryManager extends Manager {

    private Category editingCategory;
    private ArrayList<String> features;

    public EditCategoryManager(Account account, Category category) {
        super(account);
        this.editingCategory = category;
        this.features = category.getFeaturesNames();
        new EditCategoryMenu(this);
    }

    public void editName(String newName) {
        editingCategory.editName(newName);
    }

    public boolean hasFeatureWithName(String name) {
        return features.contains(name);
    }

    public void addFeature(String feature) {
        features.add(feature);
        editingCategory.addFeature(feature);
    }

    public void editFeature(String oldName, String newName) {
        features.remove(oldName);
        features.add(newName);
        editingCategory.editFeature(oldName, newName);
    }

    public void removeFeature(String feature) {
        features.remove(feature);
        editingCategory.removeFeature(feature);
    }
}
