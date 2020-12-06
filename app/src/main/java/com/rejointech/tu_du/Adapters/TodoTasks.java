package com.rejointech.tu_du.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rejointech.tu_du.R;
import com.rejointech.tu_du.model.TaskData;

import java.util.List;

public class TodoTasks extends RecyclerView.Adapter<TodoTasks.viewholderr> {
    Context context;
    List<TaskData> list;
    TaskData taskData;

    public TodoTasks(Context context, List<TaskData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewholderr onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.task_layput, parent, false);
        return new viewholderr(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholderr holder, int position) {
        taskData = list.get(position);
        holder.checkBox.setText(taskData.getTaskData());
        holder.checkBox.setChecked(taskData.getStatus());

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.update);
                EditText editText1 = dialog.findViewById(R.id.editText1);
                Button SaveBot1 = dialog.findViewById(R.id.SaveBot1);
                Button CancelBot1 = dialog.findViewById(R.id.deleteBot);
            }
        });


    }


    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }

    public static class viewholderr extends RecyclerView.ViewHolder {

        public CheckBox checkBox;

        public viewholderr(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBoxer);
        }
    }
}
