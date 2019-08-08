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
import android.widget.Toast;
import java.util.List;
import static ru.job4j.todolist.MainActivity.tasks;

public class TasksListActivity extends Fragment {
    private RecyclerView recycler;
    private OnTaskSelectClickListener callback;
    FloatingActionButton buttonAddTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tasks_list, container, false);
        recycler = view.findViewById(R.id.recyclerViewTasks);
        buttonAddTask=view.findViewById(R.id.floatingActionButton);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));// it was --- getApplicationContext()
        this.recycler.setAdapter(new TasksAdapter(getContext(), tasks));
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
            Task task = tasks.get(i);
            tasksHolder.textViewName.setText(task.getName());
            tasksHolder.textViewDesc.setText(task.getDesc());
            tasksHolder.textViewCreated.setText(task.getCreated());
            tasksHolder.textViewClosed.setText(task.getClosed());
        }

        @Override
        public int getItemCount() {
            return tasks.size();
        }

        public class TasksHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private TextView textViewName, textViewDesc, textViewCreated, textViewClosed;

            TasksHolder(View view) {
                super(view);
                textViewName = view.findViewById(R.id.textViewName);
                textViewDesc = view.findViewById(R.id.textViewDesc);
                textViewCreated = view.findViewById(R.id.textViewCreated);
                textViewClosed = view.findViewById(R.id.textViewClosed);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), " " + tasks.get(getAdapterPosition()).getName().toString(), Toast.LENGTH_SHORT).show();
                callback.onTaskClicked(getAdapterPosition());
                notifyDataSetChanged();
            }
        }


    }

    public interface OnTaskSelectClickListener {
        void onTaskClicked(int i);
        void onAddTask();
    }
}
