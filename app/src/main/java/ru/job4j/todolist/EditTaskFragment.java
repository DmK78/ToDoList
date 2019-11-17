package ru.job4j.todolist;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ru.job4j.todolist.db.Task;
import ru.job4j.todolist.db.TasksRepository;

public class EditTaskFragment extends Fragment {

    private ViewTaskClickListener callback;
    private Task task;
    private int indexTask;
    private TextView textViewCreated, textViewClosed;
    private EditText editTextName, editTextDesc;
    private Button buttonUpdate, buttonCancel, buttonDelete;
    TasksRepository tasksRepository = TasksRepository.getInstance(getContext());

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_open_task, container, false);
        editTextName = view.findViewById(R.id.editTextName);
        editTextDesc = view.findViewById(R.id.editTextDesc);
        textViewCreated = view.findViewById(R.id.textViewCreated);
        textViewClosed = view.findViewById(R.id.textViewClosed);
        buttonUpdate = view.findViewById(R.id.buttonSave);
        buttonDelete = view.findViewById(R.id.buttonDelete);
        buttonCancel = view.findViewById(R.id.buttonCancel);

        if (getArguments() != null) {
            indexTask = getArguments().getInt("task");
            task=tasksRepository.getTaskById(indexTask);
            editTextName.setText(task.getName());
            editTextDesc.setText(task.getDesc());
            textViewCreated.setText(task.getCreated());
            textViewClosed.setText(task.getClosed());

        }

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.setName(editTextName.getText().toString());
                task.setDesc(editTextDesc.getText().toString());
                callback.onUpdateTaskClick(task);
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onCancelUpdateTaskClick();
            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onDeleteTaskClick(task);
            }
        });
        return view;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (ViewTaskClickListener) context; // назначаем активити при присоединении фрагмента к активити
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null; // обнуляем ссылку при отсоединении фрагмента от активити
    }

    public interface ViewTaskClickListener {
        void onUpdateTaskClick(Task task);
        void onDeleteTaskClick(Task task);
        void onCancelUpdateTaskClick();
    }
}