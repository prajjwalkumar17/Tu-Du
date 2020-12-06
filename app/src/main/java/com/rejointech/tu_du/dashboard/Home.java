package com.rejointech.tu_du.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rejointech.tu_du.Adapters.TodoTasks;
import com.rejointech.tu_du.R;
import com.rejointech.tu_du.model.TaskData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Home extends AppCompatActivity {
    RecyclerView recyclerView;
    List<TaskData> list = new ArrayList<>();
    DatabaseReference databaseReference, databaseReference1;
    FirebaseAuth auth;
    FloatingActionButton newTask;
    Dialog dialog;
    Button botSave, botCancel;
    EditText editText;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);

//TODO: all muticals::::::::::::::::::::::::::::::::::::::::::::::::::::::::

        muticals();

//TODO Buttons :::::::::::::::::::::::::::::::::::::::::::::::::::::::

        newTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogOpener();


                botSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addANewTask();
                    }
                });
            }
        });
        addDataToRecyclerView();
    }


//TODO all functions::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    private void muticals() {
        newTask = findViewById(R.id.newTask);
        recyclerView = findViewById(R.id.recyclerView);
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance()
                .getReference("Tasks_Data");
        databaseReference1 = FirebaseDatabase.getInstance()
                .getReference("Tasks_Data");
    }

    private void dialogOpener() {
        dialog = new Dialog(Home.this);
        dialog.setContentView(R.layout.newtosk);
        botSave = dialog.findViewById(R.id.SaveBot1);
        botCancel = dialog.findViewById(R.id.deleteBot);
        editText = dialog.findViewById(R.id.editText1);
        dialog.show();
        botCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void addANewTask() {
        if (editText.getText().toString().isEmpty()) {
            Toast.makeText(Home.this, "Enter a Task to add", Toast.LENGTH_SHORT).show();
        } else {
            TaskData taskData1 = new TaskData();
            taskData1.setTaskData(editText.getText().toString());
            taskData1.setStatus(false);
            taskData1.setRef((int) System.currentTimeMillis());
            Toast.makeText(Home.this, editText.getText().toString(), Toast.LENGTH_SHORT).show();
            databaseReference1
                    .child(Objects.requireNonNull(auth.getUid()))
                    .child(String.valueOf(System.currentTimeMillis()))
                    .setValue(taskData1).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    dialog.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Home.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    private void addDataToRecyclerView() {
        databaseReference
                .child(Objects.requireNonNull(auth.getUid()))
//               .child(String.valueOf(System.currentTimeMillis()))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        if (snapshot.exists()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                list.add(dataSnapshot.getValue(TaskData.class));
                            }
                            TodoTasks todoTasks = new TodoTasks(Home.this, list);
                            recyclerView.setLayoutManager(new LinearLayoutManager(Home.this));
                            recyclerView.setAdapter(todoTasks);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Home.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}