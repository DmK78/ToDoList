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

public class AddTaskFragment extends Fragment {
    private EditTaskClickListener callback;
    private Task task;
    private int indexTask=-1;
    private EditText editTextName, editTextDesc;
    private TextView textViewCreated, textViewClosed;
    private Button buttonSave, buttonCancel, buttonDelete;
    private TasksRepository tasksRepository = TasksRepository.getInstance(getContext());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_open_task, container, false);
        editTextName = view.findViewById(R.id.editTextName);
        editTextDesc = view.findViewById(R.id.editTextDesc);
        textViewCreated = view.findViewById(R.id.textViewCreated);
        textViewClosed = view.findViewById(R.id.textViewClosed);
        buttonSave = view.findViewById(R.id.buttonSave);
        buttonDelete=view.findViewById(R.id.buttonDelete);
        buttonDelete.setVisibility(View.INVISIBLE);
        buttonCancel = view.findViewById(R.id.buttonCancel);
            task = new Task("", "");

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.setName(editTextName.getText().toString());
                task.setDesc(editTextDesc.getText().toString());
                //task.setCreated(editTextCreated.getText().toString());
                //task.setClosed(editTextClosed.getText().toString());
                callback.onAddNewTaskClick(task);

            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onCancelAddNewTaskClick();
            }
        });
        return view;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (EditTaskClickListener) context; // назначаем активити при присоединении фрагмента к активити
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null; // обнуляем ссылку при отсоединении фрагмента от активити
    }


    public interface EditTaskClickListener {
        void onAddNewTaskClick(Task task);

        void onCancelAddNewTaskClick();
    }
}
