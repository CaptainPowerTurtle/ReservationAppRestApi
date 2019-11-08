package com.jacob.reservationapp.Database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reservation_table")
public class Reservation {
    @PrimaryKey
    private int id;
    private long fromTime;
    private long toTime;
    private String userId;
    private String purpose;
    private int roomId;

    public Reservation(int id,long fromTime, long toTime, String userId, String purpose, int roomId) {
        this.id = id;
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

    public String getUserId() {
        return userId;
    }

    public String getPurpose() {
        return purpose;
    }

    public int getRoomId() {
        return roomId;
    }
}
