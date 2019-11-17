package ru.job4j.todolist.db;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TasksRepository {

    private AppDatabase appDatabase;
    private static TasksRepository mInstance;
    private Context context;

    /**
     * Class BdActionsRepository methods implementations
     * @author Dmitry Kolgabov (mailto:dmk78.inbox.ru)
     *  * @since 11.10.2019
     *  * @version $Id$
     */
    private TasksRepository(Context context) {
        this.context = context;
        appDatabase = AppDatabase.getInstance(context);
    }

    public static synchronized TasksRepository getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new TasksRepository(context);
        }
        return mInstance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }


    public List<Task> getAllTasks() {
        List<Task> result = new ArrayList<>();
        class GetBdActions extends AsyncTask<Void, Void, List<Task>> {

            @Override
            protected List<Task> doInBackground(Void... voids) {
                List<Task> bdActions = appDatabase
                        .taskDao()
                        .getAllTasks();
                return bdActions;
            }

            @Override
            protected void onPostExecute(final List<Task> tasks) {
                super.onPostExecute(tasks);
            }
        }
        GetBdActions gt = new GetBdActions();
        try {
            result = gt.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void addTask(final Task task) {
        class SaveBdActionTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase
                        .taskDao()
                        .insertTask(task);
                return null;
            }
            @Override
            protected void onPostExecute(Void v) {
                super.onPostExecute(v);
                Toast.makeText(context, "Saved", Toast.LENGTH_LONG).show();

            }
        }
        SaveBdActionTask st = new SaveBdActionTask();
        st.execute();

    }

    public void deleteTask(final Task task) {
        class DeleteTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase
                        .taskDao()
                        .deleteTask(task);
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(context, "Deleted", Toast.LENGTH_LONG).show();
            }
        }
        DeleteTask dt = new DeleteTask();
        dt.execute();
    }

    public Task getTaskById(int id) {
        Task result = null;
        class GetBdActionById extends AsyncTask<Integer, Void, Task> {

            @Override
            protected Task doInBackground(Integer... integers) {
                Task task = appDatabase
                        .taskDao()
                        .getTaskById(integers[0]);
                return task;
            }

            @Override
            protected void onPostExecute(Task task) {
                super.onPostExecute(task);
            }
        }
        GetBdActionById gt = new GetBdActionById();
        try {
            result = gt.execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void updateTask(final Task task) {
        class SaveBdActionTask extends AsyncTask<Task, Void, Void> {
            @Override
            protected Void doInBackground(Task... bdActions) {
                appDatabase
                        .taskDao()
                        .updateTask(task);
                return null;
            }
            @Override
            protected void onPostExecute(Void v) {
                super.onPostExecute(v);
                Toast.makeText(context, "Updated", Toast.LENGTH_LONG).show();

            }
        }
        SaveBdActionTask st = new SaveBdActionTask();
        st.execute();
    }

}
