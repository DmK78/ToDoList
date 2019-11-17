package ru.job4j.todolist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.job4j.todolist.db.Task;
import ru.job4j.todolist.db.TasksRepository;


public class TasksListFragment extends Fragment {
    private RecyclerView recycler;
    private OnTaskSelectClickListener callback;
    private FloatingActionButton buttonAddTask;
    private TasksRepository tasksRepository;
    private TasksAdapter tasksAdapter;
    private List<Task> tasks = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tasks_list, container, false);
        tasksRepository = TasksRepository.getInstance(getContext());
        recycler = view.findViewById(R.id.recyclerViewTasks);
        buttonAddTask = view.findViewById(R.id.floatingActionButton);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));// it was --- getApplicationContext()
        this.recycler.setAdapter(new TasksAdapter(getContext(), tasks));
        tasks = tasksRepository.getAllTasks();
        tasksAdapter = new TasksAdapter(getContext(), tasks);
        recycler.setAdapter(tasksAdapter);
        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onAddTask();
            }
        });
        return view;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (OnTaskSelectClickListener) context; // назначаем активити при присоединении фрагмента к активити
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null; // обнуляем ссылку при отсоединении фрагмента от активити
    }

    public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TasksHolder> {
        private List<Task> tasks;
        private LayoutInflater inflater;

        TasksAdapter(Context context, List<Task> tasks) {
            this.inflater = LayoutInflater.from(context);
            this.tasks = tasks;
        }

        @NonNull
        @Override
        public TasksHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = inflater.inflate(R.layout.task_item, viewGroup, false);
            return new TasksHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TasksHolder tasksHolder, int i) {
            final Task task = tasks.get(i);
            tasksHolder.textViewName.setText(task.getName());
            tasksHolder.textViewDesc.setText(task.getDesc());
            String taskDate = task.getCreated();
            tasksHolder.textViewCreated.setText(taskDate.substring(taskDate.indexOf(',') + 1, taskDate.lastIndexOf(',')));
            tasksHolder.textViewClosed.setText(task.getClosed());
            tasksHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onTaskClicked(task.getId());
                }
            });
        }

        @Override
        public int getItemCount() {
            return tasks.size();
        }

        public class TasksHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private TextView textViewName, textViewDesc, textViewCreated, textViewClosed;

            TasksHolder(View view) {
                super(view);
                textViewName = view.findViewById(R.id.editTextName);
                textViewDesc = view.findViewById(R.id.editTextDesc);
                textViewCreated = view.findViewById(R.id.textViewCreated);
                textViewClosed = view.findViewById(R.id.textViewClosed);
                //itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                /*Toast.makeText(getContext(), " " + tasks.get(getAdapterPosition()).getName().toString(), Toast.LENGTH_SHORT).show();
                callback.onTaskClicked(getAdapterPosition());
                notifyDataSetChanged();*/
            }
        }


    }

    public interface OnTaskSelectClickListener {
        void onTaskClicked(int i);

        void onAddTask();
    }
}
