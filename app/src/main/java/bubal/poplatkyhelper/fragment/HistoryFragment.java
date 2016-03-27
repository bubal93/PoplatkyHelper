package bubal.poplatkyhelper.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bubal.poplatkyhelper.R;
import bubal.poplatkyhelper.adapter.HistoryAdapter;
import bubal.poplatkyhelper.model.ModelMeasure;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {

    private RecyclerView rvHistory;
    private RecyclerView.LayoutManager layoutManager;

    private HistoryAdapter adapter;


    public HistoryFragment() {
        // Required empty public constructor
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

    public void addMeasure(ModelMeasure newMeasure) {
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
    }
}
