package com.rejointech.tu_du.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rejointech.tu_du.R;
import com.rejointech.tu_du.model.TaskData;

import java.util.List;

public class TodoTasks extends RecyclerView.Adapter<TodoTasks.viewholderr> {
    Context context;
    List<TaskData> list;

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
        TaskData taskData = list.get(position);
        holder.checkBox.setText(taskData.getTaskData());
        holder.checkBox.setChecked(taskData.isStatus());

    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public static class viewholderr extends RecyclerView.ViewHolder {

        public CheckBox checkBox;

        public viewholderr(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBoxer);
        }
    }


}
