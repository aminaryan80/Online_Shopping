package Control.Principal.ManageCategories;

import Control.Manager;
import Models.Account.Account;
import Models.Shop.Category.Category;
import ViewController.Controller;
import ViewController.principal.manageCategories.AddCategoryController;
import ViewController.principal.manageCategories.ManageCategoriesController;

import java.util.ArrayList;
import java.util.HashMap;

public class ManageCategoriesManager extends Manager {
    public ManageCategoriesManager(Account account, Addresses address, Manager manager) {
        super(account, address, manager);
        Controller controller = loadFxml(Addresses.MANAGE_CATEGORIES);
        update(controller);
    }

    public void update(Controller c) {
        ManageCategoriesController controller = (ManageCategoriesController) c;
        controller.init();
    }

    public void addCategory(String supCategoryName, String categoryName, HashMap<String, Integer> features, ArrayList<String> productsIds) {
        if (canAddCategory(supCategoryName, categoryName)) {
            new Category(categoryName, supCategoryName, features, productsIds);
            success("Category created successfully.");
        }
    }


    public boolean canAddCategory(String supCategoryName, String categoryName) {
        if (Category.hasCategoryWithName(supCategoryName)) {
            if (!Category.getCategoryByName(supCategoryName).hasCategoryInsideWithName(categoryName)) {
                return true;
            } else error("Invalid category name");
        } else error("Invalid category name");
        return false;
    }

    public void openAddCategory(String categoryName) {
        AddCategoryController controller = (AddCategoryController) loadFxml(Addresses.ADD_CATEGORY, true);
        controller.setCategoryName(categoryName);
    }

    public void openEditCategory(String categoryName) {
        if (Category.hasCategoryWithName(categoryName))
            new EditCategoryManager(account, Category.getCategoryByName(categoryName));
        else error("Invalid category name");
    }
}