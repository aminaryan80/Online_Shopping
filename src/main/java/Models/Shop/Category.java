package Models.Shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Category {
    private static ArrayList<Category> allCategories = new ArrayList<Category>();
    private Category supCategory;
    private String name;
    private HashMap<String, Integer> features; // featureName - featureType // 0 : int - 1 : String
    private List<Category> subCategories;
    private ArrayList<Product> allProducts;

    public Category(String name, Category supCategory, HashMap<String, Integer> features, List<Product> allProducts) {
        this.name = name;
        this.features = features;
        this.allProducts = allProducts;
        this.supCategory = supCategory;
        this.subCategories = new ArrayList<Category>();
        allCategories.add(this);
    }

    public static ArrayList<String> getAllCategoriesNames() {
        ArrayList<String> allCategoriesNames= new ArrayList<String>();
        for (Category category : allCategories) {
            allCategoriesNames.add(category.getName());
        }
        return allCategoriesNames;
    }

    public static boolean hasCategoryWithName(String name) {
        for (Category category : allCategories) {
            if(category.getName().equals(name))
                return true;
        }
        return false;
    }

    public ArrayList<String> getFeaturesNames() {
        return (ArrayList<String>) features.keySet();
    }

    public static Category getCategoryByName(String name) {
        for (Category category : allCategories) {
            if (category.getName().equals(name))
                return category;
        }
        return null;
    }

    public ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    public static void deleteCategory(Category category) {
        category.supCategory.subCategories.remove(category);
        allCategories.remove(category);
        for(Category subCategory : category.subCategories) {
            deleteCategory(subCategory);
        }
    }

    public boolean hasCategoryInsideWithName(String name) {
        for (Category category : subCategories) {
            if (category.getName().equals(name))
                return true;
        }
        return false;
    }

    public List<Category> getSubCategories() {
        return subCategories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSubCategory(Category category) {
        this.subCategories.add(category);
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", features=" + features +
                ", subCategories=" + subCategories +
                ", allProducts=" + allProducts +
                '}';
    }

}
