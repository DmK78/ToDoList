package ru.job4j.todolist;

public class DBchema {
    public static final class TasksTable {
        public static final String NAME = "tasks";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TasksTable.NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TasksTable.Cols.NAME + " TEXT, " +
                TasksTable.Cols.DESC + " TEXT, " +
                Cols.CREATED + " TEXT, " +
                Cols.CLOSED + " TEXT" + ")";


        public static final class Cols {
            public static final String NAME = "name";
            public static final String DESC = "desc";
            public static final String CREATED = "created";
            public static final String CLOSED = "closed";
        }
    }



}