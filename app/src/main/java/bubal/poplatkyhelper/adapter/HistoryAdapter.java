package bubal.poplatkyhelper.adapter;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bubal.poplatkyhelper.R;
import bubal.poplatkyhelper.Utils;
import bubal.poplatkyhelper.model.Item;
import bubal.poplatkyhelper.model.ModelMeasure;

public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_MEASURE = 0;
    private static final int TYPE_SEPARATOR = 1;

    List<Item> items = new ArrayList<>();


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

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).isMeasure()) {
            return TYPE_MEASURE;
        } else {
            return TYPE_SEPARATOR;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case TYPE_MEASURE:
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.model_measure, parent, false);
                TextView value = (TextView) view.findViewById(R.id.tvMeasureValue);
                TextView date = (TextView) view.findViewById(R.id.tvMeasureDate);

                return new MeasureViewHolder(view, value, date);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Item item = items.get(position);

        if (item.isMeasure()) {
            holder.itemView.setEnabled(true);
            ModelMeasure measure = (ModelMeasure) item;
            MeasureViewHolder measureViewHolder = (MeasureViewHolder) holder;

            View itemView=measureViewHolder.itemView;
            Resources resources=itemView.getResources();

            String valueFloat = Float.toString(measure.getValue());
            measureViewHolder.value.setText(valueFloat);

            measureViewHolder.date.setText(Utils.getDate(measure.getDate()));


            itemView.setVisibility(View.VISIBLE);

            measureViewHolder.value.setTextColor(resources.getColor(R.color.primary_text));
            measureViewHolder.date.setTextColor(resources.getColor(R.color.secondary_text));
        }

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
}
