package Models.Shop;

import java.util.List;

public class Category {
    private String name;
    private List<String> features;
    private List<Category> subCategories;
    private List<Product> allProducts;
}
