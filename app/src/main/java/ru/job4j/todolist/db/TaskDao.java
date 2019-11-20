package ru.job4j.todolist.db;



import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import java.util.List;


/**
 * interface BdActionDao methods descriptions
 * @author Dmitry Kolgabov (mailto:dmk78.inbox.ru)
 *  * @since 11.10.2019
 *  * @version $Id$
 */


@Dao
public interface TaskDao {


    @Query("SELECT * FROM tasks")
    List<Task> getAllTasks();

    @Query("SELECT * FROM tasks")
    Cursor getCursorAllTasks();

    @Query("SELECT * FROM tasks WHERE id == :id")
    Task getTaskById(int id);

    @Insert
    void insertTask(Task task);

    @Delete
    void deleteTask(Task task);

    @Update
    void updateTask(Task task);

    @Query("DELETE FROM tasks")
    void deleteAllTasks();







}
