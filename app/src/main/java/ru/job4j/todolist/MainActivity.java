package ru.job4j.todolist;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import ru.job4j.todolist.db.Task;

public class MainActivity extends AppCompatActivity implements TasksListActivity.OnTaskSelectClickListener, EditTaskActivity.EditTaskClickListener, ViewTaskActivity.ViewTaskClickListener {
    public static List<Task> tasks = new ArrayList<>();
    private FragmentManager fm;
    private Fragment tasksListFragment;
    private Fragment editTaskFragment;
    private Fragment viewTaskFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*for (int i = 0; i < 100; i++) {
            tasks.add(new Task("task" + i, "desc"));
        }*/
        fm = getSupportFragmentManager(); // получить FragmentManager
        tasksListFragment = fm.findFragmentById(R.id.fragment_container);
        if (tasksListFragment == null) {
            tasksListFragment = new TasksListActivity();
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
        viewTaskFragment = new ViewTaskActivity();
        //}
        viewTaskFragment.setArguments(bundle);
        fm.beginTransaction()
                .replace(R.id.fragment_container, viewTaskFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onAddTask() {
        /*Bundle bundle = new Bundle();
        bundle.putInt("task", index);*/
        //if (editTaskFragment == null) {
        editTaskFragment = new EditTaskActivity();
        //}
        // editTaskFragment.setArguments(bundle);
        fm.beginTransaction()
                .replace(R.id.fragment_container, editTaskFragment)
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onSaveClick(Task task, int index) {
        if (index == -1) {
            tasks.add(task);
        } else {
            tasks.remove(index);
            tasks.add(index, task);
        }
        if (tasksListFragment == null) {
            tasksListFragment = new TasksListActivity();
        }
        fm.beginTransaction()
                .replace(R.id.fragment_container, tasksListFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onEditClick(Task task, int index) {
        Bundle bundle = new Bundle();
        bundle.putInt("task", index);
        //if (editTaskFragment == null) {
        editTaskFragment = new EditTaskActivity();
        //}
        editTaskFragment.setArguments(bundle);
        fm.beginTransaction()
                .replace(R.id.fragment_container, editTaskFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onDeleteClick(int index) {
        tasks.remove(index);
        if (tasksListFragment == null) {
            tasksListFragment = new TasksListActivity();
        }
        fm.beginTransaction()
                .replace(R.id.fragment_container, tasksListFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onCancelClick() {
        if (tasksListFragment == null) {
            tasksListFragment = new TasksListActivity();
        }
        fm.beginTransaction()
                .replace(R.id.fragment_container, tasksListFragment)
                .addToBackStack(null)
                .commit();
    }
}
