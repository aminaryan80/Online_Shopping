package Models;

public enum Address {
    CUSTOMERS("database"+"\\"+"accounts"+"\\"+"customers"),
    SELLERS("database"+"\\"+"accounts"+"\\"+"sellers"),
    PRINCIPALS("database"+"\\"+"accounts"+"\\"+"principals"),

    DISCOUNTS("database"+"\\"+"discounts"),

    PRODUCTS("database"+"\\"+"products"),

    CATEGORIES("database"+"\\"+"categories"),
    RATES("database"+"\\"+"rates"),
    COMMENTS("database"+"\\"+"comments"),

    REQUESTS("database"+"\\"+"requests"),
    ADD_PRODUCT_REQUESTS("database"+"\\"+"requests"+"\\"+"add product requests"),
    EDIT_PRODUCT_REQUESTS("database"+"\\"+"requests"+"\\"+"edit product requests"),
    ADD_OFF_REQUESTS("database"+"\\"+"requests"+"\\"+"add off requests"),
    DELETE_PRODUCT_REQUEST("database" + "\\" + "requests" + "\\" + "delete product requests"),
    ADD_SELLER_REQUEST("database" + "\\" + "requests" + "\\" + "add seller requests"),
    EDIT_OFF_REQUESTS("database"+"\\"+"requests"+"\\"+"edit off requests"),
    AUCTIONS("database"+"\\"+"auctions"),
    IDNETITIES("database" + "\\" + "identities")
    ;
    private final String address;
    Address(String address) {
        this.address = address;
    }
    public String get() {
        return address;
    }
}
