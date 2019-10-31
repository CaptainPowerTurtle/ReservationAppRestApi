package com.jacob.reservationapp.Retrofit;

import com.jacob.reservationapp.Database.Reservation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonReservationApi {

    @GET("reservations")
    Call<List<ReservationModel>> getReservations();
}
