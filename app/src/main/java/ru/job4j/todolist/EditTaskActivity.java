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

public class EditTaskActivity extends Fragment {
    private ConfirmSaveListener callback;
    private Task task;
    private int indexTask;
    private EditText editTextName, editTextDesc, editTextCreated, editTextClosed;
    private Button buttonSave, buttonCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_edit_task, container, false);
        editTextName = view.findViewById(R.id.editTextName);
        editTextDesc = view.findViewById(R.id.editTextDesc);
        editTextCreated = view.findViewById(R.id.editTextCreated);
        editTextClosed = view.findViewById(R.id.editTextClosed);
        buttonSave = view.findViewById(R.id.buttonSave);
        buttonCancel = view.findViewById(R.id.buttonCancel);

        if (getArguments() != null) {
            indexTask = getArguments().getInt("task");
            task = MainActivity.tasks.get(indexTask);
            editTextName.setText(task.getName());
            editTextDesc.setText(task.getDesc());
            editTextCreated.setText(task.getCreated());
            editTextClosed.setText(task.getClosed());

        }

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.setName(editTextName.getText().toString());
                task.setDesc(editTextDesc.getText().toString());
                task.setCreated(editTextCreated.getText().toString());
                task.setClosed(editTextClosed.getText().toString());
                callback.onSaveClick(task, indexTask);

            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onCancelClick();
            }
        });


        return view;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (ConfirmSaveListener) context; // назначаем активити при присоединении фрагмента к активити
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null; // обнуляем ссылку при отсоединении фрагмента от активити
    }



    public interface ConfirmSaveListener {

        void onSaveClick(Task task, int index);


        void onCancelClick();

    }
}
