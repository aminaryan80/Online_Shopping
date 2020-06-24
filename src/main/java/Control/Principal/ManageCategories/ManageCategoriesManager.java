package Control.Principal.ManageCategories;

import Control.Manager;
import Models.Account.Account;
import Models.Account.Customer;
import Models.Shop.Category.Category;
import Models.Shop.Product.Product;
import View.ErrorProcessor;
import View.Principal.ManageCategories.ManageCategoriesMenu;
import ViewController.principal.manageCategories.AddCategoryController;
import ViewController.principal.manageCategories.ManageCategoriesController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ManageCategoriesManager extends Manager {
    public ManageCategoriesManager(Account account) {
        super(account);
        new ManageCategoriesMenu(this);
    }

    public ManageCategoriesManager(Account account, Addresses address, Manager manager) {
        super(account, address, manager);
        //new ManageCategoriesMenu(this);
        ManageCategoriesController controller = (ManageCategoriesController) loadFxml(Addresses.MANAGE_CATEGORIES);
        controller.init();
    }

    public void addCategory(String supCategoryName, String categoryName, HashMap<String, Integer> features, ArrayList<String> productsIds) {
//        Category supCategory = Category.getCategoryByName(supCategoryName); canAddCategory checks existence
        if (canAddCategory(supCategoryName, categoryName)) {
            Category category = new Category(categoryName, supCategoryName, features, productsIds);
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

    public void deleteCategory(String categoryName) throws IOException {
        Category.deleteCategory(Category.getCategoryByName(categoryName));
    }

    public boolean canDeleteCategory(String categoryName) {
        if (Category.hasCategoryWithName(categoryName)) {
            if (!Category.getCategoryByName(categoryName).equals(mainCategory)) {
                return true;
            } else ErrorProcessor.invalidCategoryName();
        } else ErrorProcessor.invalidCategoryName();
        return false;
    }

    private void deleteProductsByList(ArrayList<Product> allProducts) throws IOException { //TODO HANDLE
        for (Product product : allProducts) {
            Product.deleteProduct(product);
            Customer.deleteProductFromCarts(product);
        }
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