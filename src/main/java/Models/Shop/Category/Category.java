package Models.Shop.Category;

import Models.Address;
import Models.Gson;
import Models.Shop.Product.Product;
import com.sun.jdi.ArrayReference;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Category {
    private static ArrayList<Category> allCategories = new ArrayList<>();
//    private Category supCategory;
    private String supCategoryName = null;
    private String name;
    private HashMap<String, Integer> features; // featureName - featureType // 0 : int - 1 : String
//    private List<Category> subCategories;
    private List<String> subCategoriesNames;
//    private ArrayList<Product> allProducts;
    private ArrayList<String> allProductsIds;

    public Category(String name, String supCategoryName, HashMap<String, Integer> features, ArrayList<String> allProductsIds) {
        this.name = name;
        this.features = features;
//        this.allProducts = allProducts;
        this.allProductsIds = new ArrayList<>();
 //       for (Product product : allProducts) {
 //           this.allProductsIds.add(product.getId());
//        }
 //       this.supCategory = supCategory;
        if (supCategoryName != null) {
            this.supCategoryName = supCategoryName;
        }
//        this.subCategories = new ArrayList<Category>();
        this.subCategoriesNames = new ArrayList<>();
        allCategories.add(this);
    }

    public void changeCategoryNameForProducts() {
        for (Product product : getProducts()) {
            product.setCompanyName(name);
        }
    }

    public void addFeature(String feature) {
        features.put(feature, 0);
        for (Product product : getProducts()) {
            product.addFeature(new Feature(feature, ""));
        }
    }

    public void editFeature(String oldName, String newName) {
        features.remove(oldName);
        features.put(newName, 0);
        for (Product product : getProducts()) {
            product.editFeature(oldName, newName);
        }
    }

    public void removeFeature(String feature) {
        features.remove(feature);
        for (Product product : getProducts()) {
            product.removeFeature(feature);
        }
    }

    public static ArrayList<String> getAllCategoriesNames() {
        ArrayList<String> allCategoriesNames = new ArrayList<>();
        for (Category category : allCategories) {
            allCategoriesNames.add(category.getName());
        }
        return allCategoriesNames;
    }

    public static boolean hasCategoryWithName(String name) {
        for (Category category : allCategories) {
            if (category.getName().equals(name))
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
        ArrayList<Product> products = new ArrayList<>(getProducts());
        for (Category category : getSubCategories()) {
            products.addAll(category.getProducts());
        }
        return products;
    }

    public static void deleteCategory(Category category) {
        category.subCategoriesNames.remove(category.getName());
        allCategories.remove(category);
        ArrayList<Category> subCats = new ArrayList<>(category.getSubCategories());
        for (Category subCategory : subCats) {
            deleteCategory(subCategory);
        }
    }

    public boolean hasCategoryInsideWithName(String name) {
        for (Category category : getSubCategories()) {
            if (category.getName().equals(name))
                return true;
        }
        return false;
    }

//    public List<Category> getSubCategories() {
//        return subCategories;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSubCategory(Category category) {
//        this.subCategories.add(category);
        this.subCategoriesNames.add(category.getName());
    }

    public static void open() throws Exception {
        File folder = new File(Address.CATEGORIES.get());
        if (!folder.exists()) folder.mkdirs();
        else {
            for (File file : folder.listFiles()) {
                allCategories.add(open(file));
            }
        }
    }

    public static Category open(File file) throws Exception {
        StringBuilder json = new StringBuilder();
        Scanner reader = new Scanner(file);
        while (reader.hasNext()) json.append(reader.next());
        return Gson.INSTANCE.get().fromJson(json.toString(), Category.class);
    }

    public static void save() throws Exception {
        for (Category category : allCategories) {
            save(category);
        }
    }

    public static void save(Category category) throws Exception {
        String jsonAccount = Gson.INSTANCE.get().toJson(category);
        FileWriter file = new FileWriter(Address.CATEGORIES.get() + "\\" + category.getName() + ".json");
        file.write(jsonAccount);
        file.close();
    }

//    public static void loadReferences() {
//        for (Category category : allCategories) {
//            category.loadReference();
//        }
//    }
//
//    private void loadReference() {
//        supCategory = Category.getCategoryByName(supCategoryName);
//
//    }

    public ArrayList<Product> getProducts(){
        ArrayList<Product> products = new ArrayList<>();
        for (String productsId : allProductsIds) {
            products.add(Product.getProductById(productsId));
        }
        return products;
    }

    public Category getSupCategory() {
        return Category.getCategoryByName(supCategoryName);
    }

    public ArrayList<Category> getSubCategories() {
        ArrayList<Category> subCategories = new ArrayList<>();
        for (String subCategoriesName : subCategoriesNames) {
            subCategories.add(Category.getCategoryByName(subCategoriesName));
        }
        return subCategories;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", features=" + features +
                ", subCategories=" + getSubCategories() +
                ", allProducts=" + getAllProducts() + //????????????????????????????????//
                '}';
    }

}
