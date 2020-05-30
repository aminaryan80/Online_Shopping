package Control.Principal.ViewDiscountCodes;

import Control.Manager;
import Models.Account.Account;
import Models.Shop.Category.Sort;
import Models.Shop.Off.Discount;
import Models.Shop.Product.Product;
import View.Principal.ViewDiscountCodes.ViewDiscountCodesMenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ViewDiscountCodesManager extends Manager {

    private Sort currentSort;
    private ArrayList<Discount> discounts;

    public ViewDiscountCodesManager(Account account) {
        super(account);
        discounts = Discount.getAllDiscounts();
        new ViewDiscountCodesMenu(this);
    }

    public void showDiscounts() {
        for (String discountInShort : Discount.getDiscountInShort()) {
            System.out.println(discountInShort);
        }
    }

    public void deleteDiscountCode(String id) throws IOException {
        Discount.getDiscountById(id).deleteDiscount();
    }

    public String viewDiscountCode(String id) {
        return Discount.getDiscountById(id).toString();
    }

    public String currentSort() {
        return currentSort.toString();
    }

    public ArrayList<String> disableSort() {
        currentSort = null;
        discounts = Discount.getAllDiscounts();
        return discountsInShort();
    }

    private ArrayList<String> discountsInShort() {
        ArrayList<String> discountsInShort = new ArrayList<>();
        for (Discount discount : discounts) {
            discountsInShort.add(discount.getId() + "  " + discount.getDiscountPercent() + "  " + discount.getBeginningDate() + "  " + discount.getEndingDate());
        }
        return discountsInShort;
    }

    public String showAvailableSorts() {
        return "discountpercent\n" +
                "beginningdate\n" +
                "endingdate";
    }

    public ArrayList<String> sort(String sort, boolean isAscending) {
        discounts = Discount.getAllDiscounts();
        currentSort = new Sort(sort, isAscending);
        applySort();
        return discountsInShort();
    }

    private void applySort() {
        if (currentSort == null) {
            return;
        }
        String field = currentSort.getField();
        if (field.equals("discountpercent")) {
            sortByDiscountPercentage();
        } else if (field.equals("beginningdate")) {
            sortByBeginningDate();
        } else {
            sortByEndingDate();
        }
        if (!currentSort.isAscending()) {
            Collections.reverse(discounts);
        }
    }

    private void sortByDiscountPercentage() {
        Discount[] discountsForSort = discounts.toArray(new Discount[0]);
        for (int i = 0; i < discountsForSort.length; i++) {
            for (int j = i + 1; j < discountsForSort.length; j++) {
                if (discountsForSort[i].getDiscountPercent() > discountsForSort[j].getDiscountPercent()) {
                    Discount temp = discountsForSort[i];
                    discountsForSort[i] = discountsForSort[j];
                    discountsForSort[j] = temp;
                }
            }
        }
        discounts = (ArrayList<Discount>) Arrays.asList(discountsForSort);
    }

    private void sortByBeginningDate() {
        Discount[] discountsForSort = discounts.toArray(new Discount[0]);
        for (int i = 0; i < discountsForSort.length; i++) {
            for (int j = i + 1; j < discountsForSort.length; j++) {
                if (discountsForSort[i].getBeginningDate().isBefore(discountsForSort[j].getBeginningDate())) {
                    Discount temp = discountsForSort[i];
                    discountsForSort[i] = discountsForSort[j];
                    discountsForSort[j] = temp;
                }
            }
        }
        discounts = (ArrayList<Discount>) Arrays.asList(discountsForSort);
    }

    private void sortByEndingDate() {
        Discount[] discountsForSort = discounts.toArray(new Discount[0]);
        for (int i = 0; i < discountsForSort.length; i++) {
            for (int j = i + 1; j < discountsForSort.length; j++) {
                if (discountsForSort[i].getEndingDate().isBefore(discountsForSort[j].getEndingDate())) {
                    Discount temp = discountsForSort[i];
                    discountsForSort[i] = discountsForSort[j];
                    discountsForSort[j] = temp;
                }
            }
        }
        discounts = (ArrayList<Discount>) Arrays.asList(discountsForSort);
    }

    public boolean isEnteredSortFieldValid(String field) {
        return field.equals("discountpercent") || field.equals("beginningdate") || field.equals("endingdate");
    }

    public void editDiscountCode(String id) {
        new EditDiscountCodeManager(account, Discount.getDiscountById(id));
    }
}
