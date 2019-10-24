package com.jacob.reservationapp;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ReservationRepository {

    private ReservationDao reservationDao;
    private LiveData<List<Reservation>> allReservations;

    public ReservationRepository(Application application) {
        ReservationDatabase database = ReservationDatabase.getInstance(application);
        reservationDao = database.reservationDao();
        allReservations = reservationDao.getAllReservations();
    }
    public void insert(Reservation reservation){
        new InsertReservationAsyncTask(reservationDao).execute(reservation);
    }
    public void update(Reservation reservation){
        new UpdateReservationAsyncTask(reservationDao).execute(reservation);
    }
    public void delete(Reservation reservation){
        new DeleteReservationAsyncTask(reservationDao).execute(reservation);
    }
    public void deleteAllReservation(){
        new DeleteAlleservationsAsyncTask(reservationDao).execute();
    }
    public LiveData<List<Reservation>> getAllReservations(){
        return allReservations;
    }

    private static class InsertReservationAsyncTask extends AsyncTask<Reservation, Void, Void>{
        private ReservationDao reservationDao;
        private InsertReservationAsyncTask(ReservationDao reservationDao){
            this.reservationDao = reservationDao;
        }
        @Override
        protected Void doInBackground(Reservation... reservations) {
            reservationDao.insert(reservations[0]);
            return null;
        }
    }
    private static class UpdateReservationAsyncTask extends AsyncTask<Reservation, Void, Void>{
        private ReservationDao reservationDao;
        private UpdateReservationAsyncTask(ReservationDao reservationDao){
            this.reservationDao = reservationDao;
        }
        @Override
        protected Void doInBackground(Reservation... reservations) {
            reservationDao.update(reservations[0]);
            return null;
        }
    }
    private static class DeleteReservationAsyncTask extends AsyncTask<Reservation, Void, Void>{
        private ReservationDao reservationDao;
        private DeleteReservationAsyncTask(ReservationDao reservationDao){
            this.reservationDao = reservationDao;
        }
        @Override
        protected Void doInBackground(Reservation... reservations) {
            reservationDao.delete(reservations[0]);
            return null;
        }
    }
    private static class DeleteAlleservationsAsyncTask extends AsyncTask<Void, Void, Void>{
        private ReservationDao reservationDao;
        private DeleteAlleservationsAsyncTask(ReservationDao reservationDao){
            this.reservationDao = reservationDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            reservationDao.deleteAllReservation();
            return null;
        }
    }
}
