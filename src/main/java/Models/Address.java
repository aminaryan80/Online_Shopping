package Models;

public enum Address {
    ACCOUNTS("database"+"\\"+"accounts");
    private final String address;
    Address(String address) {
        this.address = address;
    }
    public String get() {
        return address;
    }
}
