package bubal.poplatkyhelper.model;

public class ModelMeasure implements Item {

    private float value;
    private long date;

    public ModelMeasure(){}

    public ModelMeasure(float value, long date) {
        this.value = value;
        this.date = date;
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
}
