package com.hms;

import java.sql.Date;

public class Booking {
    private int booking_id;
    private int resident_id;
    private int room_id;
    private Date booking_date;
    private String status;

    public Booking() {
    }

    public Booking(int booking_id, int resident_id, int room_id, Date booking_date, String status) {
        this.booking_id = booking_id;
        this.resident_id = resident_id;
        this.room_id = room_id;
        this.booking_date = booking_date;
        this.status = status;
    }

    public int getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public int getResident_id() {
        return resident_id;
    }

    public void setResident_id(int resident_id) {
        this.resident_id = resident_id;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public Date getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(Date booking_date) {
        this.booking_date = booking_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}