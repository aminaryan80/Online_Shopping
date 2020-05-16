package Models;

public enum Address {
    CUSTOMERS("database"+"\\"+"accounts"+"\\"+"customers"),
    SELLERS("database"+"\\"+"accounts"+"\\"+"sellers"),
    PRINCIPALS("database"+"\\"+"accounts"+"\\"+"principals"),
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
