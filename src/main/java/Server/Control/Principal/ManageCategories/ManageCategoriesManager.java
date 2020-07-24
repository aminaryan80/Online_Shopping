package Server.Control.Principal.ManageCategories;

import Server.Control.Manager;
import Client.ViewController.Controller;
import Client.ViewController.principal.manageCategories.ManageCategoriesController;
import Models.Account.Account;
import Models.Shop.Category.Category;

import java.util.ArrayList;
import java.util.HashMap;

public class ManageCategoriesManager extends Manager {
    public ManageCategoriesManager(Account account) {
        super(account);
    }

    public int addCategory(String supCategoryName, String categoryName, HashMap<String, Integer> features, ArrayList<String> productsIds) {
        if (canAddCategory(supCategoryName, categoryName)) {
            new Category(categoryName, supCategoryName, features, productsIds);
            return 0;
        }
        return 1;
    }

    public boolean canAddCategory(String supCategoryName, String categoryName) {
        if (Category.hasCategoryWithName(supCategoryName)) {
            if (!Category.getCategoryByName(supCategoryName).hasCategoryInsideWithName(categoryName)) {
                return true;
            }
        }
        return false;
    }
}