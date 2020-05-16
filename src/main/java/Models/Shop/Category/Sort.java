package Models.Shop.Category;

public class Sort {
    private String field;
    private boolean isAscending;

    public Sort(String field, boolean isAscending) {
        this.field = field;
        this.isAscending = isAscending;
    }

    public String getField() {
        return field;
    }

    public boolean isAscending() {
        return isAscending;
    }

    @Override
    public String toString() {
        return "Sort{" +
                "field='" + field + '\'' +
                ", isAscending=" + isAscending +
                '}';
    }
}
