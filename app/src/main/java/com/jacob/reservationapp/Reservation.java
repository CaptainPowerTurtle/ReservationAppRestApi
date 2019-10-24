package com.jacob.reservationapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reservation_table")
public class Reservation {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int fromTime;
    private int toTime;
    private int userId;
    private String purpose;
    private int roomId;

    public Reservation(int fromTime, int toTime, int userId, String purpose, int roomId) {
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.userId = userId;
        this.purpose = purpose;
        this.roomId = roomId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getFromTime() {
        return fromTime;
    }

    public int getToTime() {
        return toTime;
    }

    public int getUserId() {
        return userId;
    }

    public String getPurpose() {
        return purpose;
    }

    public int getRoomId() {
        return roomId;
    }
}
