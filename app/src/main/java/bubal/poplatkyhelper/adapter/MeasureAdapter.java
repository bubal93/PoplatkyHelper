package bubal.poplatkyhelper.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bubal.poplatkyhelper.fragment.MeasureFragment;
import bubal.poplatkyhelper.model.Item;

public abstract class MeasureAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Item> items;

    MeasureFragment measureFragment;

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

    public void removeItem(int location) {
        if (location >= 0 && location <= getItemCount() - 1) {
            items.remove(location);
            notifyItemRemoved(location);
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

    public MeasureFragment getMeasureFragment() {
        return measureFragment;
    }
}
