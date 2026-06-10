package com.hms;

public class Resident {
    private int resident_id;
    private String full_name;
    private String student_id;
    private String phone;
    private Integer room_id;
    private Integer user_id;

    public Resident() {
    }

    public Resident(int resident_id, String full_name, String student_id, String phone, Integer room_id, Integer user_id) {
        this.resident_id = resident_id;
        this.full_name = full_name;
        this.student_id = student_id;
        this.phone = phone;
        this.room_id = room_id;
        this.user_id = user_id;
    }

    public int getResident_id() {
        return resident_id;
    }

    public void setResident_id(int resident_id) {
        this.resident_id = resident_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getRoom_id() {
        return room_id;
    }

    public void setRoom_id(Integer room_id) {
        this.room_id = room_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
}