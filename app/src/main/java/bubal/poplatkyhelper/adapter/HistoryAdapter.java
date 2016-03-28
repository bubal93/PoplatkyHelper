package bubal.poplatkyhelper.adapter;

import android.content.res.Resources;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bubal.poplatkyhelper.R;
import bubal.poplatkyhelper.Utils;
import bubal.poplatkyhelper.fragment.HistoryFragment;
import bubal.poplatkyhelper.model.Item;
import bubal.poplatkyhelper.model.ModelMeasure;

public class HistoryAdapter extends MeasureAdapter {

    private static final int TYPE_MEASURE = 0;
    private static final int TYPE_SEPARATOR = 1;

    HistoryFragment historyFragment;

    public HistoryAdapter(HistoryFragment measureFragment) {
        super(measureFragment);
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
            final MeasureViewHolder measureViewHolder = (MeasureViewHolder) holder;

            View itemView = measureViewHolder.itemView;
            Resources resources = itemView.getResources();

            String valueFloat = Float.toString(measure.getValue());
            measureViewHolder.value.setText(valueFloat);

            measureViewHolder.date.setText(Utils.getDate(measure.getDate()));


            itemView.setVisibility(View.VISIBLE);

            measureViewHolder.value.setTextColor(resources.getColor(R.color.primary_text));
            measureViewHolder.date.setTextColor(resources.getColor(R.color.secondary_text));

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getMeasureFragment().removeMeasureDialog(measureViewHolder.getLayoutPosition());
                        }
                    }, 300);

                    return true;
                }
            });
        }

    }

    public HistoryFragment getHistoryFragment(){
        return this.historyFragment;
    }
}
