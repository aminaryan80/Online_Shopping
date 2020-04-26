package View.Products;

import Control.Manager;
import Control.Products.ProductsManager;
import Models.Shop.Category;
import View.Menu;

import java.util.regex.Matcher;

public class ProductsMenu extends CommandProcessor{

    public ProductsMenu(Manager manager, Category category) {
        super(manager);
        this.category = category;
        productsMenu();
    }

    private Category category;

    public void productsMenu() {
        while (true) {
            Matcher matcher;
            String input = scanner.nextLine();
            if (getMatcher(input, "^view categories$").find()) {
                ((ProductsManager) manager).showCategories();
            } else if (getMatcher(input, "^filtering$").find()) {
                // TODO by ali
            } else if (getMatcher(input, "^sorting$").find()) {

            } else if (getMatcher(input, "^show products$").find()) {

            } else if ((matcher = getMatcher(input, "^show product (\\S+)$")).find()) {

            }
        }
    }

    public void viewCategories() {

    }

    private void filtering() {

    }

    private void showAvailableFilters() {

    }

    private void applyFilter(String filterType) {

    }

    private void currentFilters() {

    }

    private void disableFilter(String filter) {

    }

    private void showAvailableSorts() {

    }

    private void sort(String sort) {

    }

    private void currentSort() {

    }

    private void disableSort() {

    }

    private void showProducts() {

    }

    private void showProduct(String id) {

    }
}
