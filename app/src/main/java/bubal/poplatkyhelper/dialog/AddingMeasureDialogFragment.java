package bubal.poplatkyhelper.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import bubal.poplatkyhelper.R;
import bubal.poplatkyhelper.Utils;
import bubal.poplatkyhelper.model.ModelMeasure;

public class AddingMeasureDialogFragment extends DialogFragment {

    private AddingMeasureListener addingMeasureListener;

    public interface AddingMeasureListener {
        void onMeasureAdded(ModelMeasure newMeasure);

        void onMeasureAddingCancel();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            addingMeasureListener = (AddingMeasureListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement AddingMeasureListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.dialog_title);

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View container = inflater.inflate(R.layout.dialog_measure, null);

        final TextInputLayout tilValue = (TextInputLayout) container.findViewById(R.id.tilDialogMeasureValue);
        final EditText etValue = tilValue.getEditText();

        final TextInputLayout tilDate = (TextInputLayout) container.findViewById(R.id.tilDialogMeasureDate);
        final EditText etDate = tilDate.getEditText();

        tilValue.setHint(getResources().getString(R.string.measure_value));
        tilDate.setHint(getResources().getString(R.string.measure_date));

        builder.setView(container);

        final ModelMeasure measure = new ModelMeasure();
        final Calendar calendar = Calendar.getInstance();


        if (etDate != null) {
            etDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //To prevent overlaying of floating labels component
                    if (etDate.length() == 0) {
                        etDate.setText("");
                    }
                    DialogFragment datePickerFragment = new DatePickerFragment() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            calendar.set(Calendar.YEAR, year);
                            calendar.set(Calendar.MONTH, monthOfYear);
                            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            etDate.setText(Utils.getDate(calendar.getTimeInMillis()));
                        }

                        @Override
                        public void onCancel(DialogInterface dialog) {
                            etDate.setText(null);
                        }
                    };
                    datePickerFragment.show(getFragmentManager(), "DatePickerFragment");
                }
            });
        }

        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (etValue != null && etDate != null) {

                    measure.setValue(Float.parseFloat(etValue.getText().toString()));
                    //Setting the current date if no date is entered
                    measure.setDate(calendar.getTimeInMillis());
                }
                addingMeasureListener.onMeasureAdded(measure);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addingMeasureListener.onMeasureAddingCancel();
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                final Button positiveButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                if (etValue != null) {

                    if (etValue.length() == 0) {
                        positiveButton.setEnabled(false);
                        tilValue.setError(getResources().getString(R.string.dialog_error_empty_value));
                    }


                    etValue.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (s.length() == 0) {
                                positiveButton.setEnabled(false);
                                tilValue.setError(getResources().getString(R.string.dialog_error_empty_value));
                            } else {
                                positiveButton.setEnabled(true);
                                tilValue.setErrorEnabled(false);
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                        }
                    });
                }
            }
        });

        return alertDialog;
    }
}
