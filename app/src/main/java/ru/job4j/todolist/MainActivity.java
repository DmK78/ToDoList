package ru.job4j.todolist;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import ru.job4j.todolist.db.Task;
import ru.job4j.todolist.db.TasksRepository;

public class MainActivity extends AppCompatActivity implements TasksListFragment.OnTaskSelectClickListener, AddTaskFragment.EditTaskClickListener, EditTaskFragment.ViewTaskClickListener {
    private FragmentManager fm;
    private Fragment tasksListFragment;
    private Fragment editTaskFragment;
    private Fragment viewTaskFragment;
    //private TasksRepository tasksRepository;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper=DBHelper.getInstance(this);
/*        Cursor cursor = getContentResolver()
                .query(StoreContentProvider.CONTENT_URI, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Log.d("ContentProvider", cursor.getString(1));
        }*/
        fm = getSupportFragmentManager(); // получить FragmentManager
        tasksListFragment = fm.findFragmentById(R.id.fragment_container);
        if (tasksListFragment == null) {
            tasksListFragment = new TasksListFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, tasksListFragment) // добавить фрагмент в контейнер
                    .commit();
        }
    }

    @Override
    public void onTaskClicked(int i) {
        Bundle bundle = new Bundle();
        bundle.putInt("task", i);
        //if (editTaskFragment == null) {
        viewTaskFragment = new EditTaskFragment();
        //}
        viewTaskFragment.setArguments(bundle);
        fm.beginTransaction()
                .replace(R.id.fragment_container, viewTaskFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onAddTask() {
        editTaskFragment = new AddTaskFragment();
        fm.beginTransaction()
                .replace(R.id.fragment_container, editTaskFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onUpdateTaskClick(Task task) {
        //tasksRepository.updateTask(task);
        dbHelper.upadateTask(task);
        if (tasksListFragment == null) {
            tasksListFragment = new TasksListFragment();
        }
        fm.beginTransaction()
                .replace(R.id.fragment_container, tasksListFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onDeleteTaskClick(Task task) {
        dbHelper.deleteTask(task);
        //tasksRepository.deleteTask(task);
        if (tasksListFragment == null) {
            tasksListFragment = new TasksListFragment();
        }
        fm.beginTransaction()
                .replace(R.id.fragment_container, tasksListFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onAddNewTaskClick(Task task) {
        //tasksRepository.addTask(task);
        dbHelper.addTask(task);

        if (tasksListFragment == null) {
            tasksListFragment = new TasksListFragment();
        }
        fm.beginTransaction()
                .replace(R.id.fragment_container, tasksListFragment)
                .addToBackStack(null)
                .commit();


    }

    @Override
    public void onCancelAddNewTaskClick() {
        if (tasksListFragment == null) {
            tasksListFragment = new TasksListFragment();
        }
        fm.beginTransaction()
                .replace(R.id.fragment_container, tasksListFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onCancelUpdateTaskClick() {
        if (tasksListFragment == null) {
            tasksListFragment = new TasksListFragment();
        }
        fm.beginTransaction()
                .replace(R.id.fragment_container, tasksListFragment)
                .addToBackStack(null)
                .commit();
    }
}
