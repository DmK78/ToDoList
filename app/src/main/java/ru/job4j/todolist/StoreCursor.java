package ru.job4j.todolist;

import android.database.AbstractCursor;

import ru.job4j.todolist.db.Task;
import ru.job4j.todolist.db.TasksRepository;

public class StoreCursor extends AbstractCursor {
    private DBHelper dbHelper;

    public StoreCursor(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public int getCount() {
        return dbHelper.getAllTasks().size();
    }

    @Override
    public String[] getColumnNames() {
        return new String[]{"id", "name", "desc", "created", "closed"};

    }

    @Override
    public String getString(int column) {
        Task task = dbHelper.getTask(getPosition());
        String value = null;
        if (column == 0) {
            value = String.valueOf(task.getId());
        }
        if (column == 1) {
            value = task.getName();
        }
        if (column == 2) {
            value = task.getDesc();
        }
        if (column == 3) {
            value = task.getCreated();
        }
        if (column == 4) {
            value = task.getClosed();
        }
        return value;
    }

    @Override
    public short getShort(int column) {
        return 0;
    }

    @Override
    public int getInt(int column) {
        return 0;
    }

    @Override
    public long getLong(int column) {
        return 0;
    }

    @Override
    public float getFloat(int column) {
        return 0;
    }

    @Override
    public double getDouble(int column) {
        return 0;
    }

    @Override
    public boolean isNull(int column) {
        return false;
    }
}
