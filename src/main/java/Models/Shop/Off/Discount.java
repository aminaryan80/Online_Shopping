package Models.Shop.Off;

import Client.Control.Identity;
import Models.Account.Customer;
import Models.Address;
import Models.Gson;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Discount {
    private static ArrayList<Discount> allDiscounts = new ArrayList<>();
    private String id;
    private LocalDate beginningDate;
    private LocalDate endingDate;
    private int discountPercent;
    private double maximumDiscount;
    private int discountUseCount;
    private ArrayList<String> allCustomersUsernames;
    private HashMap<String, Integer> customerToDiscountUseCountMap = new HashMap<>();

    public Discount(LocalDate beginningDate, LocalDate endingDate, int discountPercent, double maximumDiscount, int discountUseCount,
                    ArrayList<String> allcustomersUsernames) {
        this.id = Identity.getId();
        this.beginningDate = beginningDate;
        this.endingDate = endingDate;
        this.discountPercent = discountPercent;
        this.maximumDiscount = maximumDiscount;
        this.discountUseCount = discountUseCount;
        this.allCustomersUsernames = allcustomersUsernames;
        for (String username : allcustomersUsernames) {
            customerToDiscountUseCountMap.put(username, discountUseCount);
        }
        allDiscounts.add(this);
    }

    public static ArrayList<Discount> getAllDiscounts() {
        return allDiscounts;
    }

    public static boolean hasDiscountWithId(String id) {
        for (Discount discount : allDiscounts) {
            if (discount.id.equals(id))
                return true;
        }
        return false;
    }

    public static Discount getDiscountById(String id) {
        for (Discount discount : allDiscounts) {
            if (discount.id.equals(id))
                return discount;
        }
        return null;
    }

    public static void open() throws Exception {
        File folder = new File(Address.DISCOUNTS.get());
        if (!folder.exists()) folder.mkdirs();
        else {
            for (File file : folder.listFiles()) {
                allDiscounts.add(open(file));
            }
        }
    }

    public static Discount open(File file) throws Exception {
        StringBuilder json = new StringBuilder();
        Scanner reader = new Scanner(file);
        while (reader.hasNext()) json.append(reader.next());
        reader.close();
        return Gson.INSTANCE.get().fromJson(json.toString(), Discount.class);
    }

    public static void save() throws Exception {
        for (Discount discount : allDiscounts) {
            save(discount);
        }
    }

    public static void save(Discount discount) throws Exception {
        String jsonAccount = Gson.INSTANCE.get().toJson(discount);
        FileWriter file = new FileWriter(Address.DISCOUNTS.get() + "\\" + discount.getId() + ".json");
        file.write(jsonAccount);
        file.close();
    }

    public void setDiscountUseCount(int discountUseCount) {
        this.discountUseCount = discountUseCount;
    }

    public LocalDate getBeginningDate() {
        return beginningDate;
    }

    public void setBeginningDate(LocalDate beginningDate) {
        this.beginningDate = beginningDate;
    }

    public LocalDate getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(LocalDate endingDate) {
        this.endingDate = endingDate;
    }

    public ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> allCustomers = new ArrayList<>();
        for (String customerUsername : allCustomersUsernames) {
            allCustomers.add((Customer) Customer.getAccountByUsername(customerUsername));
        }
        return allCustomers;
    }

    public void useDiscount(Customer customer) {
        if (canUseDiscount(customer)) {
            int numberOfDiscountUseLeft = customerToDiscountUseCountMap.get(customer.getUsername());
            customerToDiscountUseCountMap.replace(customer.getUsername(), numberOfDiscountUseLeft - 1);
        }
    }

    public boolean canUseDiscount(Customer customer) {
        return customerToDiscountUseCountMap.get(customer.getUsername()) >= 1;
    }

    public String getId() {
        return id;
    }

    public double getMaximumDiscount() {
        return maximumDiscount;
    }

    public void setMaximumDiscount(double maximumDiscount) {
        this.maximumDiscount = maximumDiscount;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    @Override
    public String toString() {
        return "Discount{" +
                "id='" + id + '\'' +
                ", beginningDate=" + beginningDate +
                ", endingDate=" + endingDate +
                ", discountPercent=" + discountPercent +
                ", maximumDiscount=" + maximumDiscount +
                ", discountUseCount=" + discountUseCount +
                ", allCustomers=" + getAllCustomers() +
                '}';
//        return "#" + id + " : " +
//                "beginningDate = " + dateFormat.format(beginningDate) +
//                "\nendingDate = " + dateFormat.format(endingDate) +
//                "\ndiscountPercent = " + discountPercent + "%" +
//                "\nmaximumDiscount = " + maximumDiscount +
//                "\ndiscountUseCount = " + discountUseCount +
//                "\nallowed customers = " + allCustomersNames;
    }

    public void deleteDiscount() {
        for (Customer customer : getAllCustomers()) {
            customer.deleteDiscount(this);
        }
        allDiscounts.remove(this);
        File file = new File(Address.DISCOUNTS.get() + "\\" + this.getId() + ".json");
        try {
            if (file.exists())
                FileUtils.forceDelete(file);
        } catch (Exception ignored) {

        }
        //file.delete();
    }

    public boolean isActive(LocalDate now) {
        return now.compareTo(beginningDate) > 0 && now.compareTo(endingDate) < 0;
    }

    public boolean belongsToCustomer(Customer customer) {
        for (String customersUsername : allCustomersUsernames) {
            if (customer.getUsername().equals(customersUsername)) {
                return true;
            }
        }
        return false;
    }

//    public static void loadReferences() {
//        for (Discount discount : allDiscounts) {
//            discount.loadReference();
//        }
//    }
//
//    private void loadReference() {
//        for (String customersName : allCustomersUsernames) {
//              allCustomers.add((Customer) Customer.getAccountByUsername(customersName));
//        }
//    }
}
