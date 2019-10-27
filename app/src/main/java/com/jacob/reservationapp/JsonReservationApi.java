package com.jacob.reservationapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonReservationApi {

    @GET("reservations")
    Call<List<Reservation>> getReservations();
}
