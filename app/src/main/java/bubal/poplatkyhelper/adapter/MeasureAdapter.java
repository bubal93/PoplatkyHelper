package bubal.poplatkyhelper.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bubal.poplatkyhelper.fragment.MeasureFragment;
import bubal.poplatkyhelper.model.Item;
import bubal.poplatkyhelper.model.ModelMeasure;
import bubal.poplatkyhelper.model.ModelSeparator;

public abstract class MeasureAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Item> items;

    MeasureFragment measureFragment;

    public boolean containsSeparatorElectricity;
    public boolean containsSeparatorWater;
    public boolean containsSeparatorNaturalGas;


    public MeasureAdapter(MeasureFragment measureFragment) {
        this.measureFragment = measureFragment;
        items = new ArrayList<>();
    }

    public Item getItem(int position) {
        return items.get(position);
    }

    public void addItem(Item item) {
        items.add(item);
        notifyItemInserted(getItemCount() - 1);
    }

    public void addItem(int location, Item item) {
        items.add(location, item);
        notifyItemInserted(location);
    }

    public void updateMeasure(ModelMeasure newMeasure) {
        for (int i = 0; i < getItemCount(); i++) {
            if (getItem(i).isMeasure()) {
                ModelMeasure measure = (ModelMeasure) getItem(i);
                if (newMeasure.getTimeStamp() == measure.getTimeStamp()) {
                    removeItem(i);
                    getMeasureFragment().addMeasure(newMeasure, false);
                }
            }
        }
    }

    public void removeItem(int location) {
        if (location >= 0 && location <= getItemCount() - 1) {
            items.remove(location);
            notifyItemRemoved(location);
        }

        //Removing separator
        if (location - 1 >= 0 && location <= getItemCount() - 1) {
            if (!getItem(location).isMeasure() && !getItem(location - 1).isMeasure()) {

                ModelSeparator separator = (ModelSeparator) getItem(location - 1);
                checkSeparators(separator.getType());

                items.remove(location - 1);
                notifyItemRemoved(location - 1);
            }
        } else if (getItemCount() - 1 >= 0 && !getItem(getItemCount() - 1).isMeasure()) {
            ModelSeparator separator = (ModelSeparator) getItem(getItemCount() - 1);
            checkSeparators(separator.getType());

            int locationTemp = getItemCount() - 1;
            items.remove(locationTemp);
            notifyItemRemoved(locationTemp);
        }
    }

    public void checkSeparators(int type) {
        switch (type) {
            case ModelSeparator.TYPE_ELECTRICITY:
                containsSeparatorElectricity = false;
                break;
            case ModelSeparator.TYPE_WATER:
                containsSeparatorWater = false;
                break;
            case ModelSeparator.TYPE_NATURAL_GAS:
                containsSeparatorNaturalGas = false;
                break;
        }
    }

    public void removeAllItems() {
        if (getItemCount() != 0) {
            items = new ArrayList<>();
            notifyDataSetChanged();
            containsSeparatorElectricity = false;
            containsSeparatorWater = false;
            containsSeparatorNaturalGas = false;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    protected class MeasureViewHolder extends RecyclerView.ViewHolder {

        protected TextView value;
        protected TextView date;

        public MeasureViewHolder(View itemView, TextView value, TextView date) {
            super(itemView);
            this.value = value;
            this.date = date;
        }
    }

    protected class SeparatorViewHolder extends RecyclerView.ViewHolder {

        protected TextView type;

        public SeparatorViewHolder(View itemView, TextView type) {
            super(itemView);
            this.type = type;
        }
    }

    public MeasureFragment getMeasureFragment() {
        return measureFragment;
    }
}
