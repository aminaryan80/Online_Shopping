package View.Products;

import Control.Manager;
import Control.Products.ProductsManager;
import Models.Shop.Category;
import View.Menu;

import java.util.regex.Matcher;

public class ProductsMenu extends Menu {

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
                showCategories();
            } else if (getMatcher(input, "^filtering$").find()) {
                // TODO by ali
            } else if (getMatcher(input, "^sorting$").find()) {

            } else if (getMatcher(input, "^show products$").find()) {

            } else if ((matcher = getMatcher(input, "^show product (\\S+)$")).find()) {

            } else if (getMatcher(input, "^help$").find()) {
                help();
            } else if (getMatcher(input, "^back$").find()) {
                return;
            }
        }
    }

    public void showCategories() {
        System.out.println(((ProductsManager) manager).showCategories());
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

    private void help() {
        System.out.println("view categories\n" +
                "filtering\n" +
                "sorting\n" +
                "show products\n" +
                "show product pproductId]\n" +
                "help\n" +
                "back");
    }
}
