package com.jacob.reservationapp.Retrofit;

public class ReservationModel {
    private int id;
    private long fromTime;
    private long toTime;
    private String userId;
    private String purpose;
    private int roomId;

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

    public void setFromTime(long fromTime) {
        this.fromTime = fromTime;
    }

    public void setToTime(long toTime) {
        this.toTime = toTime;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}
