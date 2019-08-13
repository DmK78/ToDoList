package ru.job4j.todolist;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(name, task.name) &&
                Objects.equals(desc, task.desc) &&
                Objects.equals(created, task.created) &&
                Objects.equals(closed, task.closed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, desc, created, closed);
    }
}
