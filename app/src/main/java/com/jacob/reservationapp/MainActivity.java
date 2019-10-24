package com.jacob.reservationapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ReservationViewModel reservationViewModel;
    public static final int ADD_RESERVATION_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddReservation = findViewById(R.id.button_add_reservation);
        buttonAddReservation.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, AddReservationActivity.class);
                startActivityForResult(intent, ADD_RESERVATION_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ReservationAdapter adapter = new ReservationAdapter();
        recyclerView.setAdapter(adapter);

        reservationViewModel = ViewModelProviders.of(this).get(ReservationViewModel.class);
        reservationViewModel.getAllReservations().observe(this, new Observer<List<Reservation>>() {
            @Override
            public void onChanged(List<Reservation> reservations) {
                adapter.setReservations(reservations);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_RESERVATION_REQUEST && resultCode == RESULT_OK){
            int roomId = data.getIntExtra(AddReservationActivity.EXTRA_ROOMID, 1);
            String purpose = data.getStringExtra(AddReservationActivity.EXTRA_PURPOSE);
            int fromTime = data.getIntExtra(AddReservationActivity.EXTRA_FROMTIME, 1);
            int toTime = data.getIntExtra(AddReservationActivity.EXTRA_TOTIME, 1);
            int userId = data.getIntExtra(AddReservationActivity.EXTRA_USERID, 1);

            Reservation reservation = new Reservation(roomId, fromTime, toTime, purpose, userId);
            reservationViewModel.insert(reservation);

            Toast.makeText(this, "Reservation Saved", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, "Reservation not saved", Toast.LENGTH_SHORT).show();
        }
    }
}
