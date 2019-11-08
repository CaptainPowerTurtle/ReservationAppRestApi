package com.jacob.reservationapp.Retrofit;

import com.jacob.reservationapp.Database.Reservation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface JsonReservationApi {

    @GET("reservations")
    Call<List<ReservationModel>> getReservations();

    @GET("rooms")
    Call<List<RoomModel>> getRooms();

    @GET("reservations/user/{userId}")
    Call<List<ReservationModel>> getReservationByUserId(@Path("userId") String userId);

    @POST("reservations")
    Call<ReservationModel> postReservation(@Body ReservationModel reservationModel);

    @PUT("reservations/{id}")
    Call<ReservationModel> putReservation(@Path("id") int id, @Body ReservationModel reservationModel);

    @DELETE("reservations/{id}")
    Call<Void> deleteReservation(@Path("id") int id);
}
