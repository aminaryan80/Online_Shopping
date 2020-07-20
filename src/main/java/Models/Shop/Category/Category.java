package Models.Shop.Category;

import Client.Control.Identity;
import Models.Address;
import Models.Gson;
import Models.Shop.Product.Product;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Category {
    private static ArrayList<Category> allCategories = new ArrayList<>();
    private String supCategoryName;
    private String name;
    private HashMap<String, Integer> features; // featureName - featureType // 0 : int - 1 : String
    private ArrayList<String> subCategoriesNames;
    private ArrayList<String> allProductsIds;
    private String id;

    public Category(String name, String supCategoryName, HashMap<String, Integer> features, ArrayList<String> allProductsIds) {
        id = Identity.getId(); //future TODO category should be unified by id
        this.name = name;
        this.features = features;
        this.allProductsIds = allProductsIds;
        this.supCategoryName = supCategoryName;
        if (supCategoryName != null)
            getSupCategory().addSubCategory(this);
        this.subCategoriesNames = new ArrayList<>();
        allCategories.add(this);
    }

    public static ArrayList<Category> getAllCategories() {
        return allCategories;
    }

    public ArrayList<String> getSubCategoriesNames() {
        return subCategoriesNames;
    }

    public static boolean hasCategoryWithName(String name) {
        for (Category category : allCategories) {
            if (category.getName().equals(name))
                return true;
        }
        return false;
    }

    public static Category getCategoryByName(String name) {
        for (Category category : allCategories) {
            if (category.getName().equals(name))
                return category;
        }
        return null;
    }

    public static Category getCategoryById(String id) {
        for (Category category : allCategories) {
            if (category.getId().equals(id))
                return category;
        }
        return null;
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
        reader.close();
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

    public ArrayList<Feature> getFeatures() {
        ArrayList<Feature> features = new ArrayList<>();
        ArrayList<String> names = getFeaturesNames();
        for (String name : names) {
            features.add(new Feature(name, "iu"));
        }
        return features;
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

    public void addProduct(String productId) {
        allProductsIds.add(productId);
    }

    public String getId() {
        return id;
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

//    public List<Category> getSubCategories() {
//        return subCategories;
//    }

    public ArrayList<String> getFeaturesNames() {
        return new ArrayList<>(features.keySet());
    }

    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>(getProducts());
        ArrayList<Category> subCategories = getSubCategories();
        for (Category category : subCategories) {
            products.addAll(category.getAllProducts());
        }
        return products;
    }

    public void editName(String newName) {
        getCategoryByName(this.supCategoryName).subCategoriesNames.remove(this.name);
        getCategoryByName(this.supCategoryName).subCategoriesNames.add(newName);
        File file = new File(Address.CATEGORIES.get() + "\\" + this.name + ".json");
        try {
            if (file.exists())
                FileUtils.forceDelete(file);
        } catch (Exception ignored) {

        }
        this.setName(newName);
        this.changeCategoryNameForProducts();

    }

    public boolean hasCategoryInsideWithName(String name) {
        for (Category category : getSubCategories()) {
            if (category.getName().equals(name))
                return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addSubCategory(Category category) {
        this.subCategoriesNames.add(category.getName());
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

    public ArrayList<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<>();
        for (String productsId : allProductsIds) {
            if (Product.getProductById(productsId) != null)
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
