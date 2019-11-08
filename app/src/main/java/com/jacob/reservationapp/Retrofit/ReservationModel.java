package com.jacob.reservationapp.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReservationModel {
    private Integer id;
    @SerializedName("fromTime")
    @Expose
    private long fromTime;
    @SerializedName("toTime")
    @Expose
    private long toTime;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("purpose")
    @Expose
    private String purpose;
    @SerializedName("roomId")
    @Expose
    private int roomId;

    public ReservationModel(long fromTime, long toTime, String userId, String purpose, int roomId) {
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.userId = userId;
        this.purpose = purpose;
        this.roomId = roomId;
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
