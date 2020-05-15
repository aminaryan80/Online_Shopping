package Models.Shop;

import Models.Account.Customer;
import Models.Address;
import Models.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Discount {
    private static ArrayList<Discount> allDiscounts = new ArrayList<>();
    private String id;
    private Date beginningDate;
    private Date endingDate;
    private int discountPercent;
    private double maximumDiscount;
    private int discountUseCount;
    private List<Customer> allCustomers;

    public Discount(String id, Date beginningDate, Date endingDate, int discountPercent, double maximumDiscount, int discountUseCount, List<Customer> allCustomers) {
        this.id = id;
        this.beginningDate = beginningDate;
        this.endingDate = endingDate;
        this.discountPercent = discountPercent;
        this.maximumDiscount = maximumDiscount;
        this.discountUseCount = discountUseCount;
        this.allCustomers = allCustomers;
        allDiscounts.add(this);
    }

    public static boolean hasDiscountWithId(String id) {
        return true;
    }

    public static Discount getDiscountById(String id) {
        return null;
    }

    public static void deleteDiscount(Discount discount) {

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

    public static ArrayList<String> viewDiscountInShort() {
        return null;
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

    public static void open(){
        File folder = new File(Address.DISCOUNTS.get());
        if(!folder.exists()) folder.mkdirs();
        else {
            for (File file : folder.listFiles()) {
                allDiscounts.add(open(file));
            }
        }
    }

    public static Discount open(File file){
        StringBuilder json = new StringBuilder();
        try {
            Scanner reader = new Scanner(file);
            while (reader.hasNext()) {
                json.append(reader.next());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return Gson.INSTANCE.get().fromJson(json.toString(),Discount.class);
    }

    public static void save(){
        for (Discount discount : allDiscounts) {
            save(discount);
        }
    }

    public static void save(Discount discount){
        try {
            String jsonAccount = Gson.INSTANCE.get().toJson(discount);
            try {
                FileWriter file = new FileWriter(Address.DISCOUNTS.get() +"\\"+discount.getId()+".json");
                file.write(jsonAccount);
                file.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
