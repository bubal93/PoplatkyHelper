package bubal.poplatkyhelper.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import bubal.poplatkyhelper.fragment.HistoryFragment;
import bubal.poplatkyhelper.fragment.StatisticsFragment;

public class TabAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;

    public static final int HISTORY_FRAGMENT_MEASURE_POSITION = 0;

    private HistoryFragment historyFragment;
    private StatisticsFragment statisticsFragment;

    public TabAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
        historyFragment = new HistoryFragment();
        statisticsFragment = new StatisticsFragment();
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return historyFragment;
            case 1:
                return statisticsFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
