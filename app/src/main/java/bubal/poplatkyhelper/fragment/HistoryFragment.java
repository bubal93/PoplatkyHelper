package bubal.poplatkyhelper.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import bubal.poplatkyhelper.R;
import bubal.poplatkyhelper.adapter.HistoryAdapter;
import bubal.poplatkyhelper.database.DBHelper;
import bubal.poplatkyhelper.model.ModelMeasure;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends MeasureFragment {

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvHistory);

        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);

        adapter = new HistoryAdapter(this);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    public void addMeasureFromDB() {
        adapter.removeAllItems();

        List<ModelMeasure> measures = new ArrayList<>();
        measures.addAll(activity.dbHelper.query().getMeasures(null, null, DBHelper.MEASURE_TYPE_COLUMN));

        for (int i = 0; i < measures.size(); i++) {
            addMeasure(measures.get(i), false);
        }
    }
}
