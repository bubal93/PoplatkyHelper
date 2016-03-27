package bubal.poplatkyhelper.model;

public class ModelMeasure implements Item {

    public static final int MEASURE_TYPE_ELECTRICITY = 0;
    public static final int MEASURE__TYPE_WATER = 1;
    public static final int MEASURE__TYPE_NATURAL_GAS = 2;

    public static final String[] MEASURE_TYPES = {"Electricity", "Water", "Natural gas"};

    private float value;
    private long date;
    private int measureType;

    public ModelMeasure() {
    }

    public ModelMeasure(float value, long date, int measureType) {
        this.value = value;
        this.date = date;
        this.measureType = measureType;
    }

    @Override
    public boolean isMeasure() {
        return true;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getMeasureType() {
        return measureType;
    }

    public void setMeasureType(int measureType) {
        this.measureType = measureType;
    }
}
