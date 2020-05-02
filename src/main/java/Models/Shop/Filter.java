package Models.Shop;

public class Filter {
    private String field;
    private String value;

    public Filter(String field, String value) {
        this.field = field;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Filter{" +
                "field='" + field + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
