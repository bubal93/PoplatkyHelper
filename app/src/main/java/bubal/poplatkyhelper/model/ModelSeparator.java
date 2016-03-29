package bubal.poplatkyhelper.model;

import bubal.poplatkyhelper.R;

public class ModelSeparator implements Item {

    public static final int TYPE_ELECTRICITY = R.string.separator_electricity;
    public static final int TYPE_WATER = R.string.separator_water;
    public static final int TYPE_NATURAL_GAS = R.string.separator_naturalGas;

    private int type;

    public ModelSeparator(int type) {
        this.type = type;
    }

    @Override
    public boolean isMeasure() {
        return false;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTypeColor() {
        switch (getType()) {
            case TYPE_ELECTRICITY:
                return R.color.type_electricity;
            case TYPE_WATER:
                return R.color.type_water;
            case TYPE_NATURAL_GAS:
                return R.color.type_natural_gas;
            default:
                return 0;
        }
    }
}
