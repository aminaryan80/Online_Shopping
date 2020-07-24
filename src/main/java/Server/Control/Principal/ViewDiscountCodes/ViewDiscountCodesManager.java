package Server.Control.Principal.ViewDiscountCodes;

import Server.Control.Manager;
import Models.Account.Account;
import Models.Shop.Category.Sort;
import Models.Shop.Off.Discount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ViewDiscountCodesManager extends Manager {

    private Sort currentSort;
    private List<Discount> discounts;

    public ViewDiscountCodesManager(Account account) {
        super(account);
        discounts = Discount.getAllDiscounts();
    }

    public int deleteDiscountCode(String id) {
        if (Discount.hasDiscountWithId(id)) {
            Discount.getDiscountById(id).deleteDiscount();
            return 0;
        }
        return 1;
    }

    public String viewDiscountCode(String id) {
        if (Discount.hasDiscountWithId(id)) {
            return Discount.getDiscountById(id).toString();
        }
        return "Not Found";
    }

    public String currentSort() {
        return currentSort.toString();
    }

    public ArrayList<Object> disableSort() {
        currentSort = null;
        discounts = Discount.getAllDiscounts();
        return new ArrayList<>(discounts);
    }

    public String showAvailableSorts() {
        return "discountpercent\n" +
                "beginningdate\n" +
                "endingdate";
    }

    public ArrayList<String> getSortFields() {
        ArrayList<String> fields = new ArrayList<>();
        fields.add("discountpercent");
        fields.add("beginningdate");
        fields.add("endingdate");
        return fields;
    }

    public ArrayList<Object> sort(String sort, boolean isAscending) {
        discounts = Discount.getAllDiscounts();
        currentSort = new Sort(sort, isAscending);
        applySort();
        return new ArrayList<>(discounts);
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
        discounts = Arrays.asList(discountsForSort);
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
        discounts = Arrays.asList(discountsForSort);
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
        discounts = Arrays.asList(discountsForSort);
    }

    public boolean isEnteredSortFieldValid(String field) {
        return field.equals("discountpercent") || field.equals("beginningdate") || field.equals("endingdate");
    }
}
