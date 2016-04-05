package bubal.poplatkyhelper.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bubal.poplatkyhelper.MainActivity;
import bubal.poplatkyhelper.R;
import bubal.poplatkyhelper.adapter.CategoryAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticsFragment extends Fragment {

    protected RecyclerView recyclerView;
    protected RecyclerView.LayoutManager layoutManager;

    protected CategoryAdapter adapter;

    public MainActivity activity;


    public StatisticsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_statistics, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvStatistics);

        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);

        adapter = new CategoryAdapter();
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    public void addMeasureFromDB(){


    }

}
