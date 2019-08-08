package ru.job4j.todolist;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Task {

    private String name;
    private String desc;
    private String created;
    private String closed;

    public Task(String name, String desc) {
        this.name = name;
        this.desc = desc;
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        this.created = df.format(Calendar.getInstance().getTime());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getClosed() {
        return closed;
    }

    public void setClosed(String closed) {
        this.closed = closed;
    }
}
