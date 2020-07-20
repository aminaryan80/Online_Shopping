package Client.Control.Principal.ManageCategories;

import Client.Control.Manager;
import Models.Account.Account;
import Models.Shop.Category.Category;

import java.util.ArrayList;

public class EditCategoryManager extends Manager {

    private Category editingCategory;
    private ArrayList<String> features;

    public EditCategoryManager(Account account) {
        super(account);
    }

    public EditCategoryManager(Account account, Category category) {
        super(account);
        this.editingCategory = category;
        this.features = category.getFeaturesNames();
        loadFxml(Addresses.EDIT_CATEGORY, true);
    }

    private boolean hasFeatureWithName(String name) {
        return features.contains(name);
    }

    public int editName(String newName) {
        if (!Category.hasCategoryWithName(newName)) {
            editingCategory.editName(newName);
            return 0;
        }
        return 1;
    }

    public int addFeature(String feature) {
        if (!feature.equals("")) {
            if (!hasFeatureWithName(feature)) {
                features.add(feature);
                editingCategory.addFeature(feature);
                return 0;
            }
        }
        return 1;
    }

    public int editFeature(String inputs) {
        String[] input = inputs.split(" ");
        String oldName = input[0];
        String newName = input[1];
        if (!oldName.equals("") && !newName.equals("")) {
            if (hasFeatureWithName(oldName)) {
                if (!hasFeatureWithName(newName)) {
                    features.remove(oldName);
                    features.add(newName);
                    editingCategory.editFeature(oldName, newName);
                    return 0;
                } else return 3;
            }else return 2;
        }else return 1;
    }

    public int removeFeature(String feature) {
        if (!feature.equals("")) {
            if (hasFeatureWithName(feature)) {
                features.remove(feature);
                editingCategory.removeFeature(feature);
                return 0;
            } else return 1;
        } else return 2;
    }

    public int editCategory(String featureName, String categoryName, String inputs) {
        if (Category.hasCategoryWithName(categoryName)) {
            editingCategory = Category.getCategoryByName(categoryName);
            features = editingCategory.getFeaturesNames();
            if (featureName.equals("EDIT_NAME")) {
                return editName(inputs);
            } else if (featureName.equals("ADD_FEATURE")) {
                return addFeature(inputs);
            } else if (featureName.equals("EDIT_FEATURE")) {
                return editFeature(inputs);
            } else if (featureName.equals("DELETE_FEATURE")) {
                return removeFeature(inputs);
            }
        }
        return 1;
    }

    private void openEditName() {
        loadFxml(Addresses.EDIT_NAME_CATEGORY, true);
    }

    private void openEditFeatures() {
        loadFxml(Addresses.EDIT_FEATURES_CATEGORY, true);
    }
}
