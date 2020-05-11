package View.Seller;

import Control.Manager;
import Control.Seller.SellerManager;
import Models.Account.Seller;
import Models.Shop.*;
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
            String command = scanner.nextLine();
            if ((matcher = getMatcher(command, "^view personal info$")).find()) {
                viewPersonalInfo();
            } else if ((matcher = getMatcher(command, "^view company information$")).find()) {
                viewCompanyInformation();
            } else if ((matcher = getMatcher(command, "^view sales history$")).find()) {
                viewSalesHistory();
            } else if ((matcher = getMatcher(command, "^manage products$")).find()) {
                manageProducts();
            } else if ((matcher = getMatcher(command, "^add product$")).find()) {
                addProduct();
            } else if ((matcher = getMatcher(command, "^remove product (\\d+)$")).find()) {
                deleteProduct(matcher.group(1));
            } else if ((matcher = getMatcher(command, "^show categories$")).find()) {
                showCategories();
            } else if ((matcher = getMatcher(command, "^view offs$")).find()) {
                viewOffs();
            } else if ((matcher = getMatcher(command, "view balance$")).find()) {
                viewSellerBalance();
            } else if ((matcher = getMatcher(command, "^back$")).find()) {
                return;
            } else if ((matcher = getMatcher(command, "help")).find()) {
                help();
            } else {
                ErrorProcessor.invalidInput();
            }
        }
    }

    private void viewPersonalInfo() {
        System.out.println(((SellerManager) manager).viewPersonalInfo());
        while (true) {
            String command = scanner.nextLine();
            Matcher matcher;
            if ((matcher = getMatcher(command, "^edit$")).find()) {
                editAttribute();
            } else ErrorProcessor.invalidInput();
        }
    }

    private void editAttribute() {
        System.out.println("enter the field you want to edit");
        String field = scanner.nextLine();
        if (((SellerManager) manager).isEnteredAccountFieldValid(field)) {
            ErrorProcessor.invalidEditField();
            return;
        }
        System.out.println("enter the new value");
        String newValue = scanner.nextLine();
        //TODO the rest of method
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
        System.out.println("enter product's id");
        String id = scanner.nextLine();
        System.out.println("enter product's price");
        double price = scanner.nextFloat();
        System.out.println("enter product's name");
        String name = scanner.nextLine();
        System.out.println("enter product's company name");
        String companyName = scanner.nextLine();
        System.out.println("enter product's description");
        String description = scanner.nextLine();
        System.out.println("is your product available(answer with true or false)");
        boolean isAvailable = Boolean.parseBoolean(scanner.nextLine());
        String categoryName;
        while (true) {
            System.out.println("enter product's category");
            categoryName = scanner.nextLine();
            if (Category.hasCategoryWithName(categoryName)) {
                break;
            } else {
                ErrorProcessor.invalidCategoryName();
            }
        }
        Category category = Category.getCategoryByName(categoryName);
        ArrayList<Feature> allFeatures = getFeatures(category);
        Product product = ((SellerManager) manager).addProduct(id, name, companyName, category, price, isAvailable, description, allFeatures);
        new AddProductRequest("random id", (Seller) manager.getAccount(), manager, product);
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
        System.out.println(((SellerManager) manager).showCategories());
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
                "help\n" +
                "back");
    }
}
