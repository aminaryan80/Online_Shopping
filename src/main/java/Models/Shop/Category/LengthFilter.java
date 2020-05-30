package Models.Shop.Category;

public class LengthFilter {

    private String field;
    private String minValue;
    private String maxValue;

    public LengthFilter(String field, String minValue, String maxValue) {
        this.field = field;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public String getField() {
        return field;
    }

    public String getMinValue() {
        return minValue;
    }

    public String getMaxValue() {
        return maxValue;
    }

    @Override
    public String toString() {
        return "LengthFilter{" +
                "field='" + field + '\'' +
                ", minValue='" + minValue + '\'' +
                ", maxValue='" + maxValue + '\'' +
                '}';
    }
}
