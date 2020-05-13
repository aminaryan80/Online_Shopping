package Models;

public enum Address {
    ACCOUNTS("database"+"\\"+"accounts"),
    DISCOUNTS("database"+"\\"+"discounts"),
    PRODUCTS("database"+"\\"+"products"),
    CATEGORIES("database"+"\\"+"categories"),
    REQUESTS("database"+"\\"+"requests"),
    AUCTIONS("database"+"\\"+"auctions");
    private final String address;
    Address(String address) {
        this.address = address;
    }
    public String get() {
        return address;
    }
}
