package bubal.poplatkyhelper.model;

import java.util.Date;

public class ModelMeasure implements Item {

    public static final int MEASURE_TYPE_ELECTRICITY = 0;
    public static final int MEASURE__TYPE_WATER = 1;
    public static final int MEASURE__TYPE_NATURAL_GAS = 2;

    private float value;
    private long date;
    private int measureType;
    private long timeStamp;
    private int typeStatus;

    public int getTypeStatus() {
        return typeStatus;
    }

    public void setTypeStatus(int typeStatus) {
        this.typeStatus = typeStatus;
    }

    public ModelMeasure() {
        this.timeStamp = new Date().getTime();
    }

    public ModelMeasure(float value, long date, int measureType, long timeStamp) {
        this.value = value;
        this.date = date;
        this.measureType = measureType;
        this.timeStamp = timeStamp;
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

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
