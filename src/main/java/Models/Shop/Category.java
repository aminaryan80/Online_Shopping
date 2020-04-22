package Models.Shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Category {
    private static ArrayList<Category> allCategories = new ArrayList<>();
    private String name;
    private HashMap<String, Integer> features; // featureName - featureType
    private List<Category> subCategories;
    private List<Product> allProducts;

    public Category(String name, HashMap<String, Integer> features, List<Product> allProducts) {
        this.name = name;
        this.features = features;
        this.allProducts = allProducts;
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
            if(category.getName().equals(name))
                return category;
        }
        return null;
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

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", features=" + features +
                ", subCategories=" + subCategories +
                ", allProducts=" + allProducts +
                '}';
    }

    public static void deleteCategory(Category category) {

    }
}
