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

import bubal.poplatkyhelper.MainActivity;
import bubal.poplatkyhelper.R;
import bubal.poplatkyhelper.adapter.HistoryAdapter;
import bubal.poplatkyhelper.database.DBHelper;
import bubal.poplatkyhelper.model.ModelMeasure;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {

    private RecyclerView rvHistory;
    private RecyclerView.LayoutManager layoutManager;

    private HistoryAdapter adapter;

    public MainActivity activity;


    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null) {
            activity = (MainActivity) getActivity();
        }

        addMeasureFromDB();
    }

    public void addMeasureFromDB() {
        List<ModelMeasure> measures = new ArrayList<>();
        measures.addAll(activity.dbHelper.query().getMeasures(null, null, DBHelper.MEASURE_TYPE_COLUMN));

        for (int i = 0; i < measures.size(); i++) {
            addMeasure(measures.get(i), false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_history, container, false);

        rvHistory = (RecyclerView) rootView.findViewById(R.id.rvHistory);

        layoutManager = new LinearLayoutManager(getActivity());

        rvHistory.setLayoutManager(layoutManager);

        adapter = new HistoryAdapter();
        rvHistory.setAdapter(adapter);

        return rootView;
    }

    public void addMeasure(ModelMeasure newMeasure, boolean saveToDB) {
        int position = -1;

        for (int i = 0; i < adapter.getItemCount(); i++) {

            if (adapter.getItem(i).isMeasure()) {
                ModelMeasure measure = (ModelMeasure) adapter.getItem(i);

                if (newMeasure.getDate() < measure.getDate()) {
                    position = i;
                    break;
                }
            }
        }
        if (position != -1) {
            adapter.addItem(position, newMeasure);
        } else {
            adapter.addItem(newMeasure);
        }

        if (saveToDB) {
            activity.dbHelper.saveMeasure(newMeasure);
        }
    }
}
