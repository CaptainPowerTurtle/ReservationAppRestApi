package com.jacob.reservationapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jacob.reservationapp.Database.Reservation;
import com.jacob.reservationapp.Repo.ReservationRepository;

import java.util.List;

public class ReservationViewModel extends AndroidViewModel {
    private ReservationRepository repository;

    private LiveData<List<Reservation>> allReservations;

    public ReservationViewModel(@NonNull Application application) {
        super(application);
        repository = new ReservationRepository(application);
        allReservations = repository.getAllReservations();
    }

    public void insert(Reservation reservation) {
        repository.insert(reservation);
    }
    public void update(Reservation reservation){
        repository.update(reservation);
    }
    public void delete(Reservation reservation){
        repository.delete(reservation);
    }
    public void deleteAllReservations(){
        repository.deleteAllReservation();
    }
    public LiveData<List<Reservation>> getAllReservations(){
        return allReservations;
    }
}
