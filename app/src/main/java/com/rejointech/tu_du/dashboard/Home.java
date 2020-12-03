package com.rejointech.tu_du.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.WindowManager;

import com.rejointech.tu_du.Adapters.TodoTasks;
import com.rejointech.tu_du.R;
import com.rejointech.tu_du.model.TaskData;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {
    RecyclerView recyclerView;
    TodoTasks todoTasks;
    List<TaskData> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.recyclerView);
        list = new ArrayList<>();
        todoTasks = new TodoTasks(Home.this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(todoTasks);
        TaskData taskData = new TaskData();
        taskData.setTaskData("thsi is a very potty exmple pad dunga muh me");
        taskData.setStatus(true);
        taskData.setRef(45);
        list.add(taskData);
        todoTasks.notifyDataSetChanged();


    }
}