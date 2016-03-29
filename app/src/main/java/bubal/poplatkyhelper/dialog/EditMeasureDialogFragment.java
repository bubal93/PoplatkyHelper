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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;

import bubal.poplatkyhelper.R;
import bubal.poplatkyhelper.Utils;
import bubal.poplatkyhelper.model.ModelMeasure;

public class EditMeasureDialogFragment extends DialogFragment {

    public static EditMeasureDialogFragment newInstanse(ModelMeasure measure) {
        EditMeasureDialogFragment editMeasureDialogFragment = new EditMeasureDialogFragment();

        Bundle args = new Bundle();
        args.putFloat("value", measure.getValue());
        args.putLong("date", measure.getDate());
        args.putInt("measure_type", measure.getMeasureType());
        args.putLong("time_stamp", measure.getTimeStamp());

        editMeasureDialogFragment.setArguments(args);
        return editMeasureDialogFragment;
    }

    private EditingMeasureListener editingMeasureListener;

    public interface EditingMeasureListener {
        void onMeasureEdited(ModelMeasure updateMeasure);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            editingMeasureListener = (EditingMeasureListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement EditingMeasureListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle args = getArguments();
        float value = args.getFloat("value", 0);
        long date = args.getLong("date", 0);
        int measureType = args.getInt("measure_type", 0);
        long timeStamp = args.getLong("time_stamp", 0);

        final ModelMeasure measure = new ModelMeasure(value, date, measureType, timeStamp);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.dialog_editing_title);

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View container = inflater.inflate(R.layout.dialog_measure, null);

        final TextInputLayout tilValue = (TextInputLayout) container.findViewById(R.id.tilDialogMeasureValue);
        final EditText etValue = tilValue.getEditText();

        final TextInputLayout tilDate = (TextInputLayout) container.findViewById(R.id.tilDialogMeasureDate);
        final EditText etDate = tilDate.getEditText();

        Spinner spMeasureType = (Spinner) container.findViewById(R.id.spDialogMeasureType);

        if (etValue != null && etDate != null) {
            etValue.setText(String.valueOf(measure.getValue()));
            etValue.setSelection(etValue.length());
            etDate.setText(Utils.getDate(measure.getDate()));
        }


        tilValue.setHint(getResources().getString(R.string.measure_value));
        tilDate.setHint(getResources().getString(R.string.measure_date));

        builder.setView(container);


        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(measure.getDate());

        ArrayAdapter<String> measureTypeAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.measure_types));

        spMeasureType.setAdapter(measureTypeAdapter);

        spMeasureType.setSelection(measure.getMeasureType());

        spMeasureType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                measure.setMeasureType(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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
                editingMeasureListener.onMeasureEdited(measure);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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
                            } else if (Float.parseFloat(s.toString()) == 0 || Float.parseFloat(s.toString()) < 0) {
                                positiveButton.setEnabled(false);
                                tilValue.setError(getResources().getString(R.string.dialog_error_incorrect_value));
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
