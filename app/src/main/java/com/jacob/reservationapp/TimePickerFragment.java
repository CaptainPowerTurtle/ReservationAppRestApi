package com.jacob.reservationapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment {
    static final int START_TIME = 1;
    static final int END_TIME = 2;

    private int mChosenTime;

    static int cur = 0;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mChosenTime = bundle.getInt("TIME", 1);
        }


        switch (mChosenTime) {

            case START_TIME:
                cur = START_TIME;
                return new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener) getActivity(), hour, minute, true);

            case END_TIME:
                cur = END_TIME;
                return new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener) getActivity(), hour, minute, true);

        }
        return null;
    }
}
