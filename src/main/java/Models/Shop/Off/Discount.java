package Models.Shop.Off;

import Models.Account.Customer;
import Models.Address;
import Models.Gson;

import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Discount {
    private static ArrayList<Discount> allDiscounts = new ArrayList<>();
    private String id;
    private Date beginningDate;
    private Date endingDate;
    private int discountPercent;
    private double maximumDiscount;
    private int discountUseCount;
    private ArrayList<Customer> allCustomers;
    private ArrayList<String> allCustomersNames;

    public Discount(String id, Date beginningDate, Date endingDate, int discountPercent, double maximumDiscount, int discountUseCount,
                    ArrayList<Customer> allCustomers) {
        this.id = id;
        this.beginningDate = beginningDate;
        this.endingDate = endingDate;
        this.discountPercent = discountPercent;
        this.maximumDiscount = maximumDiscount;
        this.discountUseCount = discountUseCount;
        this.allCustomers = allCustomers;
        this.allCustomersNames = new ArrayList<>();
        for (Customer customer : allCustomers) {
            allCustomersNames.add(customer.getName());
        }
        allDiscounts.add(this);
    }

    public static boolean hasDiscountWithId(String id) {
        return true;
    }

    public static Discount getDiscountById(String id) {
        return null;
    }

    public static void deleteDiscount(Discount discount) {
        for (Customer customer : discount.allCustomers) {
            customer.deleteDiscount(discount);
        }
        allDiscounts.remove(discount);
    }

    public String getId() {
        return id;
    }

    public void setBeginningDate(Date beginningDate) {
        this.beginningDate = beginningDate;
    }

    public void setEndingDate(Date endingDate) {
        this.endingDate = endingDate;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public void setMaximumDiscount(double maximumDiscount) {
        this.maximumDiscount = maximumDiscount;
    }

    public double getMaximumDiscount() {
        return maximumDiscount;
    }

    public void setDiscountUseCount(int discountUseCount) {
        this.discountUseCount = discountUseCount;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public static ArrayList<String> getDiscountInShort() {
        ArrayList<String> discountsInShort = new ArrayList<>();
        for (Discount discount : allDiscounts) {
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String discountInShort = "#" + discount.id + " : " + discount.discountPercent + "% - " + dateFormat.format(discount.endingDate);
            discountsInShort.add(discountInShort);
        }
        return discountsInShort;
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
                ", allCustomers=" + allCustomers +
                '}';
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

    public static void loadReferences() {
        for (Discount discount : allDiscounts) {
            discount.loadReference();
        }
    }

    private void loadReference() {
        allCustomers = new ArrayList<>();
        for (String customersName : allCustomersNames) {
            allCustomers.add((Customer) Customer.getAccountByUsername(customersName));
        }
    }
}
