package Models.Shop;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private static ArrayList<Category> allCategories = new ArrayList<Category>();
    private String name;
    private List<String> features;
    private List<Category> subCategories;
    private List<Product> allProducts;

    public static boolean hasCategoryWithName(String name) {

    }

    public Category getCategoryByName(String name) {

    }

    public List<String> getFeatures() {
        return features;
    }

    public List<String> filterBasedOnFeature(String feature, String value) {

    }

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
