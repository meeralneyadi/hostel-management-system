package com.hms;

public class SupportRequest {
    private int request_id;
    private int resident_id;
    private String title;
    private String description;
    private String priority;
    private String status;

    public SupportRequest() {
    }

    public SupportRequest(int request_id, int resident_id, String title, String description, String priority, String status) {
        this.request_id = request_id;
        this.resident_id = resident_id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
    }

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }

    public int getResident_id() {
        return resident_id;
    }

    public void setResident_id(int resident_id) {
        this.resident_id = resident_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}