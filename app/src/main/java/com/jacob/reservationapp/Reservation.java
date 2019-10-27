package com.jacob.reservationapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reservation_table")
public class Reservation {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private long fromTime;
    private long toTime;
    private int userId;
    private String purpose;
    private int roomId;

    public Reservation(long fromTime, long toTime, int userId, String purpose, int roomId) {
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

    public long getFromTime() {
        return fromTime;
    }

    public long getToTime() {
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
