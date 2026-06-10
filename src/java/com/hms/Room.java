package com.hms;

public class Room {
    private int room_id;
    private String room_number;
    private String room_type;
    private int capacity;
    private String status;
    private int floor;

    public Room() {
    }

    public Room(int room_id, String room_number, String room_type, int capacity, String status, int floor) {
        this.room_id = room_id;
        this.room_number = room_number;
        this.room_type = room_type;
        this.capacity = capacity;
        this.status = status;
        this.floor = floor;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public String getRoom_number() {
        return room_number;
    }

    public void setRoom_number(String room_number) {
        this.room_number = room_number;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }
}