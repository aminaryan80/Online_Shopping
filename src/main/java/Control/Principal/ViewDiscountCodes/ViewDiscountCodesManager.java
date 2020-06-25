package Control.Principal.ViewDiscountCodes;

import Control.Manager;
import Models.Account.Account;
import Models.Shop.Category.Sort;
import Models.Shop.Off.Discount;
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

    public ViewDiscountCodesManager(Account account, Addresses address, Manager manager) {
        super(account, address, manager);
        discounts = Discount.getAllDiscounts();
        loadFxml(Addresses.VIEW_DISCOUNT_CODES);
    }

    public void showDiscounts() {
        for (String discountInShort : Discount.getDiscountInShort()) {
            System.out.println(discountInShort);
        }
    }

    public void deleteDiscountCode(String id) throws IOException {
        if (Discount.hasDiscountWithId(id)) {
            Discount.getDiscountById(id).deleteDiscount();
            success("Discount deleted successfully.");
        } else error("Invalid discount id");
    }

    public String viewDiscountCode(String id) {
        if (Discount.hasDiscountWithId(id)) {
            return Discount.getDiscountById(id).toString();
        } else error("Invalid discount id");
        return null;
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

    public ArrayList<Discount> sort(String sort, boolean isAscending) {
        discounts = Discount.getAllDiscounts();
        currentSort = new Sort(sort, isAscending);
        applySort();
        return discounts;
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
        if (Discount.hasDiscountWithId(id)) {
            new EditDiscountCodeManager(account, Discount.getDiscountById(id));
        } else error("Invalid discount id");
    }
}
