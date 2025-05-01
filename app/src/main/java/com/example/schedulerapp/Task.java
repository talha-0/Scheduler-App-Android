package com.example.schedulerapp;

public class Task {
    public int id;
    public String title;
    public String description;
    public String datetime;
    public String status;

    public Task(int id, String title, String description, String datetime, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.datetime = datetime;
        this.status = status;
    }
}
