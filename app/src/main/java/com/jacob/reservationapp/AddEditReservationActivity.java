package com.jacob.reservationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class AddEditReservationActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public static final String EXTRA_ID = "com.jacob.reservationapp.EXTRA_ID";
    public static final String EXTRA_ROOMID = "com.jacob.reservationapp.EXTRA_ROOMID";
    public static final String EXTRA_PURPOSE = "com.jacob.reservationapp.EXTRA_PURPOSE";
    public static final String EXTRA_FROMTIME = "com.jacob.reservationapp.EXTRA_FROMTIME";
    public static final String EXTRA_TOTIME = "com.jacob.reservationapp.EXTRA_TOTIME";
    public static final String EXTRA_USERID = "com.jacob.reservationapp.EXTRA_USERID";

    private NumberPicker numberPickerRoomId;
    private NumberPicker numberPickerUserId;
    private EditText editTextPupose;
    private EditText editTextFromTime;
    private EditText editTextToTime;
    private long fromTime;
    private long toTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reservation);

        numberPickerRoomId = findViewById(R.id.number_picker_roomId);
        editTextPupose = findViewById(R.id.edit_text_pupose);
        numberPickerUserId = findViewById(R.id.number_picker_userId);
        editTextFromTime = findViewById(R.id.edit_text_fromTime);
        editTextToTime = findViewById(R.id.edit_text_toTime);

        numberPickerRoomId.setMinValue(1);
        numberPickerRoomId.setMaxValue(10);
        numberPickerUserId.setMinValue(1);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Reservation");
            editTextPupose.setText(intent.getStringExtra(EXTRA_PURPOSE));
            numberPickerRoomId.setValue(intent.getIntExtra(EXTRA_ROOMID, 1));
            editTextFromTime.setText(intent.getStringExtra(EXTRA_FROMTIME));
            editTextToTime.setText(intent.getStringExtra(EXTRA_TOTIME));
        }else{
            setTitle("Add Reservation");
        }

        EditText editTextFromTime = findViewById(R.id.edit_text_fromTime);
        editTextFromTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("DATE", 1);

                DialogFragment datePickerFromTime = new DatePickerFragment();
                datePickerFromTime.setArguments(bundle);
                datePickerFromTime.show(getSupportFragmentManager(), "date picker");
            }
        });
        EditText editTextToTime = findViewById(R.id.edit_text_toTime);
        editTextToTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("DATE", 2);
                DialogFragment datePickerToTime = new DatePickerFragment();
                datePickerToTime.setArguments(bundle);
                datePickerToTime.show(getSupportFragmentManager(), "date picker");
            }
        });
    }

    private void saveReservation(){
        int roomId = numberPickerRoomId.getValue();
        String roomPurpose = editTextPupose.getText().toString();
        int userId = numberPickerUserId.getValue();
//        if (fromTime > toTime){
//            Toast.makeText(this, "From Time cannot be larger than To TIme", Toast.LENGTH_SHORT).show();
//            return;
//        }
        Intent data = new Intent();
        data.putExtra(EXTRA_ROOMID, roomId);
        data.putExtra(EXTRA_PURPOSE, roomPurpose);
        data.putExtra(EXTRA_FROMTIME, fromTime);
        data.putExtra(EXTRA_TOTIME, toTime);
        data.putExtra(EXTRA_USERID, userId);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1){
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_reservation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_reservation:
                saveReservation();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance().format(c.getTime());
        if (DatePickerFragment.START_DATE == DatePickerFragment.cur){
            EditText editTextFromTime = findViewById(R.id.edit_text_fromTime);
            editTextFromTime.setText(currentDateString);
            fromTime = c.getTimeInMillis();
        } else {
            EditText editTextToTime = findViewById(R.id.edit_text_toTime);
            editTextToTime.setText(currentDateString);
            toTime = c.getTimeInMillis();
        }
    }
}
