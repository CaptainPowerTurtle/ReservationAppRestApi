package com.jacob.reservationapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationHolder> {

    private List<Reservation> reservations = new ArrayList<>();
    @NonNull
    @Override
    public ReservationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reservation_item, parent, false);
        return new ReservationHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationHolder holder, int position) {
        Reservation currentReservation = reservations.get(position);
        holder.textViewRoomId.setText(String.valueOf(currentReservation.getRoomId()));
        holder.textViewPurpose.setText(currentReservation.getPurpose());
        holder.textViewFromTime.setText(String.valueOf(currentReservation.getFromTime()));
        holder.textViewToTime.setText(String.valueOf(currentReservation.getToTime()));
        holder.textViewUserId.setText(String.valueOf(currentReservation.getUserId()));
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    public void setReservations(List<Reservation> reservations){
        this.reservations = reservations;
        notifyDataSetChanged();
    }
    class ReservationHolder extends RecyclerView.ViewHolder{
        private TextView textViewRoomId;
        private TextView textViewPurpose;
        private TextView textViewFromTime;
        private TextView textViewToTime;
        private TextView textViewUserId;

        public ReservationHolder(@NonNull View itemView) {
            super(itemView);
            textViewRoomId = itemView.findViewById(R.id.text_view_room_id);
            textViewPurpose = itemView.findViewById(R.id.text_view_purpose);
            textViewFromTime = itemView.findViewById(R.id.text_view_fromTime);
            textViewToTime = itemView.findViewById(R.id.text_view_toTime);
            textViewUserId = itemView.findViewById(R.id.text_view_user_id);
        }
    }
}
