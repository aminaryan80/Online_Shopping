package Models.Account;

import Models.Shop.SellingLog;

import java.util.ArrayList;

public class Seller extends Account {
    private String companyName;
    private ArrayList<SellingLog> allLogs;

    public String getCompanyName() {
        return companyName;
    }

    @Override
    public String toString() {
//        return super.toString() + "Seller{" +
//                "companyName='" + companyName + '\'' +
//                '}';
    }
}
