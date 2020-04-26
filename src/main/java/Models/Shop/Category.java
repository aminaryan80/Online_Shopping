package Models.Shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Category {
    private static ArrayList<Category> allCategories = new ArrayList<>();
    private Category supCategory;
    private String name;
    private HashMap<String, Integer> features; // featureName - featureType // 0 : int - 1 : String
    private List<Category> subCategories;
    private List<Product> allProducts;

    public Category(String name, Category supCategory, HashMap<String, Integer> features, List<Product> allProducts) {
        this.name = name;
        this.features = features;
        this.allProducts = allProducts;
        this.supCategory = supCategory;
        allCategories.add(this);
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

//    public List<String> filterBasedOnFeature(String feature, String value) {
//
//    }

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
