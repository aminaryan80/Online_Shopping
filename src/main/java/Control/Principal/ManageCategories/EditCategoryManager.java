package Control.Principal.ManageCategories;

import Control.Manager;
import Models.Account.Account;
import Models.Shop.Category.Category;

import java.util.ArrayList;

public class EditCategoryManager extends Manager {

    private Category editingCategory;
    private ArrayList<String> features;

    public EditCategoryManager(Account account, Category category) {
        super(account);
        this.editingCategory = category;
        this.features = category.getFeaturesNames();
        loadFxml(Addresses.EDIT_CATEGORY, true);
    }

    public void editName(String newName) {
        if (!Category.hasCategoryWithName(newName)) {
            editingCategory.editName(newName);
        } else error("Category exists with this name.");
    }

    public boolean hasFeatureWithName(String name) {
        return features.contains(name);
    }

    public void addFeature(String feature) {
        if (feature.equals("")) {
            if (!hasFeatureWithName(feature)) {
                features.add(feature);
                editingCategory.addFeature(feature);
            } else error("Category has feature with this name.");
        } else error("Invalid input");

    }

    public void editFeature(String oldName, String newName) {
        if (oldName.equals("") || newName.equals("")) {
            if (hasFeatureWithName(oldName)) {
                if (!hasFeatureWithName(newName)) {
                    features.remove(oldName);
                    features.add(newName);
                    editingCategory.editFeature(oldName, newName);
                } else error("Category has feature with this name.");
            } else error("Invalid feature name");
        } else error("Invalid input");
    }

    public void removeFeature(String feature) {
        if (feature.equals("")) {
            if (hasFeatureWithName(feature)) {
                features.remove(feature);
                editingCategory.removeFeature(feature);
            } else error("Invalid feature name");
        } else error("Invalid input");
    }

    public void editCategory(String featureName) {
        if (featureName.equals("name")) {
            openEditName();
        } else if (featureName.equals("feature")) {
            openEditFeatures();
        } else error("Invalid input");
    }

    private void openEditName() {
        loadFxml(Addresses.EDIT_NAME_CATEGORY, true);
    }

    private void openEditFeatures() {
        loadFxml(Addresses.EDIT_FEATURES_CATEGORY, true);
    }
}
