package com.jacob.reservationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jacob.reservationapp.Adapter.ReservationAdapter;
import com.jacob.reservationapp.Auth.User;
import com.jacob.reservationapp.Database.Reservation;
import com.jacob.reservationapp.Retrofit.DataServiceGenerator;
import com.jacob.reservationapp.Retrofit.JsonReservationApi;
import com.jacob.reservationapp.Retrofit.ReservationModel;
import com.jacob.reservationapp.Splash.SplashActivity;
import com.jacob.reservationapp.Utils.Constants;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.os.Build.USER;

public class MainActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener, Serializable {
    public static final String EXTRA_UID = "com.jacob.reservationapp.EXTRA_UID";

    private ReservationViewModel reservationViewModel;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public static final int ADD_RESERVATION_REQUEST = 1;
    public static final int EDIT_RESERVATION_REQUEST = 2;
    List<Reservation> reservationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        User user = getUserFromIntent();
        initGoogleSignInClient();

        FloatingActionButton buttonAddReservation = findViewById(R.id.button_add_reservation);
        buttonAddReservation.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Intent intent = new Intent(MainActivity.this, AddEditReservationActivity.class);
                goToAddEditReservationActivity(user);
                //startActivityForResult(intent, ADD_RESERVATION_REQUEST);
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
                adapter.submitList(reservations);
                reservationList = reservations;
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                reservationViewModel.delete(adapter.getReservationAt(viewHolder.getAdapterPosition()));

                Toast.makeText(MainActivity.this, "Reservation deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new ReservationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Reservation resertvation) {
                Intent intent = new Intent(MainActivity.this, AddEditReservationActivity.class);
                intent.putExtra(AddEditReservationActivity.EXTRA_ID, resertvation.getId());
                intent.putExtra(AddEditReservationActivity.EXTRA_FROMTIME, resertvation.getFromTime());
                intent.putExtra(AddEditReservationActivity.EXTRA_TOTIME, resertvation.getToTime());
                intent.putExtra(AddEditReservationActivity.EXTRA_USERID, resertvation.getUserId());
                intent.putExtra(AddEditReservationActivity.EXTRA_PURPOSE, resertvation.getPurpose());
                intent.putExtra(AddEditReservationActivity.EXTRA_ROOMID, resertvation.getRoomId());
                startActivityForResult(intent, EDIT_RESERVATION_REQUEST);
            }
        });
        fetchReservations();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_RESERVATION_REQUEST && resultCode == RESULT_OK){
            int roomId = data.getIntExtra(AddEditReservationActivity.EXTRA_ROOMID, 1);
            String purpose = data.getStringExtra(AddEditReservationActivity.EXTRA_PURPOSE);
            long fromTime = data.getLongExtra(AddEditReservationActivity.EXTRA_FROMTIME, 1);
            long toTime = data.getLongExtra(AddEditReservationActivity.EXTRA_TOTIME, 1);
            String userId = data.getStringExtra(AddEditReservationActivity.EXTRA_USERID);

            Reservation reservation = new Reservation(fromTime, toTime, userId, purpose, roomId);
            reservationViewModel.insert(reservation);

            Toast.makeText(this, "Reservation Saved", Toast.LENGTH_SHORT).show();
        }else if (requestCode == EDIT_RESERVATION_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditReservationActivity.EXTRA_ID, -1);
            if (id == -1){
                Toast.makeText(this, "Reservation can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            int roomId = data.getIntExtra(AddEditReservationActivity.EXTRA_ROOMID, 1);
            String purpose = data.getStringExtra(AddEditReservationActivity.EXTRA_PURPOSE);
            long fromTime = data.getLongExtra(AddEditReservationActivity.EXTRA_FROMTIME, 1);
            long toTime = data.getLongExtra(AddEditReservationActivity.EXTRA_TOTIME, 1);
            String  userId = data.getStringExtra(AddEditReservationActivity.EXTRA_USERID);

            Reservation reservation = new Reservation(fromTime, toTime, userId, purpose, roomId);
            reservation.setId(id);
            reservationViewModel.update(reservation);
            Toast.makeText(this, "Reservation have been updated", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Reservation not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_all_reservations:
                reservationViewModel.deleteAllReservations();
                Toast.makeText(this, "All reservations have been deleted", Toast.LENGTH_SHORT).show();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }
    private void fetchReservations(){
        DataServiceGenerator DataServiceGenerator = new DataServiceGenerator();
        JsonReservationApi jsonReservationApi = DataServiceGenerator.createService(JsonReservationApi.class);

        Call<List<ReservationModel>> call = jsonReservationApi.getReservations();

        call.enqueue(new Callback<List<ReservationModel>>() {
            @Override
            public void onResponse(Call<List<ReservationModel>> call, Response<List<ReservationModel>> response) {
                if (response.isSuccessful()){
                    if (response != null){
                        List<ReservationModel> reservationModelList = response.body();
                        for (int i=0; i<reservationModelList.size(); i++){
                            long fromTime = reservationModelList.get(i).getFromTime();
                            long toTime = reservationModelList.get(i).getToTime();
                            String userId = reservationModelList.get(i).getUserId();
                            String purpose = reservationModelList.get(i).getPurpose();
                            int roomId = reservationModelList.get(i).getRoomId();

                            Reservation reservations = new Reservation(fromTime, toTime, userId, purpose, roomId);
                            reservationViewModel.insert(reservations);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ReservationModel>> call, Throwable t) {

            }
        });
    }


    private User getUserFromIntent() {
        return (User) getIntent().getSerializableExtra(USER);
    }

    private void initGoogleSignInClient() {
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            goToAuthInActivity();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(this);
    }

    private void signOut() {
        singOutFirebase();
        signOutGoogle();
    }

    private void goToAuthInActivity() {
        Intent intent = new Intent(MainActivity.this, AuthActivity.class);
        startActivity(intent);
    }
    private void goToAddEditReservationActivity(User user) {
        Intent intent = new Intent(MainActivity.this, AddEditReservationActivity.class);
        intent.putExtra(USER, user);
        startActivityForResult(intent, ADD_RESERVATION_REQUEST);
    }

    private void singOutFirebase() {
        firebaseAuth.signOut();
    }

    private void signOutGoogle() {
        googleSignInClient.signOut();
    }

//    private void initMessageTextView() {
//        messageTextView = findViewById(R.id.message_text_view);
//    }
//
//    private void setMessageToMessageTextView(User user) {
//        Toast.makeText(this, user.name, Toast.LENGTH_SHORT).show();
//    }

}
