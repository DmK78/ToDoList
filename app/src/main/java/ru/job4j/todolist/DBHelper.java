package ru.job4j.todolist;

import android.arch.persistence.room.Room;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import ru.job4j.todolist.db.AppDatabase;
import ru.job4j.todolist.db.Task;

public class DBHelper extends SQLiteOpenHelper {
    SQLiteDatabase dbRead = getReadableDatabase();
    SQLiteDatabase dbWrite = getWritableDatabase();
    private static DBHelper mInstance;

    public static final String DB = "todolist.db";
    public static final int VERSION = 1;
    private Context context;

    public static DBHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DBHelper(context);
        }
        return mInstance;
    }


    private DBHelper(Context context) {
        super(context, DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBchema.TasksTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + DBchema.TasksTable.NAME);
        onCreate(db);
    }

    public List<Task> getAllTasks() {
        List<Task> result = new ArrayList<>();
        Cursor cursor = dbRead.query(
                DBchema.TasksTable.NAME,
                null, null, null,
                null, null, null
        );
        if (cursor.moveToFirst()) {
            do {
                result.add(new Task(
                        cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getString(cursor.getColumnIndex("desc")),
                        cursor.getString(cursor.getColumnIndex("created")),
                        cursor.getString(cursor.getColumnIndex("closed"))
                ));
            } while (cursor.moveToNext());
        }
        return result;
    }

    public Cursor getAllTasksAsCursor() {

        Cursor cursor = dbRead.query(
                DBchema.TasksTable.NAME,
                null, null, null,
                null, null, null
        );

        return cursor;
    }


    public void addTask(Task task) {
        ContentValues value = new ContentValues();
        value.put(DBchema.TasksTable.Cols.NAME, task.getName());
        value.put(DBchema.TasksTable.Cols.DESC, task.getDesc());
        value.put(DBchema.TasksTable.Cols.CREATED, task.getCreated());
        value.put(DBchema.TasksTable.Cols.CLOSED, task.getClosed());
        dbWrite.insert(DBchema.TasksTable.NAME, null, value);
    }

    public Task getTask(int id) {
        Task result = null;
        String selectionExam = "id =?";
        String[] selectionArgsExam = new String[]{String.valueOf(id)};
        Cursor cursor = dbRead.query(
                DBchema.TasksTable.NAME,
                null, selectionExam, selectionArgsExam,
                null, null, null
        );
        cursor.moveToFirst();
        result = new Task(
                cursor.getInt(cursor.getColumnIndex("id")),
                cursor.getString(cursor.getColumnIndex(DBchema.TasksTable.Cols.NAME)),
                cursor.getString(cursor.getColumnIndex(DBchema.TasksTable.Cols.DESC)),
                cursor.getString(cursor.getColumnIndex(DBchema.TasksTable.Cols.CREATED)),
                cursor.getString(cursor.getColumnIndex(DBchema.TasksTable.Cols.CLOSED)));
        cursor.close();
        return result;
    }

    public void upadateTask(Task task) {
        ContentValues value = new ContentValues();
        value.put(DBchema.TasksTable.Cols.NAME, task.getName());
        value.put(DBchema.TasksTable.Cols.DESC, task.getDesc());
        value.put(DBchema.TasksTable.Cols.CREATED, task.getCreated());
        value.put(DBchema.TasksTable.Cols.CLOSED, task.getClosed());
        dbWrite.update(DBchema.TasksTable.NAME, value, "id =?",
                new String[]{String.valueOf(task.getId())});

    }

    public void deleteTask(Task task) {
        dbWrite.delete(DBchema.TasksTable.NAME, "id = ?", new String[]{String.valueOf(task.getId())});
    }

}
