package Models.Shop;

public class Feature {
    private String name;
    private String value;

    public Feature(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
