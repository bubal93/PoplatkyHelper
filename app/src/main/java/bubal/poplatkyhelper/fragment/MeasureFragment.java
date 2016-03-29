package bubal.poplatkyhelper.fragment;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import bubal.poplatkyhelper.MainActivity;
import bubal.poplatkyhelper.R;
import bubal.poplatkyhelper.adapter.MeasureAdapter;
import bubal.poplatkyhelper.dialog.EditMeasureDialogFragment;
import bubal.poplatkyhelper.model.Item;
import bubal.poplatkyhelper.model.ModelMeasure;
import bubal.poplatkyhelper.model.ModelSeparator;

public abstract class MeasureFragment extends Fragment {

    protected RecyclerView recyclerView;
    protected RecyclerView.LayoutManager layoutManager;

    protected MeasureAdapter adapter;

    public MainActivity activity;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null) {
            activity = (MainActivity) getActivity();
        }

        addMeasureFromDB();
    }

    public void updateMeasure(ModelMeasure measure) {
        adapter.updateMeasure(measure);
    }

    public void addMeasure(ModelMeasure newMeasure, boolean saveToDB) {
        int position = -1;
        ModelSeparator separator = null;

        for (int i = 0; i < adapter.getItemCount(); i++) {

            if (adapter.getItem(i).isMeasure()) {
                ModelMeasure measure = (ModelMeasure) adapter.getItem(i);

                if (newMeasure.getDate() < measure.getDate()) {
                    position = i;
                    break;
                }
            }
        }

        if (newMeasure.getMeasureType() == ModelMeasure.MEASURE_TYPE_ELECTRICITY) {

            newMeasure.setTypeStatus(ModelSeparator.TYPE_ELECTRICITY);
            if (!adapter.containsSeparatorElectricity) {
                adapter.containsSeparatorElectricity = true;
                separator = new ModelSeparator(ModelSeparator.TYPE_ELECTRICITY);
            }
        } else if (newMeasure.getMeasureType() == ModelMeasure.MEASURE__TYPE_WATER) {

            newMeasure.setTypeStatus(ModelSeparator.TYPE_WATER);
            if (!adapter.containsSeparatorWater) {
                adapter.containsSeparatorWater = true;
                separator = new ModelSeparator(ModelSeparator.TYPE_WATER);
            }
        } else if (newMeasure.getMeasureType() == ModelMeasure.MEASURE__TYPE_NATURAL_GAS) {

            newMeasure.setTypeStatus(ModelSeparator.TYPE_NATURAL_GAS);
            if (!adapter.containsSeparatorNaturalGas) {
                adapter.containsSeparatorNaturalGas = true;
                separator = new ModelSeparator(ModelSeparator.TYPE_NATURAL_GAS);
            }
        }


        if (position != -1) {

            if (!adapter.getItem(position - 1).isMeasure()) {
                if (position - 2 >= 0 && adapter.getItem(position - 2).isMeasure()) {
                    ModelMeasure measure = (ModelMeasure) adapter.getItem(position - 2);
                    if (measure.getTypeStatus() == newMeasure.getTypeStatus()) {
                        position -= 1;
                    }
                } else if (position - 2 < 0 && newMeasure.getDate() == 0) {
                    position -= 1;
                }
            }

            if (separator != null) {
                adapter.addItem(position - 1, separator);
            }

            adapter.addItem(position, newMeasure);

        } else {
            if (separator != null) {
                adapter.addItem(separator);
            }

            adapter.addItem(newMeasure);
        }

        if (saveToDB) {
            activity.dbHelper.saveMeasure(newMeasure);
        }
    }

    public void removeMeasureDialog(final int location) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

        dialogBuilder.setMessage(R.string.dialog_removing_message);

        Item item = adapter.getItem(location);

        if (item.isMeasure()) {
            ModelMeasure removingMeasure = (ModelMeasure) item;

            final long timeStamp = removingMeasure.getTimeStamp();
            final boolean[] isRemoved = {false};

            dialogBuilder.setPositiveButton(R.string.dialog_removing_yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    adapter.removeItem(location);
                    isRemoved[0] = true;

                    //Snackbar for restoring deleted measure
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.coordinator),
                            R.string.removed, Snackbar.LENGTH_LONG);

                    snackbar.setAction(R.string.dialog_cancel, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addMeasure(activity.dbHelper.query().getMeasure(timeStamp), false);
                            isRemoved[0] = false;
                        }
                    });
                    snackbar.getView().addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                        @Override
                        public void onViewAttachedToWindow(View v) {
                        }

                        @Override
                        public void onViewDetachedFromWindow(View v) {
                            //Completely removing measure from database if there is no snackbar cancel clicking
                            if (isRemoved[0]) {
                                activity.dbHelper.removeMeasure(timeStamp);
                            }
                        }
                    });

                    //Changing the color of snackbar and text
                    View snackBarView = snackbar.getView();
                    snackBarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.setActionTextColor(getResources().getColor(R.color.white));

                    snackbar.show();

                    dialog.dismiss();
                }
            });

            dialogBuilder.setNegativeButton(R.string.dialog_removing_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
        }
        dialogBuilder.show();
    }

    public void showMeasureEditDialog(ModelMeasure measure){
        DialogFragment editingMeasureDialog= EditMeasureDialogFragment.newInstanse(measure);
        editingMeasureDialog.show(getActivity().getFragmentManager(), "EditMeasureDialogFragment");
    }

    public abstract void addMeasureFromDB();
}
