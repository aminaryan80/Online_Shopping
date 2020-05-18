package View.Seller;

import Control.Manager;
import Control.Seller.SellerManager;
import Models.Shop.Category.Category;
import Models.Shop.Category.Feature;
import Models.Shop.Product.Product;
import View.ErrorProcessor;
import View.Menu;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class SellerMenu extends Menu {

    public SellerMenu(Manager manager) {
        super(manager);
        sellerMenu();
    }

    public void sellerMenu() {
        Matcher matcher;
        while (true) {
            String input = scanner.nextLine();
            if (getMatcher(input, "^view personal info$").find()) {
                viewPersonalInfo();
            } else if (getMatcher(input, "^view company information$").find()) {
                viewCompanyInformation();
            } else if (getMatcher(input, "^view sales history$").find()) {
                viewSalesHistory();
            } else if (getMatcher(input, "^manage products$").find()) {
                manageProducts();
            } else if (getMatcher(input, "^add product$").find()) {
                addProduct();
            } else if ((matcher = getMatcher(input, "^remove product (\\d+)$")).find()) {
                deleteProduct(matcher.group(1));
            } else if ((matcher = getMatcher(input, "^show categories$")).find()) {
                showCategories();
            } else if ((matcher = getMatcher(input, "^view offs$")).find()) {
                viewOffs();
            } else if ((matcher = getMatcher(input, "view balance$")).find()) {
                viewSellerBalance();
            } else if (getMatcher(input, "logout").find()) {
                logout();
                return;
            } else if ((matcher = getMatcher(input, "^back$")).find()) {
                return;
            } else if ((matcher = getMatcher(input, "help")).find()) {
                help();
            } else {
                ErrorProcessor.invalidInput();
            }
        }
    }

    private void viewPersonalInfo() {
        manager.viewPersonalInfo();
    }

    private void viewCompanyInformation() {
        System.out.println(((SellerManager) manager).viewCompanyInformation());
    }

    private void viewSalesHistory() {
        ArrayList<String> allHistory = ((SellerManager) manager).viewSalesHistory();
        for (String history : allHistory) {
            System.out.println(history);
        }
    }

    private void manageProducts() {
        ArrayList<String> allProducts = ((SellerManager) manager).viewProducts();
        for (String product : allProducts) {
            System.out.println(product);
        }
        new EditProductsMenu(manager);
    }

    private void addProduct() {
        String priceString;
        while (true) {
            System.out.println("enter product's price");
            priceString = scanner.nextLine();
            if (manager.checkNumber(priceString)) {
                break;
            } else {
                ErrorProcessor.invalidCategoryName();
            }
        }
        double price = Double.parseDouble(priceString);
        System.out.println("enter product's name");
        String name = scanner.nextLine();
        System.out.println("enter product's description");
        String description = scanner.nextLine();
        System.out.println("is your product available(answer with true or false)");
        boolean isAvailable = Boolean.parseBoolean(scanner.nextLine());
        String categoryName;
        while (true) {
            System.out.println("enter product's category");
            categoryName = scanner.nextLine();
            if (Category.hasCategoryWithName(categoryName) && !categoryName.equals("mainCategory")) {
                break;
            } else {
                ErrorProcessor.invalidCategoryName();
            }
        }
        Category category = Category.getCategoryByName(categoryName);
        ArrayList<Feature> allFeatures = getFeatures(category);
        ((SellerManager) manager).addProduct(name, category, price, isAvailable, description, allFeatures);
    }

    private ArrayList<Feature> getFeatures(Category category) {
        ArrayList<Feature> features = new ArrayList<Feature>();
        ArrayList<String> featuresNames = category.getFeaturesNames();
        for (String featureName : featuresNames) {
            System.out.printf("enter products's %s%n", featureName);
            String featureValue = scanner.nextLine();
            features.add(new Feature(featureName, featureValue));
        }
        return features;
    }

    private void deleteProduct(String id) {
        if (!Product.hasProductWithId(id)) {
            ErrorProcessor.invalidProductId();
            return;
        }
        if (!((SellerManager) manager).isItSellersProduct(id)) {
            ErrorProcessor.notYourProduct();
            return;
        }
        ((SellerManager) manager).deleteProductById(id);
    }

    private void showCategories() {
        System.out.println(manager.showCategories());
    }

    private void viewOffs() {
        ArrayList<String> allOffs = ((SellerManager) manager).viewOffs();
        for (String off : allOffs) {
            System.out.println(off);
        }
        new OffsMenu(manager);
    }

    private void viewSellerBalance(){
        System.out.println(((SellerManager) manager).viewSellerBalance());
    }

    private void help() {
        System.out.println("view personal info\n" +
                "view company information\n" +
                "view sales history\n" +
                "manage products\n" +
                "add product\n" +
                "remove product [product id]\n" +
                "show categories\n" +
                "view offs\n" +
                "view balance\n" +
                "logout\n" +
                "help\n" +
                "back");
    }
}
