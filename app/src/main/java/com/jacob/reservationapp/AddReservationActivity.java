package com.jacob.reservationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class AddReservationActivity extends AppCompatActivity {
    public static final String EXTRA_ROOMID = "com.jacob.reservationapp.EXTRA_ROOMID";
    public static final String EXTRA_PURPOSE = "com.jacob.reservationapp.EXTRA_PURPOSE";
    public static final String EXTRA_FROMTIME = "com.jacob.reservationapp.EXTRA_FROMTIME";
    public static final String EXTRA_TOTIME = "com.jacob.reservationapp.EXTRA_TOTIME";
    public static final String EXTRA_USERID = "com.jacob.reservationapp.EXTRA_USERID";

    private NumberPicker numberPickerRoomId;
    private NumberPicker numberPickerUserId;
    private EditText editTextPupose;
    private DatePicker datePickerFromTime;
    private DatePicker datePickerToTime;
    private Calendar calendarFromTime = new GregorianCalendar(datePickerFromTime.getYear(), datePickerFromTime.getMonth(), datePickerFromTime.getDayOfMonth());
    private Calendar calenderToTime = new GregorianCalendar(datePickerToTime.getYear(), datePickerToTime.getMonth(), datePickerToTime.getDayOfMonth());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reservation);

        numberPickerRoomId = findViewById(R.id.number_picker_roomId);
        editTextPupose = findViewById(R.id.edit_text_pupose);
        datePickerFromTime = findViewById(R.id.date_picker_fromTime);
        datePickerToTime = findViewById(R.id.date_picker_toTime);
        numberPickerUserId = findViewById(R.id.number_picker_userId);

        datePickerFromTime.setMinDate(System.currentTimeMillis() - 1000);
        datePickerToTime.setMinDate(System.currentTimeMillis() - 1000);
        numberPickerRoomId.setMinValue(1);
        numberPickerRoomId.setMaxValue(10);
        numberPickerUserId.setMinValue(1);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Reservation");
    }

    private void saveReservation(){
        int roomId = numberPickerRoomId.getValue();
        String roomPurpose = editTextPupose.getText().toString();
        long fromTime = calendarFromTime.getTimeInMillis();
        long toTime = calenderToTime.getTimeInMillis();
        int userId = numberPickerUserId.getValue();
        if (fromTime > toTime){
            Toast.makeText(this, "From Time cannot be larger than To TIme", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_ROOMID, roomId);
        data.putExtra(EXTRA_PURPOSE, roomPurpose);
        data.putExtra(EXTRA_FROMTIME, fromTime);
        data.putExtra(EXTRA_TOTIME, toTime);
        data.putExtra(EXTRA_USERID, userId);

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
}
