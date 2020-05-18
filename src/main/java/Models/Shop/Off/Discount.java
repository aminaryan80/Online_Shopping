package Models.Shop.Off;

import Control.Identity;
import Models.Account.Customer;
import Models.Address;
import Models.Gson;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
//    private ArrayList<Customer> allCustomers;
    private ArrayList<String> allCustomersUsernames;
    private HashMap<String,Integer> customerToDiscountUseCountMap = new HashMap<>();
    public Discount(LocalDate beginningDate, LocalDate endingDate, int discountPercent, double maximumDiscount, int discountUseCount,
                    ArrayList<String> allcustomersUsernames) {
        this.id = Identity.getId();
        this.beginningDate = beginningDate;
        this.endingDate = endingDate;
        this.discountPercent = discountPercent;
        this.maximumDiscount = maximumDiscount;
        this.discountUseCount = discountUseCount;
        //this.allCustomers = allCustomers;
        this.allCustomersUsernames = allcustomersUsernames;
        for (String username : allcustomersUsernames) {
            customerToDiscountUseCountMap.put(username,discountUseCount);
        }
        allDiscounts.add(this);
    }

    public void setDiscountUseCount(int discountUseCount) {
        this.discountUseCount = discountUseCount;
    }

    public ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> allCustomers = new ArrayList<>();
        for (String customerUsername : allCustomersUsernames) {
            allCustomers.add((Customer) Customer.getAccountByUsername(customerUsername));
        }
        return allCustomers;
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

    public void useDiscount(Customer customer) {
        if(canUseDiscount(customer)){
            int numberOfDiscountUseLeft = customerToDiscountUseCountMap.get(customer.getUsername());
            customerToDiscountUseCountMap.replace(customer.getUsername(),numberOfDiscountUseLeft-1);
        }
    }

    public boolean canUseDiscount(Customer customer) {
        return customerToDiscountUseCountMap.get(customer.getUsername())>=1;
    }

    public void deleteDiscount(Discount discount) {
        for (Customer customer : getAllCustomers()) {
            customer.deleteDiscount(discount);
        }
        allDiscounts.remove(discount);
    }

    public String getId() {
        return id;
    }

    public void setBeginningDate(LocalDate beginningDate) {
        this.beginningDate = beginningDate;
    }

    public void setEndingDate(LocalDate endingDate) {
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

    public void reDiscountUseCount(int discountUseCount) {
        this.discountUseCount = discountUseCount;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public static ArrayList<String> getDiscountInShort() {
        ArrayList<String> discountsInShort = new ArrayList<>();
        for (Discount discount : allDiscounts) {
//            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            //String discountInShort = "#" + discount.id + " : " + discount.discountPercent + "% - " + dateFormat.format(discount.endingDate);
            String discountInShort = "#" + discount.id + " : " + discount.discountPercent + "% - " + discount.endingDate;
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

    public void deleteDiscount() throws IOException {
        for (Customer customer : getAllCustomers()) {
            customer.deleteDiscount(this);
        }
        allDiscounts.remove(this);
        File file = new File(Address.DISCOUNTS.get()+"\\"+this.getId()+".json");
        FileUtils.forceDelete(file);
        //file.delete();
    }

    public boolean isActive(LocalDate now) {
        if(now.compareTo(beginningDate)>0 && now.compareTo(endingDate)<0){
            return true;
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
