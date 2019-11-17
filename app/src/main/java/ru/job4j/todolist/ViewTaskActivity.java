package ru.job4j.todolist;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ru.job4j.todolist.db.Task;

public class ViewTaskActivity extends Fragment {

    private ViewTaskClickListener callback;
    private Task task;
    private int indexTask;
    private TextView textViewName, textViewDesc, textViewCreated, textViewClosed;
    private Button buttonEdit, buttonCancel, buttonDelete;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_view_task, container, false);
        textViewName = view.findViewById(R.id.textViewName);
        textViewDesc = view.findViewById(R.id.textViewDesc);
        textViewCreated = view.findViewById(R.id.textViewCreated);
        textViewClosed = view.findViewById(R.id.textViewClosed);
        buttonEdit = view.findViewById(R.id.buttonEdit);
        buttonDelete = view.findViewById(R.id.buttonDelete);
        buttonCancel = view.findViewById(R.id.buttonCancel);

        if (getArguments() != null) {
            indexTask = getArguments().getInt("task");
            task = MainActivity.tasks.get(indexTask);
            textViewName.setText(task.getName());
            textViewDesc.setText(task.getDesc());
            textViewCreated.setText(task.getCreated());
            textViewClosed.setText(task.getClosed());

        }

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onEditClick(task, indexTask);
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onCancelClick();
            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onDeleteClick(indexTask);
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
        void onEditClick(Task task, int index);
        void onDeleteClick(int index);
        void onCancelClick();
    }
}