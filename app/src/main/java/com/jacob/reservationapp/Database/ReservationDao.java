package com.jacob.reservationapp.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ReservationDao {

    @Insert
    void insert(Reservation reservation);
    @Update
    void update(Reservation reservation);
    @Delete
    void delete(Reservation reservation);

    @Query("DELETE FROM reservation_table")
    void deleteAllReservation();

    @Query("SELECT * FROM reservation_table ORDER BY fromTime DESC")
    LiveData<List<Reservation>> getAllReservations();
}
