package com.jacob.reservationapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.jacob.reservationapp.Database.Reservation;
import com.jacob.reservationapp.R;

public class ReservationAdapter extends ListAdapter<Reservation, ReservationAdapter.ReservationHolder> {
    private OnItemClickListener listener;

    public ReservationAdapter() {
        super(DIFF_CALLBACK);

    }
    private static final DiffUtil.ItemCallback<Reservation> DIFF_CALLBACK = new DiffUtil.ItemCallback<Reservation>() {
        @Override
        public boolean areItemsTheSame(@NonNull Reservation oldItem, @NonNull Reservation newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Reservation oldItem, @NonNull Reservation newItem) {
            return oldItem.getPurpose().equals(newItem.getPurpose()) &&
                    oldItem.getFromTime() == newItem.getFromTime() &&
                    oldItem.getToTime() == newItem.getToTime() &&
                    oldItem.getRoomId() == newItem.getRoomId();
        }
    };

    @NonNull
    @Override
    public ReservationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reservation_item, parent, false);
        return new ReservationHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationHolder holder, int position) {
        Reservation currentReservation = getItem(position);
        holder.textViewRoomId.setText(String.valueOf(currentReservation.getRoomId()));
        holder.textViewPurpose.setText(currentReservation.getPurpose());
        holder.textViewFromTime.setText(String.valueOf(currentReservation.getFromTime()));
        holder.textViewToTime.setText(String.valueOf(currentReservation.getToTime()));
        holder.textViewUserId.setText(String.valueOf(currentReservation.getUserId()));
    }

    public Reservation getReservationAt(int position) {
        return getItem(position);
    }

    class ReservationHolder extends RecyclerView.ViewHolder {
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Reservation resertvation);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;

    }
}
