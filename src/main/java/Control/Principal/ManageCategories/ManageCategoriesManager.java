package Control.Principal.ManageCategories;

import Control.Manager;
import Models.Account.Account;
import Models.Account.Customer;
import Models.Shop.Category.Category;
import Models.Shop.Product.Product;
import View.ErrorProcessor;
import View.Principal.ManageCategories.ManageCategoriesMenu;

import java.util.ArrayList;
import java.util.HashMap;

public class ManageCategoriesManager extends Manager {
    public ManageCategoriesManager(Account account) {
        super(account);
        new ManageCategoriesMenu(this);
    }

    public void addCategory(String supCategoryName, String categoryName, HashMap<String, Integer> features, ArrayList<String> productsId) {
        Category supCategory = Category.getCategoryByName(supCategoryName);
        Category category = new Category(categoryName, supCategory, features, getProductsListByIds(productsId));
        supCategory.setSubCategory(category);
    }


    public boolean canAddCategory(String supCategoryName, String categoryName) {
        if (Category.hasCategoryWithName(supCategoryName)) {
            if (!Category.getCategoryByName(supCategoryName).hasCategoryInsideWithName(categoryName)) {
                return true;
            } else ErrorProcessor.invalidCategoryName();
        } else ErrorProcessor.invalidCategoryName();
        return false;
    }


    private ArrayList<Product> getProductsListByIds(ArrayList<String> productsId) {
        ArrayList<Product> productsList = new ArrayList<>();
        for (String id : productsId) {
            productsList.add(Product.getProductById(id));
        }
        return productsList;
    }

    public void deleteCategory(String categoryName) {
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

    private void deleteProductsByList(ArrayList<Product> allProducts) {
        for (Product product : allProducts) {
            Product.deleteProduct(product);
            Customer.deleteProductFromCarts(product);
        }
    }
}