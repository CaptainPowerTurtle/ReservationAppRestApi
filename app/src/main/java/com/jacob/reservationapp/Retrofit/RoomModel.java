package com.jacob.reservationapp.Retrofit;

public class RoomModel {
    int id;
    String name;
    String description;
    int capacity;
    String remarks;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
