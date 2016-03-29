package bubal.poplatkyhelper;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import bubal.poplatkyhelper.adapter.TabAdapter;
import bubal.poplatkyhelper.database.DBHelper;
import bubal.poplatkyhelper.dialog.AddingMeasureDialogFragment;
import bubal.poplatkyhelper.dialog.EditMeasureDialogFragment;
import bubal.poplatkyhelper.fragment.HistoryFragment;
import bubal.poplatkyhelper.fragment.MeasureFragment;
import bubal.poplatkyhelper.model.ModelMeasure;

public class MainActivity extends AppCompatActivity
        implements AddingMeasureDialogFragment.AddingMeasureListener,
        EditMeasureDialogFragment.EditingMeasureListener {

    FragmentManager fragmentManager;

    TabAdapter tabAdapter;

    MeasureFragment historyFragment;

    public DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(getApplicationContext());

        fragmentManager = getFragmentManager();

        setUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_settings:
                Intent intent = new Intent();
                intent.setClass(this, SettingsActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        //Get preferences for tariffs
        float electricityTariff = Float.parseFloat(sharedPreferences.getString(getString(R.string.pref_key_tariffElectricity), "0"));
        float waterTariff = Float.parseFloat(sharedPreferences.getString(getString(R.string.pref_key_tariffWater), "0"));
        float naturalGasTariff = Float.parseFloat(sharedPreferences.getString(getString(R.string.pref_key_tariffNaturalGas), "0"));
    }

    private void setUI() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            toolbar.setTitleTextColor(getResources().getColor(R.color.white));
            setSupportActionBar(toolbar);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        if (tabLayout != null) {

            tabLayout.addTab(tabLayout.newTab().setText(R.string.history_tab));
            tabLayout.addTab(tabLayout.newTab().setText(R.string.statistics_tab));

            final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
            tabAdapter = new TabAdapter(fragmentManager, 2);

            if (viewPager != null) {

                viewPager.setAdapter(tabAdapter);
                viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

                tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        viewPager.setCurrentItem(tab.getPosition());
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                    }
                });
            }
        }

        historyFragment = (HistoryFragment) tabAdapter.getItem(TabAdapter.HISTORY_FRAGMENT_MEASURE_POSITION);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment addingMeasureDialogFragment = new AddingMeasureDialogFragment();
                    addingMeasureDialogFragment.show(fragmentManager, "AddingMeasureDialogFragment");
                }
            });
        }
    }

    @Override
    public void onMeasureAdded(ModelMeasure newMeasure) {
        Toast.makeText(this, "Measure added", Toast.LENGTH_SHORT).show();

        historyFragment.addMeasure(newMeasure, true);
    }

    @Override
    public void onMeasureAddingCancel() {
        Toast.makeText(this, "Measure adding canceled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMeasureEdited(ModelMeasure updateMeasure) {
        historyFragment.updateMeasure(updateMeasure);
        dbHelper.update().measure(updateMeasure);
    }
}
