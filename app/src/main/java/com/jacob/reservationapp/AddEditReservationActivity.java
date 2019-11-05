package com.jacob.reservationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jacob.reservationapp.Auth.User;
import com.jacob.reservationapp.Retrofit.DataServiceGenerator;
import com.jacob.reservationapp.Retrofit.JsonReservationApi;
import com.jacob.reservationapp.Retrofit.ReservationModel;
import com.jacob.reservationapp.Utils.Constants;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Calendar;

import retrofit2.Call;

import static android.os.Build.USER;

public class AddEditReservationActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, FirebaseAuth.AuthStateListener{
    public static final String EXTRA_ID = "com.jacob.reservationapp.EXTRA_ID";
    public static final String EXTRA_ROOMID = "com.jacob.reservationapp.EXTRA_ROOMID";
    public static final String EXTRA_PURPOSE = "com.jacob.reservationapp.EXTRA_PURPOSE";
    public static final String EXTRA_FROMTIME = "com.jacob.reservationapp.EXTRA_FROMTIME";
    public static final String EXTRA_TOTIME = "com.jacob.reservationapp.EXTRA_TOTIME";
    public static final String EXTRA_USERID = "com.jacob.reservationapp.EXTRA_USERID";
    public static final String EXTRA_USER = "com.jacob.reservationapp.EXTRA_USER";

    private NumberPicker numberPickerRoomId;
    private NumberPicker numberPickerUserId;
    private EditText editTextPupose;
    private EditText editTextFromTime;
    private EditText editTextToTime;
    private long fromTime;
    private long toTime;
    private TextView textViewUid;
    AuthViewModel authViewModel;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reservation);
        User user = getUserFromIntent();
        initGoogleSignInClient();

        numberPickerRoomId = findViewById(R.id.number_picker_roomId);
        editTextPupose = findViewById(R.id.edit_text_pupose);
        numberPickerUserId = findViewById(R.id.number_picker_userId);
        editTextFromTime = findViewById(R.id.edit_text_fromTime);
        editTextToTime = findViewById(R.id.edit_text_toTime);
        textViewUid = findViewById(R.id.text_view_uid);

        numberPickerRoomId.setMinValue(1);
        numberPickerRoomId.setMaxValue(10);
        numberPickerUserId.setMinValue(1);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        initMessageTextView();

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Reservation");
            editTextPupose.setText(intent.getStringExtra(EXTRA_PURPOSE));
            numberPickerRoomId.setValue(intent.getIntExtra(EXTRA_ROOMID, 1));
            editTextFromTime.setText(intent.getStringExtra(EXTRA_FROMTIME));
            editTextToTime.setText(intent.getStringExtra(EXTRA_TOTIME));
        }else{
            setTitle("Add Reservation");
            setMessageToMessageTextView(user);
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
    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            goToAuthInActivity();
        }
    }

    private void saveReservation(){

        int roomId = numberPickerRoomId.getValue();
        String roomPurpose = editTextPupose.getText().toString();
        String userId = getUserFromIntent().uid;
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
    private User getUserFromIntent() {
        return (User) getIntent().getSerializableExtra(USER);
    }
    private void initMessageTextView() {
        textViewUid = findViewById(R.id.text_view_uid);
    }

    private void setMessageToMessageTextView(User user) {
        String message = "You are logged in as: " + user.name;
        textViewUid.setText(message);
    }
    private void initGoogleSignInClient() {
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
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

    private void goToAuthInActivity() {
        Intent intent = new Intent(AddEditReservationActivity.this, AuthActivity.class);
        startActivity(intent);
    }
}
