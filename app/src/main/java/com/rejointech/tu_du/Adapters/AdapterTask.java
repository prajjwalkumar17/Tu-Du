package com.rejointech.tu_du.Adapters;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.rejointech.tu_du.R;
import com.rejointech.tu_du.model.ModelTask;

import java.util.List;

public class AdapterTask extends RecyclerView.Adapter<AdapterTask.viewholderr> {
    Context context;
    List<ModelTask> list;
    ModelTask modelTask;

    public AdapterTask(Context context, List<ModelTask> list) {
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
        modelTask = list.get(position);
        holder.textView.setText(modelTask.getTaskData());
        holder.checkBox.setChecked(modelTask.getStatus());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                holder.cardi.setCardBackgroundColor(R.color.light_blue_200);
                holder.cardi.setElevation(20);
                holder.deleBott.setVisibility(View.VISIBLE);
                holder.editBott.setVisibility(View.VISIBLE);
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
        public TextView textView;
        public Button editBott, deleBott;
        public CardView cardi;

        public viewholderr(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBoxer);
            textView = itemView.findViewById(R.id.textView);
            editBott = itemView.findViewById(R.id.editBott);
            deleBott = itemView.findViewById(R.id.deleteBott);
            cardi = itemView.findViewById(R.id.cardi);

        }
    }
}