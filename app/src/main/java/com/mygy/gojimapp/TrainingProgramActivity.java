package com.mygy.gojimapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mygy.gojimapp.adapters.TrainingDayRecyclerAdapter;
import com.mygy.gojimapp.data.TrainingProgramm;

public class TrainingProgramActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_program);

        ImageButton backBtn = findViewById(R.id.trainingProgramContent_backBtn);
        backBtn.setOnClickListener(v -> {
            finish();
        });

        Bundle arguments = getIntent().getExtras();
        TrainingProgramm program;
        if(arguments!=null){
            program = (TrainingProgramm) arguments.getSerializable(TrainingProgramm.class.getSimpleName());
            TextView programName = findViewById(R.id.trainingProgramContent_name);
            programName.setText(program.getName());
            RecyclerView recyclerView=findViewById(R.id.trainingProgramContent_recycler);
            TrainingDayRecyclerAdapter adapter=new TrainingDayRecyclerAdapter(this,program.getTrainingDays(),true);
            recyclerView.setAdapter(adapter);
        }
    }
}