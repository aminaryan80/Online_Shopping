package Models.Shop;

import Models.Address;
import Models.Gson;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Category {
    private static ArrayList<Category> allCategories = new ArrayList<Category>();
    private Category supCategory;
    private String supCategoryName;
    private String name;
    private HashMap<String, Integer> features; // featureName - featureType // 0 : int - 1 : String
    private List<Category> subCategories;
    private List<String> subCategoriesNames;
    private ArrayList<Product> allProducts;
    private ArrayList<String> allProductsIds;

    public Category(String name, Category supCategory, HashMap<String, Integer> features, ArrayList<Product> allProducts) {
        this.name = name;
        this.features = features;
        this.allProducts = allProducts;
        this.allProductsIds = new ArrayList<>();
        for (Product product : allProducts) {
            this.allProductsIds.add(product.getId());
        }
        this.supCategory = supCategory;
        if (supCategory != null) {
            this.supCategoryName = supCategory.getName();
        }
        this.subCategories = new ArrayList<Category>();
        this.subCategoriesNames = new ArrayList<>();
        allCategories.add(this);
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
        ArrayList<Product> products = new ArrayList<>(allProducts);
        for (Category category : subCategories) {
            products.addAll(category.allProducts);
        }
        return products;
    }

    public static void deleteCategory(Category category) {
        category.supCategory.subCategories.remove(category);
        allCategories.remove(category);
        ArrayList<Category> tmp = new ArrayList<>(category.subCategories);
        for (Category subCategory : tmp) {
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

    public static void loadReferences() {
        for (Category category : allCategories) {
            category.loadReference();
        }
    }

    private void loadReference() {
        supCategory = Category.getCategoryByName(supCategoryName);
        allProducts = new ArrayList<>();
        for (String productsId : allProductsIds) {
            allProducts.add(Product.getProductById(productsId));
        }
        subCategories = new ArrayList<>();
        for (String subCategoriesName : subCategoriesNames) {
            subCategories.add(Category.getCategoryByName(subCategoriesName));
        }
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
